import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import ViewRecipe from '../views/ViewRecipe.vue'
import Account from '../views/Account.vue'
Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home
    },
    {
        path: '/viewrecipe/:id?',
        name: 'ViewRecipe',
        component: ViewRecipe,
        props: route => ({ query: route.query.id })
    },
    {
        path: '/account',
        name: 'Account',
        component: Account
    }
];

const router = new VueRouter({
    mode: 'hash',
    base: process.env.BASE_URL,
    routes
})

export default router