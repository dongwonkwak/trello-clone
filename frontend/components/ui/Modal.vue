<!-- components/ui/Modal.vue -->
<template>
  <div class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
    <div class="bg-white p-8 rounded-lg shadow-lg max-w-md w-full">
      <div class="text-center">
        <slot name="icon"></slot>
        <h3 class="text-xl font-bold mb-2">{{ title }}</h3>
        <p class="text-gray-600 mb-6">{{ message }}</p>
        <button
            @click="$emit('action')"
            class="px-6 py-2 rounded-md hover:opacity-90 transition"
            :class="buttonClass"
        >
          {{ buttonText }}
        </button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
defineProps({
  title: {
    type: String,
    required: true
  },
  message: {
    type: String,
    required: true
  },
  buttonText: {
    type: String,
    required: true
  },
  buttonClass: {
    type: String,
    default: 'bg-blue-600 text-white'
  }
});

defineEmits(['action']);
</script>