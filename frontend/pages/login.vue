<template>

</template>

<script setup lang="ts">
import {useAuthService} from "~/composables/useAuthService";

console.log("login page");
const authService = useAuthService();

onMounted(async () => {
  try {
    await authService.login();
  } catch(e) {
    console.error(e);
  }
})

</script>