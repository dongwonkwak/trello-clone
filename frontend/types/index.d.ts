declare global {
  interface OidcConfig {
    authority: string;
    clientId: string;
    clientSecret: string;
    redirectUri: string;
    responseType: string;
    scope: string;
    postLogoutRedirectUri: string;
    silentRedirectUri: string;
    automaticSilentRenew: boolean;
    disablePKCE: boolean;
    clientAuthentication: "client_secret_basic" | "client_secret_post" | undefined;
  }
}

export {};