<script setup>
import { ref } from 'vue';
import AttendanceForm from './components/AttendanceForm.vue';
import AttendanceList from './components/AttendanceList.vue';

const listRef = ref(null);

const onAttendanceUpdated = () => {
    // Call fetchAttendance on the child component
    if (listRef.value) {
        listRef.value.fetchAttendance();
    }
};
</script>

<template>
  <div class="min-h-screen bg-gray-100 py-10 px-4 sm:px-6 lg:px-8">
    <div class="max-w-4xl mx-auto">
      <header class="mb-8 text-center">
        <h1 class="text-3xl font-extrabold text-gray-900">Attendance System</h1>
        <p class="mt-2 text-gray-600">Employee Check In / Check Out</p>
      </header>

      <!-- Form Component -->
      <AttendanceForm @refresh="onAttendanceUpdated" />

      <!-- List Component -->
      <AttendanceList ref="listRef" />
    </div>
  </div>
</template>

<style>
/* Global styles if needed, Tailwind handles most */
body {
    font-family: 'Inter', sans-serif;
}
</style>
