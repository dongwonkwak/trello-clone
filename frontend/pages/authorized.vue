<script setup lang="ts">

import {useAuthService} from "~/composables/useAuthService";

const authService = useAuthService();

onMounted(async () => {
  try {
    const user = await authService.loginCallback();
    console.log('[authorized] login success');
    console.log(user);
    navigateTo('/');
  } catch(e) {
    console.error('[authorized] ', e);
  }
})

</script>

<template>

</template>
