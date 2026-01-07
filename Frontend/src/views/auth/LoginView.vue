<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import AuthInput from '../../components/ui/AuthInput.vue'
import AuthButton from '../../components/ui/AuthButton.vue'
import api from '../../libs/api'
import authStore from '../../stores/auth'
import Swal from 'sweetalert2'

const router = useRouter()
const username = ref('')
const password = ref('')
const isLoading = ref(false)

const handleLogin = async () => {
  isLoading.value = true
  try {
    const response = await api.post('/auth/login', {
      username: username.value,
      password: password.value
    })
    
    const { token, username: user, name } = response.data
    authStore.setToken(token)
    authStore.setUser({ username: user, name })
    
    router.push('/')
  } catch (error) {
    Swal.fire({
      icon: 'error',
      title: 'Login Failed',
      text: error.response?.data || 'Login failed'
    })
  } finally {
    isLoading.value = false
  }
}
</script>

<template>
  <div class="min-h-screen bg-gradient-to-br from-indigo-100 to-purple-100 flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
    <div class="max-w-md w-full space-y-8 bg-white p-8 rounded-xl shadow-lg transform transition-all hover:scale-[1.01] duration-300">
      <div>
        <h2 class="mt-2 text-center text-3xl font-extrabold text-gray-900 tracking-tight">
          Welcome Back
        </h2>
        <p class="mt-2 text-center text-sm text-gray-600">
          Sign in to your account
        </p>
      </div>
      <form class="mt-8 space-y-6" @submit.prevent="handleLogin">
        <div class="rounded-md shadow-sm -space-y-px">
          <AuthInput
            id="username"
            label="Username"
            type="text"
            v-model="username"
            placeholder="username"
            required
            class="mb-4"
          />
          <AuthInput
            id="password"
            label="Password"
            type="password"
            v-model="password"
            placeholder="••••••••"
            required
          />
        </div>

        <div class="flex items-center justify-between mt-4">
          <div class="text-sm">
            <router-link to="/forgot-password" class="font-medium text-indigo-600 hover:text-indigo-500 transition-colors">
              Forgot your password?
            </router-link>
          </div>
        </div>

        <div>
          <AuthButton type="submit" :isLoading="isLoading">
            Sign in
          </AuthButton>
        </div>
      </form>
      
      <div class="text-center mt-4">
        <p class="text-sm text-gray-600">
          Don't have an account? 
          <router-link to="/register" class="font-medium text-indigo-600 hover:text-indigo-500 transition-colors">
            Register here
          </router-link>
        </p>
      </div>
    </div>
  </div>
</template>
