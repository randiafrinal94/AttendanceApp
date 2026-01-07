<script setup>
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import AttendanceForm from '../components/AttendanceForm.vue';
import AttendanceList from '../components/AttendanceList.vue';
import authStore from '../stores/auth';
import api from '../libs/api';
import Swal from 'sweetalert2';

const router = useRouter();
const listRef = ref(null);

const isProfileMenuOpen = ref(false);
const isEditProfileModalOpen = ref(false);

const profileForm = reactive({
    name: '',
    password: ''
});

const toggleProfileMenu = () => {
    isProfileMenuOpen.value = !isProfileMenuOpen.value;
};

const openEditProfile = () => {
    profileForm.name = authStore.state.user?.name || '';
    profileForm.password = '';
    isEditProfileModalOpen.value = true;
    isProfileMenuOpen.value = false;
};

const closeEditProfile = () => {
    isEditProfileModalOpen.value = false;
};

const handleLogout = () => {
    authStore.logout();
    router.push('/login');
};

const onAttendanceUpdated = () => {
    if (listRef.value) {
        listRef.value.fetchAttendance();
    }
};

const saveProfile = async () => {
    try {
        await api.put('/employees/profile', profileForm);
        // Update local user state
        const updatedUser = { ...authStore.state.user, name: profileForm.name };
        authStore.setUser(updatedUser);
        
        Swal.fire({
            icon: 'success',
            title: 'Success',
            text: 'Profile updated successfully!',
            timer: 1500,
            showConfirmButton: false
        });
        
        closeEditProfile();
    } catch (error) {
        console.error(error);
         Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Failed to update profile'
        });
    }
};
</script>

<template>
  <div class="min-h-screen bg-gray-50 font-sans text-gray-800">
    <!-- Navbar -->
    <nav class="bg-white shadow-sm sticky top-0 z-50">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex justify-between h-16">
          <div class="flex items-center">
            <h1 class="text-2xl font-bold text-indigo-600 tracking-tight">Attendance App</h1>
          </div>
          <div class="flex items-center">
            <!-- Profile Dropdown -->
            <div class="relative ml-3">
              <div>
                <button @click="toggleProfileMenu" class="flex items-center max-w-xs bg-white rounded-full focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 p-1 hover:bg-gray-100 transition">
                   <div class="h-8 w-8 rounded-full bg-indigo-100 flex items-center justify-center text-indigo-600 font-bold">
                      {{ authStore.state.user?.name ? authStore.state.user.name.charAt(0).toUpperCase() : 'U' }}
                   </div>
                   <span class="ml-2 text-sm font-medium text-gray-700 hidden md:block">{{ authStore.state.user?.name }}</span>
                   <svg class="ml-1 h-5 w-5 text-gray-400" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                      <path fill-rule="evenodd" d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z" clip-rule="evenodd" />
                   </svg>
                </button>
              </div>

              <!-- Menu (conditionally rendered) -->
              <div v-if="isProfileMenuOpen" class="origin-top-right absolute right-0 mt-2 w-48 rounded-md shadow-lg py-1 bg-white ring-1 ring-black ring-opacity-5 focus:outline-none z-50">
                <a href="#" @click.prevent="openEditProfile" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">My Profile</a>
                <a href="#" @click.prevent="handleLogout" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">Logout</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </nav>

    <!-- Content -->
    <main class="py-10">
      <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 space-y-8">
          <!-- Welcome Section -->
          <div class="text-center mb-8">
             <h2 class="text-3xl font-bold text-gray-900">Welcome Back, {{ authStore.state.user?.name }}!</h2>
             <p class="mt-2 text-gray-600">Have a productive day.</p>
          </div>

          <AttendanceForm @refresh="onAttendanceUpdated" />
          <AttendanceList ref="listRef" />
      </div>
    </main>

    <!-- Edit Profile Modal -->
    <div v-if="isEditProfileModalOpen" class="fixed z-50 inset-0 overflow-y-auto" aria-labelledby="modal-title" role="dialog" aria-modal="true">
      <div class="flex items-end justify-center min-h-screen pt-4 px-4 pb-20 text-center sm:block sm:p-0">
        
        <div class="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity" aria-hidden="true" @click="closeEditProfile"></div>

        <span class="hidden sm:inline-block sm:align-middle sm:h-screen" aria-hidden="true">&#8203;</span>

        <div class="inline-block align-bottom bg-white rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full">
          <div class="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
            <h3 class="text-lg leading-6 font-medium text-gray-900" id="modal-title">Edit Profile</h3>
            <div class="mt-4 space-y-4">
                <div>
                   <label class="block text-sm font-medium text-gray-700">Name</label>
                   <input type="text" v-model="profileForm.name" class="mt-1 block w-full border border-gray-300 rounded-md shadow-sm py-2 px-3 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm">
                </div>
                <div>
                   <label class="block text-sm font-medium text-gray-700">New Password (leave blank to keep current)</label>
                   <input type="password" v-model="profileForm.password" class="mt-1 block w-full border border-gray-300 rounded-md shadow-sm py-2 px-3 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm">
                </div>
            </div>
          </div>
          <div class="bg-gray-50 px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse">
            <button @click="saveProfile" type="button" class="w-full inline-flex justify-center rounded-md border border-transparent shadow-sm px-4 py-2 bg-indigo-600 text-base font-medium text-white hover:bg-indigo-700 focus:outline-none sm:ml-3 sm:w-auto sm:text-sm">
              Save Changes
            </button>
            <button @click="closeEditProfile" type="button" class="mt-3 w-full inline-flex justify-center rounded-md border border-gray-300 shadow-sm px-4 py-2 bg-white text-base font-medium text-gray-700 hover:bg-gray-50 focus:outline-none sm:mt-0 sm:ml-3 sm:w-auto sm:text-sm">
              Cancel
            </button>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>
