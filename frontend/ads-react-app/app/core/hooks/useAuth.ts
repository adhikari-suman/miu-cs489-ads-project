import { useEffect, useMemo, useState } from "react";
import { LocalStorageKeys } from "../constants";
import { getCurrentUser, isValidJwtToken, type UserDetails } from "../utils/auth";

export const useAuth = (): UserDetails | null => {
    const [user, setUser] = useState<UserDetails | null>(null);

    useEffect(() => {
        const currentUser = getCurrentUser();
        setUser(currentUser);
    }, []);

    return user;
}