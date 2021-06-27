export const indicatorsListConfig = {
    url: '/indicator',
    read_privilege: 'ROLE_ROOT',
    write_privilege: 'ROLE_ROOT',
    title: "Критерии",
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
                if(target === undefined || target === '') return 'У критерия должно быть задано название'
            }
        }
    ]
}