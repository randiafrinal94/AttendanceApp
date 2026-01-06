<script setup>
import { ref } from 'vue';
import api from '../libs/axios';

const emit = defineEmits(['refresh']);

const employeeId = ref('');
const message = ref('');
const isError = ref(false);

const handleAttendance = async (type) => {
  if (!employeeId.value) {
    message.value = 'Employee ID is required';
    isError.value = true;
    return;
  }

  try {
    const endpoint = type === 'checkin' ? '/api/attendance/checkin' : '/api/attendance/checkout';
    const response = await api.post(endpoint, { employeeId: parseInt(employeeId.value) });
    
    // Response from backend: Attendance object
    const data = response.data;
    const time = type === 'checkin' ? data.checkInTime : data.checkOutTime;
    
    // Format timestamp if needed, or just show success
    const formattedTime = new Date(time).toLocaleString();
    
    message.value = `Success! ${type === 'checkin' ? 'Checked In' : 'Checked Out'} at ${formattedTime}`;
    isError.value = false;
    
    // Clear input
    employeeId.value = '';
    
    // Refresh list
    emit('refresh');
    
  } catch (error) {
    console.error(error);
    isError.value = true;
    if (error.response && error.response.data) {
       if (typeof error.response.data === 'object' && error.response.data !== null) {
          // Handle structured error from GlobalExceptionHandler
           const details = error.response.data.details ? JSON.stringify(error.response.data.details) : '';
           message.value = (error.response.data.error || 'Error') + (details ? ': ' + details : '');
       } else {
           message.value = error.response.data; // Backend returns string (legacy) or other format
       }
    } else {
       message.value = 'An error occurred';
    }
  }
};
</script>

<template>
  <div class="bg-white p-6 rounded-lg shadow-md mb-6">
    <h2 class="text-xl font-semibold mb-4 text-gray-800">Attendance Action</h2>
    
    <div class="mb-4">
      <label for="employeeId" class="block text-sm font-medium text-gray-700 mb-2">Employee ID</label>
      <input 
        id="employeeId" 
        v-model="employeeId" 
        type="number" 
        placeholder="Enter Employee ID"
        class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
      />
    </div>

    <div class="flex gap-4">
      <button 
        @click="handleAttendance('checkin')"
        class="flex-1 bg-green-600 hover:bg-green-700 text-white font-bold py-3 px-4 rounded transition duration-200"
      >
        Check In
      </button>
      <button 
        @click="handleAttendance('checkout')"
        class="flex-1 bg-red-600 hover:bg-red-700 text-white font-bold py-3 px-4 rounded transition duration-200"
      >
        Check Out
      </button>
    </div>

    <div v-if="message" :class="['mt-4 p-3 rounded text-center', isError ? 'bg-red-100 text-red-700' : 'bg-green-100 text-green-700']">
      {{ message }}
    </div>
  </div>
</template>
