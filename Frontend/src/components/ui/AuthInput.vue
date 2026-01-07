<script setup>
defineProps({
  modelValue: String,
  label: String,
  type: {
    type: String,
    default: 'text'
  },
  id: String,
  placeholder: String,
  required: Boolean
})
defineEmits(['update:modelValue'])
</script>

<template>
  <div class="mb-4">
    <label :for="id" class="block text-sm font-medium text-gray-700 mb-1">{{ label }}</label>
    <input
      :id="id"
      :type="type"
      :value="modelValue"
      @input="$emit('update:modelValue', $event.target.value)"
      :placeholder="placeholder"
      :required="required"
      class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 transition duration-200 ease-in-out shadow-sm placeholder-gray-400"
    />
  </div>
</template>
