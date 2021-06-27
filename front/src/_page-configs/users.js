import ObjectMultiselect from "@/components/fragments/ObjectMultiselect";
import ObjectSelect from "../components/fragments/ObjectSelect";

export const usersListConfig = {
    url: '/user',
    read_privilege: 'ROLE_ROOT',
    write_privilege: 'ROLE_ROOT',
    title: "Пользователь",
    columns: [
        {
            title: 'Идентификатор',
            key: 'id'
        },
        {
            title: 'Логин',
            key: 'login'
        },
        {
            title: 'Имя',
            key: 'secondName',
            displayedData: (item) => {
                return (item.secondName || '')+ " " + (item.firstName || '')+ " " + (item.middleName || '')
            }
        },
        {
            title: 'Телефон',
            key: 'phone'
        },
        {
            title: 'e-mail',
            key: 'email'
        },
        {
            title: 'Степень',
            key: 'degree',
            displayedData(item){
                return  item.degree !== null && item.degree['name'] || ''
            }
        },
        {
            title: 'Звание',
            key: 'rank',
            displayedData(item){
                return item.rank !== null && item.rank.name || ''
            }
        },
        {
            title: 'Должность',
            key: 'position',
            displayedData(item){
                return item.position !== null && item.position.name || ''
            }
        },
        {
            title: 'Направление',
            key: 'direction',
            displayedData(item){
                return item.direction !== null && item.direction.name || ''
            }
        },
        {
            title: 'Роли',
            key: 'roles',
            displayedData(item) {
                return item.roles.map(role => role.name)||''
            }
        },

    ],
    fields: [
        {
            title: 'Логин',
            key: 'login',
            disabled: 'true'
        },
        {
            title: 'Имя',
            key: 'firstName',
            validateFunction(target) {
                if (target !== null && target.length < 2) return 'Имя должно содержать больше 2х символов'
            }
        },
        {
            title: 'Отчество',
            key: 'middleName',
            validateFunction(target) {
                if (target !== null && target.length < 2) return 'Отчество должно содержать больше 2х символов'
            }
        },
        {
            title: 'Фамилия',
            key: 'secondName',
            validateFunction(target) {
                if (target !== null && target.length < 2) return 'Фамилия должна содержать больше 2х символов'
            }
        },
        {
            title: 'Телефон',
            key: 'phone',
            validateFunction(target) {
                if (target !== null && target.length !== 12) return 'Номер телефона в формате +7XXXXXXXXXXX'
            }
        },
        {
            title: 'e-mail',
            key: 'email',
            validateFunction(target) {
                const validRegex = /^[\w!#$%&'*+/=?`{|}~^-]+(?:\.[\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,6}$/
                if (target !== null && !target.match(validRegex))
                    return 'E-mail должен быть в формате example@mail.com'
            }
        },
        {
            title: 'Степень',
            key: 'degree',
            component: ObjectSelect,
            props(){
                return{
                    url:'/degree',
                    displayedValue(item){
                        return item != null && item['name'] || ''
                    },
                }
            }
        },
        {
            title: 'Звание',
            key: 'rank',
            component: ObjectSelect,
            props(){
                return{
                    url:'/rank',
                    displayedValue(item){
                        return item != null && item['name'] || ''
                    },
                }
            }
        },
        {
            title: 'Должность',
            key: 'position',
            component: ObjectSelect,
            props(){
                return{
                    url:'/position',
                    displayedValue(item){
                        return item != null && item['name'] || ''
                    },
                }
            }
        },
        {
            title: 'Направление научной работы',
            key: 'direction',
            component: ObjectSelect,
            props(){
                return{
                    url:'/scientific_direction',
                    displayedValue(item){
                        return item != null && item['name'] || ''
                    },
                }
            }
        },
        {
            title: 'Роли',
            key: 'roles',
            component: ObjectMultiselect,
            props(){
                return{
                    url:'/role',
                    displayedValue(item){
                        return item['name']
                    },
                }
            }
        },
    ]
}