<script setup lang="ts">
const { t } = useI18n();
navigateTo("/")

</script>

<template>
  <div class="flex justify-center items-center min-h-screen">
    <p>{{ t('common.loading') }}</p>
  </div>
</template>
