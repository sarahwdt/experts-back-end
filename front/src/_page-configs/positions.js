export const positionsListConfig = {
    url: '/position',
    read_privilege: 'ROLE_ROOT',
    write_privilege: 'ROLE_ROOT',
    title: "Должность",
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
                if(target === undefined || target === '') return 'У должности должно быть задано название'
            }
        }
    ]
}