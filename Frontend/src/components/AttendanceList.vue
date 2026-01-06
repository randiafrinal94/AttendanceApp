<script setup>
import { ref, onMounted } from 'vue';
import api from '../libs/axios';

const attendanceList = ref([]);
const loading = ref(false);
const error = ref('');

const fetchAttendance = async () => {
  loading.value = true;
  error.value = '';
  try {
    const response = await api.get('/api/attendance');
    // Sort by checkInTime descending (newest first)
    attendanceList.value = response.data.sort((a, b) => {
        return new Date(b.checkInTime) - new Date(a.checkInTime);
    });
  } catch (err) {
    console.error(err);
    error.value = 'Failed to load attendance history';
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  fetchAttendance();
});

defineExpose({
  fetchAttendance
});
</script>

<template>
  <div class="bg-white p-6 rounded-lg shadow-md">
    <div class="flex justify-between items-center mb-4">
        <h2 class="text-xl font-semibold text-gray-800">Attendance History</h2>
        <button @click="fetchAttendance" class="text-blue-600 hover:text-blue-800 text-sm">Refresh</button>
    </div>

    <div v-if="loading" class="text-center text-gray-500 py-4">Loading...</div>
    <div v-else-if="error" class="text-center text-red-500 py-4">{{ error }}</div>
    
    <div v-else class="overflow-x-auto">
      <table class="min-w-full divide-y divide-gray-200">
        <thead class="bg-gray-50">
          <tr>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Employee ID</th>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Date</th>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Check In</th>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Check Out</th>
          </tr>
        </thead>
        <tbody class="bg-white divide-y divide-gray-200">
          <tr v-for="item in attendanceList" :key="item.id" class="hover:bg-gray-50">
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ item.id }}</td>
            <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">{{ item.employeeId }}</td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ item.date }}</td>
             <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
              {{ item.checkInTime ? new Date(item.checkInTime).toLocaleTimeString() : '-' }}
            </td>
             <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
              {{ item.checkOutTime ? new Date(item.checkOutTime).toLocaleTimeString() : '-' }}
            </td>
          </tr>
          <tr v-if="attendanceList.length === 0">
            <td colspan="5" class="px-6 py-4 text-center text-sm text-gray-500">No attendance records found.</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
