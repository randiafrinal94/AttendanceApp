import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home',
            component: () => import('../views/HomeView.vue')
        },
        {
            path: '/login',
            name: 'login',
            component: () => import('../views/auth/LoginView.vue')
        },
        {
            path: '/register',
            name: 'register',
            component: () => import('../views/auth/RegisterView.vue')
        },
        {
            path: '/forgot-password',
            name: 'forgot-password',
            component: () => import('../views/auth/ForgotPasswordView.vue')
        }
    ]
})

import authStore from '../stores/auth'

router.beforeEach((to, from, next) => {
    const publicPages = ['/login', '/register', '/forgot-password']
    const authRequired = !publicPages.includes(to.path)
    const loggedIn = authStore.isAuthenticated.value

    if (authRequired && !loggedIn) {
        return next('/login')
    }

    if (loggedIn && publicPages.includes(to.path)) {
        return next('/')
    }

    next()
})

export default router
