// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  compatibilityDate: '2024-11-01',
  devtools: { enabled: true },
  modules: [
      '@nuxtjs/i18n',
      '@pinia/nuxt',
  ],
  ssr: false,
  typescript: {
    strict: true,
    typeCheck: true
  },
  i18n: {
    locales: [
      {
        code: 'en',
        iso: 'en-US',
        name: 'English',
        file: 'en-US.json',
        dir: 'ltr',
      },
      {

        code: 'ko',
        iso: 'ko-KR',
        name: '한국어',
        file: 'ko-KR.json',
        dir: 'ltr',
      }
    ],
    strategy: 'prefix_except_default',
    lazy: true,
    langDir: 'locales/',
    vueI18n: 'i18n.config.ts',
    restructureDir: false,
  },
  runtimeConfig: {
    public: {
      apiBaseUrl: process.env.API_BASE_URL || 'http://localhost:8080',
      oidc: {
        authority: process.env.OIDC_AUTHORITY || 'http://localhost:8080',
        clientId: process.env.OIDC_CLIENT_ID || 'trello-client',
        redirectUri: process.env.OIDC_REDIRECT_URI || 'http://localhost:3000/authorized',
        responseType: process.env.OIDC_RESPONSE_TYPE || 'code',
        scope: process.env.OIDC_SCOPE || 'openid profile email read write',
        postLogoutRedirectUri: process.env.OIDC_POST_LOGOUT_REDIRECT_URI || 'http://localhost:3000/logout',
        automaticSilentRenew: (process.env.OIDC_AUTOMATIC_SILENT_RENEW === 'true') || true,
        silentRedirectUri: process.env.OIDC_SILENT_REDIRECT_URI || 'http://localhost:3000/silent-renew',
        disablePKCE: (process.env.OIDC_DISABLE_PKCE === 'true'),
        clientSecret: process.env.OIDC_CLIENT_SECRET || 'secret',
        clientAuthentication: process.env.OIDC_CLIENT_AUTH_METHOD || 'client_secret_post'
      }
    }
  }
})