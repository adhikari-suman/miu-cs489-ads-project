import { jwtDecode } from "jwt-decode";
import { LocalStorageKeys } from "../constants";

interface ApiJwtPayload {
  iss: string;
  iat: number;
  exp: number;
  sub: string;
  user_id: number;
  authorities: string; // Comma-separated string
}

export interface UserDetails {
  username: string;
  role: string;
  permissions: Set<string>;
}

export const isValidJwtToken = (token: string | null): boolean => {
  if (typeof token !== "string" || token.trim() === "") {
    return false;
  }

  let decodedToken: ApiJwtPayload;

  try {
    decodedToken = jwtDecode<ApiJwtPayload>(token);
  } catch (err) {
    console.error("Failed to decode token:", err);
    return false;
  }

  if (!decodedToken.exp || typeof decodedToken.exp !== "number") {
    return false;
  }

  const expiryTime = new Date(decodedToken.exp * 1000);
  const currentTime = new Date();

  return currentTime < expiryTime;
};

export const getCurrentUser = (): UserDetails | null => {
  const token = localStorage.getItem(LocalStorageKeys.JWT_TOKEN_KEY);

  console.log("Token:" + token);

  if (!isValidJwtToken(token)) {
    return null;
  }

  console.log("Token:" + token);

  const decodedToken = jwtDecode<ApiJwtPayload>(token!);
  const username = decodedToken.sub ?? "";

  const authorities = (decodedToken.authorities ?? "").split(",");

  const role = authorities.find((auth) => auth.startsWith("ROLE_")) ?? "";
  const permissions = new Set<string>(
    authorities.filter((auth) => auth !== role)
  );

  return Object.freeze({
    username,
    role,
    permissions,
  });
};
