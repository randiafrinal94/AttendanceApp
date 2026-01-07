<script setup>
import { ref, onMounted } from 'vue';
import api from '../libs/api';

const attendanceList = ref([]);
const isLoading = ref(false);

const fetchAttendance = async () => {
    isLoading.value = true;
    try {
        const response = await api.get('/attendance');
        attendanceList.value = response.data;
    } catch (error) {
        console.error('Error fetching attendance:', error);
    } finally {
        isLoading.value = false;
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
        <h2 class="text-xl font-semibold mb-4">Attendance History</h2>
        
        <div class="overflow-x-auto">
            <table class="min-w-full divide-y divide-gray-200">
                <thead class="bg-gray-50">
                    <tr>
                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Date</th>
                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Name</th>
                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Username</th>
                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Check In</th>
                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Check Out</th>
                    </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200">
                    <tr v-if="isLoading">
                        <td colspan="5" class="px-6 py-4 text-center">Loading...</td>
                    </tr>
                    <tr v-else-if="attendanceList.length === 0">
                        <td colspan="5" class="px-6 py-4 text-center">No records found</td>
                    </tr>
                    <tr v-else v-for="record in attendanceList" :key="record.id">
                        <td class="px-6 py-4 whitespace-nowrap">{{ record.date }}</td>
                        <td class="px-6 py-4 whitespace-nowrap font-medium text-gray-900">{{ record.name }}</td>
                        <td class="px-6 py-4 whitespace-nowrap text-gray-500">{{ record.username }}</td>
                        <td class="px-6 py-4 whitespace-nowrap text-green-600">
                            {{ record.checkInTime ? new Date(record.checkInTime).toLocaleTimeString() : '-' }}
                        </td>
                        <td class="px-6 py-4 whitespace-nowrap text-red-600">
                            {{ record.checkOutTime ? new Date(record.checkOutTime).toLocaleTimeString() : '-' }}
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>
