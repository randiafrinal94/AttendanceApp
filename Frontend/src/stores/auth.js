import { reactive, computed } from 'vue'

const state = reactive({
    user: null,
    token: localStorage.getItem('token') || null
})

const authStore = {
    state,

    isAuthenticated: computed(() => !!state.token),

    setToken(token) {
        state.token = token
        localStorage.setItem('token', token)
    },

    setUser(user) {
        state.user = user
        if (user) {
            localStorage.setItem('user', JSON.stringify(user))
        } else {
            localStorage.removeItem('user')
        }
    },

    initialize() {
        const storedUser = localStorage.getItem('user')
        if (storedUser) {
            try {
                state.user = JSON.parse(storedUser)
            } catch (e) {
                localStorage.removeItem('user')
            }
        }
    },

    logout() {
        state.token = null
        state.user = null
        localStorage.removeItem('token')
        localStorage.removeItem('user')
    }
}

authStore.initialize()

export default authStore
