
export const projectsListConfig = {
    url: '/project',
    read_privilege: 'ROLE_ROOT',
    write_privilege: 'ROLE_ROOT',
    title:'Проект',
    columns: [
        {
            title:'Идентификатор',
            key:'id'
        },
        {
            title:'Название',
            key: 'name'
        },
        {
            title:'Описание',
            key: 'description'
        },
    ],
    fields: [
        {
            title: 'Название',
            key: 'name',
            validateFunction(target) {
                if (target === undefined || target.length < 5) return 'Название должно содержать больше 5х символов'
            }
        },
        {
            title: 'Описание',
            key: 'description',
            validateFunction(target) {
                if (target === undefined || target.length < 5) return 'Описание должно содержать больше 5х символов'
            }
        },
    ]
}