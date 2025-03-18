<!-- components/ui/SuccessModal.vue -->
<template>
  <Modal
      :title="title"
      :message="message"
      :button-text="buttonText"
      button-class="bg-green-600 text-white"
      @action="$emit('action')"
  >
    <template #icon>
      <svg xmlns="http://www.w3.org/2000/svg" class="h-16 w-16 text-green-500 mx-auto mb-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
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