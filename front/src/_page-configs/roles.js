export const rolesListConfig = {
    url: '/role',
    title: "Роль",
    read_privilege: 'ROLE_ROOT',
    write_privilege: 'ROLE_ROOT',
    columns: [
        {
            title:'Идентификатор',
            key:'id'
        },
        {
            title:'Название',
            key:'name'
        },
    ],
    fields:[
        {
            title:'Название',
            key:'name',
            validateFunction(target){
                if(target === undefined || target === '') return 'У роли должно быть задано название'
            }
        }
    ]
}