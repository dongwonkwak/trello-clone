import {
  type UserManagerSettings
} from "oidc-client-ts";

import { AuthService } from "~/services/auth";
import {useAuthStore} from "~/stores/auth";

export const useAuthService = () => {
  const config = useRuntimeConfig();
  const oidcConfig = config.public.oidc as OidcConfig;
  const store = useAuthStore();

  const settings: UserManagerSettings = {
    authority: oidcConfig.authority,
    client_id: oidcConfig.clientId,
    client_secret: oidcConfig.clientSecret,
    redirect_uri: oidcConfig.redirectUri,
    response_type: oidcConfig.responseType,
    silent_redirect_uri: oidcConfig.silentRedirectUri,
    scope: oidcConfig.scope,
    post_logout_redirect_uri: oidcConfig.postLogoutRedirectUri,
    client_authentication: oidcConfig.clientAuthentication,
    fetchRequestCredentials: 'include'
  }

  console.log(settings);

  const authService = new AuthService(settings);
  authService.userLoaded((user) => {
    console.log("[AuthService] user loaded");
    store.setUser(user);
    if (user?.url_state) {
      window.location.href = user?.url_state;
    }
  });
  authService.userUnloaded(() => {
    console.log("[AuthService] user unloaded");
    store.setUser(null);
  });

  return authService;
}