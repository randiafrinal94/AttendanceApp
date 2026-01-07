<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import AuthInput from '../../components/ui/AuthInput.vue'
import AuthButton from '../../components/ui/AuthButton.vue'

const router = useRouter()
const email = ref('')
const isLoading = ref(false)
const isSubmitted = ref(false)

const handleReset = async () => {
  isLoading.value = true
  // Mock reset delay
  setTimeout(() => {
    isLoading.value = false
    isSubmitted.value = true
  }, 1500)
}
</script>

<template>
  <div class="min-h-screen bg-gradient-to-br from-indigo-100 to-purple-100 flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
    <div class="max-w-md w-full space-y-8 bg-white p-8 rounded-xl shadow-lg transform transition-all hover:scale-[1.01] duration-300">
      <div>
        <h2 class="mt-2 text-center text-3xl font-extrabold text-gray-900 tracking-tight">
          Reset Password
        </h2>
        <p class="mt-2 text-center text-sm text-gray-600">
          Enter your email to receive reset instructions
        </p>
      </div>
      
      <div v-if="isSubmitted" class="bg-green-50 border border-green-200 rounded-md p-4 text-center">
        <p class="text-green-800 font-medium">Check your email!</p>
        <p class="text-green-600 text-sm mt-1">We've sent password reset instructions to {{ email }}</p>
        <button @click="router.push('/login')" class="mt-4 text-indigo-600 hover:text-indigo-500 font-medium text-sm">
          Return to Login
        </button>
      </div>

      <form v-else class="mt-8 space-y-6" @submit.prevent="handleReset">
        <AuthInput
          id="email-address"
          label="Email address"
          type="email"
          v-model="email"
          placeholder="you@example.com"
          required
        />

        <div>
          <AuthButton type="submit" :isLoading="isLoading">
            Send Reset Link
          </AuthButton>
        </div>
      </form>
      
      <div class="text-center mt-4" v-if="!isSubmitted">
        <p class="text-sm text-gray-600">
          Remember your password? 
          <router-link to="/login" class="font-medium text-indigo-600 hover:text-indigo-500 transition-colors">
            Sign in here
          </router-link>
        </p>
      </div>
    </div>
  </div>
</template>
