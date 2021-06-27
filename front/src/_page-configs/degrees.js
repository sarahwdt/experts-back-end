export const degreesListConfig = {
    url: '/degree',
    read_privilege: 'ROLE_ROOT',
    write_privilege: 'ROLE_ROOT',
    title: "Научная степень",
    columns: [
        {
            title:'Идентификатор',
            key:'id'
        },
        {
            title:'Название',
            key:'name'
        }
    ],
    fields:[
        {
            title:'Название',
            key:'name',
            validateFunction(target){
                if(target === undefined || target === '') return 'У степени должно быть задано название'
            }
        }
    ]
}