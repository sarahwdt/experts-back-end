import DateTimePicker from "@/components/fragments/DateTimePicker";
import ObjectMultiselect from "@/components/fragments/ObjectMultiselect";
import ObjectSelect from "../components/fragments/ObjectSelect";
import IndicatorEvaluation from "../components/fragments/IndicatorEvaluation";
import ProjectEvaluation from "../components/fragments/ProjectEvaluation";
import DownloadReport from "../components/fragments/DownloadReport";

export const contestsAdminListConfig = {
    url: '/contest',
    read_privilege: 'ROLE_ROOT',
    write_privilege: 'ROLE_ROOT',
    title: 'Конкурс',
    columns: [
        {
            title:'Идентификатор',
            key:'id'
        },
        {
            title:'Название',
            key:'name',
        },
        {
            title:'Грантовое направление',
            key:'direction',
            displayedData: (item) =>{
                return item['direction']['name']
            }
        },
        {
            title:'Дата начала',
            key:'startDate',
            displayedData: (item) =>{
                return new Date(Date.parse(item['startDate'])).toLocaleString()
            }
        },
        {
            title:'Дата окончания',
            key:'endDate',
            displayedData: (item) =>{
                return new Date(Date.parse(item['endDate'])).toLocaleString()
            }
        },
    ],
    fields:[
        {
            title:'Название',
            key:'name',
            validateFunction(target){
                if(target === undefined || target === '') return 'У конкурса должно быть задано название'
            }
        },
        {
            title:'Грантовое направление',
            key:'direction',
            component: ObjectSelect,
            props() {
                return {
                    url: '/grant_direction',
                    displayedValue(item) {
                        return item['name'];
                    }
                };
            },
            validateFunction: function (target){
                if(target === null) return 'Укажите грантовое направление'
            }
        },
        {
            title:'Дата начала',
            key:'startDate',
            component: DateTimePicker,
            validateFunction: function (target){
                if(target === undefined) return 'Необходимо установить дату начала конкурса'
                if((new Date(target)) < Date.now()) return 'Дата начала должна быть больше текущей даты'
            }
        },
        {
            title:'Дата окончания',
            key:'endDate',
            component: DateTimePicker,
            validateFunction: function (target){
                if(target === undefined) return 'Необходимо установить дату окончания конкурса'
                if((new Date(target)) < Date.now()) return 'Дата окончания должна быть больше текущей даты'
            }
        },
        {
            title:'Показатели',
            key:'indicators',
            component: ObjectMultiselect,
            props() {
                return {
                    url: '/indicator',
                    displayedValue(item) {
                        return item['name'];
                    }
                };
            },
            validateFunction: function (target){
                if(target === undefined) return 'Создайте показатели для оценки'
                if(target.length < 3) return 'Необходимо использовать более 2 показателей'
                if(target.length > 6) return 'Необходимо использовать не более 6 показателей'
            }
        },
        {
            title:'Эксперты',
            key:'users',
            component: ObjectMultiselect,
            props() {
                return {
                    url: '/user',
                    displayedValue(item) {
                        return item['id'] + ":" + item['firstName'] + " " + item['secondName'];
                    }
                };
            },
            validateFunction: function (target){
                if(target === undefined) return 'Добавьте экспертов'
                if(target.length < 3) return 'Необходимо указать более 2 экспертов'
                if(target.length > 6) return 'Необходимо указать не более 6 экспертов'
            }
        },
        {
            title:'Проекты',
            key:'projects',
            component: ObjectMultiselect,
            props() {
                return {
                    url: '/project',
                    displayedValue(item) {
                        return item['name'];
                    }
                };
            },
            validateFunction: function (target){
                if(target === undefined) return 'Добавьте проекты'
                if(target.length < 2) return 'Необходимо указать более 1 проектов'
                if(target.length > 10) return 'Необходимо указать не более 10 проектов'
            }
        },
        {
            title:'Оценки критериев',
            key:'indicatorEvaluationList',
            component: IndicatorEvaluation,
        },
        {
            title:'Оценки проектов',
            key:'finalEvaluations',
            component: ProjectEvaluation,
        },
        {
            title: '',
            key:'id',
            component: DownloadReport
        }
    ]
}