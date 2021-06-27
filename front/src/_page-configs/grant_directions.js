export const grantDirectionsListConfig = {
    url: '/grant_direction',
    read_privilege: 'ROLE_ROOT',
    write_privilege: 'ROLE_ROOT',
    title: "Грантовое направление",
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
                if(target === undefined || target === '') return 'У направления должно быть задано название'
            }
        }
    ]
}