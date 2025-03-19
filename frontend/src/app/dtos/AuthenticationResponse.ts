import { UserData } from "./UserData";

export interface AuthenticationResponse {
    token: string;
    userData: UserData;
}