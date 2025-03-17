import {AccountApi, AuthApi, Configuration} from "~/api";

export const useApiClient = (accessToken?: string) => {
  const config = useRuntimeConfig();
  const apiBaseUrl = config.public.apiBaseUrl as string;

  const configuration = new Configuration({
    basePath: apiBaseUrl,
    accessToken: accessToken ? `Bearer ${accessToken}` : undefined,
  });

  return {
    account: new AccountApi(configuration),
    auth: new AuthApi(configuration),
  }
}