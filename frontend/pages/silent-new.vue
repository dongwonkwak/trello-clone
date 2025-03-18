<script setup lang="ts">

import {useAuthService} from "~/composables/useAuthService";

const authService = useAuthService();

onMounted(async () => {
  try {
    console.log('silent renew')
    await authService.silentRenew();
  } catch(e) {
    console.error('[silent renew] ', e);
  }
})

</script>

<template>

</template>
