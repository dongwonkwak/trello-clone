import type {User} from "oidc-client-ts";

export const useAuthStore = defineStore('auth', () => {
  const user = ref<User | null>(null);
  const loading = ref<boolean>(false);
  const isAuthenticated = computed(() => !!user.value);
  const access_token = computed(() => user.value?.access_token);

  const setUser = (newUser: User | null) => {
    user.value = newUser;
  }

  return {
    user,
    loading,
    isAuthenticated,
    access_token,
    setUser
  }
});