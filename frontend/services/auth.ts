import {
  UserManager,
  type UserManagerSettings,
  Log
} from "oidc-client-ts";

if (process.env.NODE_ENV === 'development') {
  Log.setLogger(console);
  Log.setLevel(Log.DEBUG);
}

export class AuthService {
  private userManager: UserManager;

  constructor(settings: UserManagerSettings) {
    this.userManager = new UserManager(settings);
    //Log.('AuthService', 'constructor', 'UserManager created');
  }
}