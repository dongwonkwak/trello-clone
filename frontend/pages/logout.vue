<script setup lang="ts">
import {useAuthService} from "~/composables/useAuthService";

const { t } = useI18n();
const authService = useAuthService();

onMounted(async () => {
  try {
    console.log('[authorized] logout');
    await authService.logout();
  } catch (e) {
    console.error(e);
  } finally {
    navigateTo('/');
  }
});

</script>

<template>
  <div class="flex justify-center items-center min-h-screen">
    <p>{{ t('common.loading') }}</p>
  </div>
</template>
