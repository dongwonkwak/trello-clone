<template>
  <div class="max-w-md mx-auto bg-white p-8 rounded-lg shadow-md">
    <h2 class="text-2xl font-bold mb-6 text-center">
      {{ t('auth.signup') }}
    </h2>

    <form @submit.prevent="handleSignup">
      <!-- 이름, 성 -->
      <div class="mb-4 flex gap-4">
        <!-- 이름 입력 -->
        <div class="flex-1">
          <label for="firstname" class="block text-gray-700 font-medium mb-2">
            {{ t('auth.firstName') }}
          </label>
          <input
              type="text"
              id="firstname"
              v-model="firstname.value.value"
              class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              :class="{'border-red-500': firstname.errorMessage.value}"
              :placeholder="t('auth.firstName')"
              required
          />
          <p v-if="firstname.errorMessage.value" class="mt-1 text-sm text-red-500">
            {{ firstname.errorMessage.value }}
          </p>
        </div>

        <!-- 성 입력 -->
        <div class="flex-1">
          <label for="lastname" class="block text-gray-700 font-medium mb-2">
            {{ t('auth.lastName') }}
          </label>
          <input
              type="text"
              id="lastname"
              v-model="lastname.value.value"
              class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              :class="{'border-red-500': lastname.errorMessage.value}"
              :placeholder="t('auth.lastName')"
              required
          />
          <p v-if="lastname.errorMessage.value" class="mt-1 text-sm text-red-500">
            {{ lastname.errorMessage.value }}
          </p>
        </div>
      </div>


      <!-- 이메일 입력 -->
      <div class="mb-4">
        <label for="email" class="block text-gray-700 font-medium mb-2">
          {{ t('common.email')}}
        </label>
        <input
            type="email"
            id="email"
            v-model="email.value.value"
            class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            :placeholder="t('auth.enterEmail')"
            required
        />
        <p v-if="email.errorMessage.value" class="mt1 text-sm text-red-500">
          {{ email.errorMessage.value }}
        </p>
      </div>

      <!-- 비밀번호 입력 -->
      <div class="mb-4">
        <label for="password" class="block text-gray-700 font-medium mb-2">
          {{ t('auth.password') }}
        </label>
        <div class="relative">
          <input
              :type="showPassword ? 'text' : 'password'"
              id="password"
              v-model="password.value.value"
              class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              :placeholder="t('auth.enterPassword')"
              required
          />
          <button
              type="button"
              class="absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-500 hover:text-gray-700 focus:outline-none"
              @click="togglePasswordVisibility"
          >
            <svg v-if="showPassword" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5">
              <path stroke-linecap="round" stroke-linejoin="round" d="M3.98 8.223A10.477 10.477 0 0 0 1.934 12C3.226 16.338 7.244 19.5 12 19.5c.993 0 1.953-.138 2.863-.395M6.228 6.228A10.451 10.451 0 0 1 12 4.5c4.756 0 8.773 3.162 10.065 7.498a10.522 10.522 0 0 1-4.293 5.774M6.228 6.228 3 3m3.228 3.228 3.65 3.65m7.894 7.894L21 21m-3.228-3.228-3.65-3.65m0 0a3 3 0 1 0-4.243-4.243m4.242 4.242L9.88 9.88" />
            </svg>
            <svg v-else xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5">
              <path stroke-linecap="round" stroke-linejoin="round" d="M2.036 12.322a1.012 1.012 0 0 1 0-.639C3.423 7.51 7.36 4.5 12 4.5c4.638 0 8.573 3.007 9.963 7.178.07.207.07.431 0 .639C20.577 16.49 16.64 19.5 12 19.5c-4.638 0-8.573-3.007-9.963-7.178Z" />
              <path stroke-linecap="round" stroke-linejoin="round" d="M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z" />
            </svg>
          </button>
        </div>
        <p v-if="password.errorMessage.value" class="mt1 text-sm text-red-500">
          {{ password.errorMessage.value }}
        </p>
      </div>

      <!-- 회원가입 버튼 -->
      <button
          type="submit"
          class="w-full bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700 transition focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2"
      >
        {{ t('auth.signup') }}
      </button>

      <div class="mt-6 text-center">
        <p class="text-gray-600">
          {{ t('auth.alreadyHaveAccount') }}
          <NuxtLink to="/login" class="text-blue-600 hover:underline">
            {{ t('auth.login') }}
          </NuxtLink>
        </p>
      </div>
    </form>

    <!-- Use the modals -->
    <SuccessModal
      v-if="showSuccessModal"
      :title="t('auth.signupSuccessTitle')"
      :message="t('auth.signupSuccessMessage')"
      :button-text="t('common.goHome')"
      @action="goToHome"
    />
    <ErrorModal
      v-if="showErrorModal"
      :title="t('common.error')"
      :message="t('auth.signupErrorMessage')"
      :button-text="t('common.ok')"
      @action=""
    />
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useField, useForm } from "vee-validate";
import {useApiClient} from "~/composables/useApiClient";
import type {ModelError, SignupRequest} from "~/api";
import SuccessModal from "~/components/ui/SuccessModal.vue";
import ErrorModal from "~/components/ui/ErrorModal.vue";
const { t } = useI18n();
const showSuccessModal = ref(false);
const showErrorModal = ref(false);

const showPassword = ref(false);

const togglePasswordVisibility = () => {
  showPassword.value = !showPassword.value;
};

const goToHome = () => {
  showSuccessModal.value = false;
  navigateTo('/');
};

const { handleSubmit, setFieldError } = useForm({
  validationSchema: {
    firstname (value: string) {
      if (!value) {
        return t('auth.firstNameRequired');
      }
      return true;
    },
    lastname (value: string) {
      if (!value) {
        return t('auth.lastNameRequired');
      }
      return true;
    },
    email (value: string) {
      if (!value) {
        return t('auth.emailRequired');
      }
      return true;
    },
    password (value: string) {
      if (!value) {
        return t('auth.passwordRequired');
      }
      return true;
    },
  }
});

const firstname = useField("firstname");
const lastname = useField("lastname");
const email = useField('email');
const password = useField('password');
const apiClient = useApiClient();

const handleSignup = handleSubmit(async (values) => {
  try {
    await apiClient.auth.signup(values as SignupRequest);
  } catch (e: any) {
    console.log('[handleSignup] status: ', e.response?.status);
    if (e.response?.status === 409 || e.response?.status === 422) {
      const errors = e.response.data as ModelError;

      errors.errors.forEach(error => {
        if (error.source?.pointer) {
          setFieldError(error.source.pointer, error.title);
        }
      })
    }
  }
});

useSeoMeta({
  title: t('auth.signup'),
  description: t('auth.signupDescription')
})

</script>