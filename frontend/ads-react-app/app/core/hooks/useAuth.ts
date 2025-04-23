import { useEffect, useMemo, useState } from "react";
import { LocalStorageKeys } from "../constants";
import {
  getCurrentUser,
  isValidJwtToken,
  type UserDetails,
} from "../utils/auth";

interface AuthStatus {
  currentUser: UserDetails | null;
  isLoading: boolean;
}

export const useAuth = (): AuthStatus => {
  const [user, setUser] = useState<UserDetails | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const currentUser = getCurrentUser();
    setUser(currentUser);
    setLoading(false);
  }, []);

  return { currentUser: user, isLoading: loading };
};
