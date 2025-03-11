import {
  type UserManagerSettings
} from "oidc-client-ts";

import { AuthService } from "~/services/auth";

export const useAuthService = () => {
  const config = useRuntimeConfig();
  const oidcConfig = config.public.oidc as OidcConfig;

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

  const authService = new AuthService(settings);

  return authService;
}