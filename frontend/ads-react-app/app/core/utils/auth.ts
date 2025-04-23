import { jwtDecode } from "jwt-decode";
import { LocalStorageKeys } from "../constants";

export enum Role {
  PATIENT = "ROLE_PATIENT",
  OFFICE_ADMIN = "ROLE_OFFICE_ADMIN",
  DENTIST = "ROLE_DENTIST",
  UNDEFINED = "UNDEFINED",
}

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
  role: Role;
  permissions: Set<string>;
}

export const mapToRole = (role: string): Role => {
  switch (role) {
    case Role.PATIENT:
      return Role.PATIENT;
    case Role.OFFICE_ADMIN:
      return Role.OFFICE_ADMIN;
    case Role.DENTIST:
      return Role.DENTIST;
    default:
      return Role.UNDEFINED;
  }
};

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

  const roleStr = authorities.find((auth) => auth.startsWith("ROLE_")) ?? "";

  let role: Role = mapToRole(roleStr);

  switch (roleStr) {
  }

  const permissions = new Set<string>(
    authorities.filter((auth) => auth !== role)
  );

  return Object.freeze({
    username,
    role,
    permissions,
  });
};
