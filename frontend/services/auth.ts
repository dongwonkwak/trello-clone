import {
  UserManager,
  type UserManagerSettings,
  type SigninRedirectArgs,
  Log, User, UserManagerEvents
} from "oidc-client-ts";

if (process.env.NODE_ENV === 'development') {
  Log.setLogger(console);
  Log.setLevel(Log.DEBUG);
}

export class AuthService {
  private userManager: UserManager;

  constructor(settings: UserManagerSettings) {
    this.userManager = new UserManager(settings);
  }

  public login = async (args?: SigninRedirectArgs): Promise<void> => {
    try {
      console.log("[AuthService] login");
      await this.userManager.signinRedirect(args);
    } catch (error) {
      console.error("[AuthService] login error", error);
      throw error;
    }
  }

  public loginCallback = async (url?: string): Promise<User | undefined> => {
    try {
      console.log("[AuthService] loginCallback");
      const user = await this.userManager.signinCallback(url);
      return user;
    } catch (error) {
      console.error("[AuthService] loginCallback error", error);
      throw error;
    }
  }

  public logout = async (): Promise<void> => {
    try {
      await this.userManager.signoutRedirect();
    } catch (error) {
      console.error("[AuthService] logout error", error);
      throw error;
    }
  }

  public silentRenew = async (): Promise<User | null> => {
    try {
      console.log("[AuthService] silentRenew");
      return await this.userManager.signinSilent();
    } catch (error) {
      console.error("[AuthService] silentRenew error", error);
      throw error;
    }
  }

  public getUser = async (): Promise<User | null> => {
    try {
      console.log("[AuthService] getUser");
      return await this.userManager.getUser();
    } catch (error) {
      console.error("[AuthService] getUser error", error);
      return null;
    }
  }

  public events = (): UserManagerEvents => {
    return this.userManager.events;
  }
}