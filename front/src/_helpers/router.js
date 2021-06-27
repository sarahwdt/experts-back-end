import Vue from 'vue';
import Router from 'vue-router';

import HomePage from "@/components/HomePage"
import ListPage from "@/components/ListPage";
import {
    rolesListConfig,
    usersListConfig
} from "@/_page-configs";
import {store} from "@/_helpers/store";
import {ranksListConfig} from "../_page-configs/ranks";
import {degreesListConfig} from "../_page-configs/degrees";
import {positionsListConfig} from "../_page-configs/positions";
import {scientificDirectionsListConfig} from "../_page-configs/scientific_directions";
import {projectsListConfig} from "../_page-configs/projects";
import {grantDirectionsListConfig} from "../_page-configs/grant_directions";
import {contestsAdminListConfig} from "../_page-configs/contests_admin";
import {indicatorsListConfig} from "../_page-configs/indicators";
import ContestPage from "../components/ContestPage";

Vue.use(Router);

const checkAuth = function (to, from, next) {
    if (!store.state.user) {
        next('/login');
    } else {
        next();
    }
}

export const router = new Router({
    mode: 'history',
    routes: [
        {path: '/', component: HomePage},
        {path: '*', redirect: '/'},
        {
            path:'/degree',
            component: ListPage,
            beforeEnter: checkAuth,
            props: degreesListConfig
        },
        {
            path:'/rank',
            component: ListPage,
            beforeEnter: checkAuth,
            props: ranksListConfig
        },
        {
            path:'/position',
            component: ListPage,
            beforeEnter: checkAuth,
            props: positionsListConfig
        },
        {
            path:'/scientific_direction',
            component: ListPage,
            beforeEnter: checkAuth,
            props: scientificDirectionsListConfig
        },
        {
            path:'/role',
            component: ListPage,
            beforeEnter: checkAuth,
            props: rolesListConfig
        },
        {
            path:'/indicator',
            component: ListPage,
            beforeEnter: checkAuth,
            props: indicatorsListConfig
        },
        {
            path:'/grant_direction',
            component: ListPage,
            beforeEnter: checkAuth,
            props: grantDirectionsListConfig
        },
        {
            path:'/user',
            component: ListPage,
            beforeEnter: checkAuth,
            props: usersListConfig
        },
        {
            path:'/project',
            component: ListPage,
            beforeEnter: checkAuth,
            props: projectsListConfig
        },
        {
            path:'/contest_adm',
            component: ListPage,
            beforeEnter: checkAuth,
            props: contestsAdminListConfig
        },
        {
            path:'/contest',
            component: ContestPage,
            beforeEnter: checkAuth,
        },

    ],
});

