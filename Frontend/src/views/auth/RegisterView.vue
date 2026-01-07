<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import AuthInput from '../../components/ui/AuthInput.vue'
import AuthButton from '../../components/ui/AuthButton.vue'
import api from '../../libs/api'
import Swal from 'sweetalert2'

const router = useRouter()
const name = ref('')
const username = ref('')
const email = ref('')
const password = ref('')
const confirmPassword = ref('')
const isLoading = ref(false)

const handleRegister = async () => {
  if (password.value !== confirmPassword.value) {
    Swal.fire({
      icon: 'error',
      title: 'Error',
      text: 'Passwords do not match!'
    })
    return
  }
  
  isLoading.value = true
  try {
    await api.post('/auth/register', {
      name: name.value,
      username: username.value,
      email: email.value,
      password: password.value,
      position: 'Employee' // Default position
    })
    
    Swal.fire({
      icon: 'success',
      title: 'Success',
      text: 'Account created successfully! Please login.',
      timer: 1500,
      showConfirmButton: false
    })
    router.push('/login')
  } catch (error) {
    Swal.fire({
      icon: 'error',
      title: 'Registration Failed',
      text: error.response?.data || 'Registration failed'
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
          Create Account
        </h2>
        <p class="mt-2 text-center text-sm text-gray-600">
          Join us today
        </p>
      </div>
      <form class="mt-8 space-y-4" @submit.prevent="handleRegister">
        <AuthInput
          id="name"
          label="Full Name"
          type="text"
          v-model="name"
          placeholder="John Doe"
          required
        />
        
        <AuthInput
          id="username"
          label="Username"
          type="text"
          v-model="username"
          placeholder="john.doe"
          required
        />
        
        <AuthInput
          id="email-address"
          label="Email address"
          type="email"
          v-model="email"
          placeholder="you@example.com"
          required
        />
        
        <AuthInput
          id="password"
          label="Password"
          type="password"
          v-model="password"
          placeholder="••••••••"
          required
        />

        <AuthInput
          id="confirm-password"
          label="Confirm Password"
          type="password"
          v-model="confirmPassword"
          placeholder="••••••••"
          required
        />

        <div class="pt-4">
          <AuthButton type="submit" :isLoading="isLoading">
            Register
          </AuthButton>
        </div>
      </form>
      
      <div class="text-center mt-4">
        <p class="text-sm text-gray-600">
          Already have an account? 
          <router-link to="/login" class="font-medium text-indigo-600 hover:text-indigo-500 transition-colors">
            Sign in here
          </router-link>
        </p>
      </div>
    </div>
  </div>
</template>
