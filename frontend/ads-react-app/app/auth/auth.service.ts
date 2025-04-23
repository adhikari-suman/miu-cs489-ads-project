import { AxiosError, HttpStatusCode } from "axios";
import apiRoutes from "~/core/api";
import { LocalStorageKeys } from "~/core/constants";
import apiClient from "~/core/utils/axios";

interface AuthResponse {
    accessToken: string;
}

export interface Address {
    location: string;
}

export interface RegisterPayload {
    firstName: string;
    lastName: string;
    username: string;
    password: string;
    phoneNumber: string;
    email: string;
    patientNo: string;
    dateOfBirth: string;
    address: Address;
}

export interface RegisterError {
    firstName?: string;
    lastName?: string;
    username?: string;
    password?: string;
    phoneNumber?: string;
    email?: string;
    patientNo?: string;
    dateOfBirth?: string;
    address?: string;
    [key: string]: string | undefined; // Optional fallback for any dynamic fields
}

export interface RegisterResult {
    success: boolean;
    errors?: RegisterError;
}

const loginUser = async (username: string, password: string) => {
    try {
        let response = await apiClient.post(apiRoutes.auth.login, {
            username,
            password,
        });

        let loginResponse: AuthResponse = response.data;

        localStorage.setItem(
            LocalStorageKeys.JWT_TOKEN_KEY,
            loginResponse.accessToken
        );

        return true;
    } catch (e) {
        return false;
    }
};

const registerAsNewPatient = async (
    userData: RegisterPayload
): Promise<RegisterResult> => {
    try {
        const response = await apiClient.post(apiRoutes.auth.register, userData);

        if (response.status === HttpStatusCode.Created) {
            const registerResponse: AuthResponse = response.data;
            localStorage.setItem(
                LocalStorageKeys.JWT_TOKEN_KEY,
                registerResponse.accessToken
            );
            return { success: true };
        }

        return { success: false };
    } catch (error) {
        if ((error as AxiosError).response?.status === HttpStatusCode.BadRequest) {
            const axiosError = error as AxiosError<{ errors: RegisterError }>;
            const errorData = axiosError.response?.data;
            return { success: false, errors: errorData?.errors };
        }

        console.error("Unexpected registration error:", error);
        return { success: false };
    }
};

interface AuthService {
    loginUser: (username: string, password: string) => Promise<boolean>;
    registerAsNewPatient: (data: RegisterPayload) => Promise<RegisterResult>;
}

const authService: AuthService = {
    loginUser,
    registerAsNewPatient,
};

export default authService;
