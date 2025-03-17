<script setup lang="ts">

</script>

<template>
  <div class="bg-gray-100 rounded-lg p-8 mt-4">
    <section class="bg-blue-50 py-20">
      <div class="container mx-auto px-4 text-center">
        <h1 class="text-4xl md:text-5xl font-extrabold text-gray-900 mb-6">
          당신의 비즈니스를 위한 최고의 솔루션
        </h1>
        <p class="text-xl text-gray-600 mb-8 max-w-3xl mx-auto">
          간편하게 시작하고, 효율적으로 관리하세요. 사용하기 쉬운 대시보드로 모든 것을 한눈에 확인할 수 있습니다.
        </p>
        <div class="flex flex-col sm:flex-row justify-center gap-4">
          <NuxtLink to="/signup" class="px-6 py-3 bg-blue-600 text-white font-medium rounded-md hover:bg-blue-700 transition">
            무료로 시작하기
          </NuxtLink>
          <a href="#features" class="px-6 py-3 bg-white text-blue-600 font-medium rounded-md border border-blue-600 hover:bg-blue-50 transition">
            자세히 알아보기
          </a>
        </div>
      </div>
    </section>
  </div>
</template>

<style scoped>

</style>