<script setup>
import { ref } from 'vue';
import api from '../libs/api';
import authStore from '../stores/auth';
import Swal from 'sweetalert2';

const emit = defineEmits(['refresh']);
const message = ref('');
// const error = ref(''); // Removed error ref as we use Swal

const checkIn = async () => {
    try {
        message.value = '';
        await api.post('/attendance/checkin');
        Swal.fire({
            icon: 'success',
            title: 'Checked In',
            text: 'You have checked in successfully!',
            timer: 1500,
            showConfirmButton: false
        });
        emit('refresh');
    } catch (err) {
        const errorMsg = err.response?.data?.error || err.response?.data?.message || 'Failed to check in';
        Swal.fire({
            icon: 'error',
            title: 'Check In Failed',
            text: errorMsg
        });
    }
};

const checkOut = async () => {
    try {
        message.value = '';
        await api.post('/attendance/checkout');
        Swal.fire({
            icon: 'success',
            title: 'Checked Out',
            text: 'You have checked out successfully!',
            timer: 1500,
            showConfirmButton: false
        });
        emit('refresh');
    } catch (err) {
        const errorMsg = err.response?.data?.error || err.response?.data?.message || 'Failed to check out';
        Swal.fire({
            icon: 'error',
            title: 'Check Out Failed',
            text: errorMsg
        });
    }
};
</script>

<template>
    <div class="bg-white p-6 rounded-lg shadow-md mb-8">
        <div class="flex flex-col items-center">
            
            <div class="flex space-x-4">
                <button 
                    @click="checkIn" 
                    class="px-6 py-2 bg-green-600 text-white rounded hover:bg-green-700 transition cursor-pointer"
                >
                    Check In
                </button>
                <button 
                    @click="checkOut" 
                    class="px-6 py-2 bg-red-600 text-white rounded hover:bg-red-700 transition cursor-pointer"
                >
                    Check Out
                </button>
            </div>

            <!-- Removed message/error text display as we use SweetAlert -->
        </div>
    </div>
</template>
