<!-- components/ui/ErrorModal.vue -->
<template>
  <Modal
      :title="title"
      :message="message"
      :button-text="buttonText"
      button-class="bg-red-600 text-white"
      @action="$emit('action')"
  >
    <template #icon>
      <svg xmlns="http://www.w3.org/2000/svg" class="h-16 w-16 text-red-500 mx-auto mb-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
      </svg>
    </template>
  </Modal>
</template>

<script setup lang="ts">
import Modal from './Modal.vue';

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
  }
});

defineEmits(['action']);
</script>