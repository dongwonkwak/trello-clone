<script setup lang="ts">

</script>

<template>
  <div class="flex flex-col min-h-screen">
    <Header />
    <main class="flex-grow container mx-auto p-4">
      <slot />
    </main>
  </div>
</template>

<style scoped>

</style>