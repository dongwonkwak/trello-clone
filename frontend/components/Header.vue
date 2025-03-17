<script setup lang="ts">
const { t } = useI18n();

</script>

<template>
  <header class="bg-white shadow p-4">
    <div class="container mx-auto flex justify-between items-center">
      <div>
        <h1 class="text-2xl font-bold">Trello Clone</h1>
      </div>
      <div class="space-x-2">
        <NuxtLink to="/signup" class="px-4 py-2 border border-gray-300 rounded-md text-gray-700 hover:bg-gray-50 transition">
          {{ t('auth.signup') }}
        </NuxtLink>
        <NuxtLink to="/login" class="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 transition">
          {{ t('auth.login') }}
        </NuxtLink>
      </div>
    </div>
  </header>
</template>

<style scoped>

</style>