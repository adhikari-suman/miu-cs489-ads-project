import { useMemo } from "react";
import { LocalStorageKeys } from "../constants";




const useAuth = () => {
    const jwtToken: string | null = localStorage.getItem(LocalStorageKeys.JWT_TOKEN_KEY);

    const isLoggedIn = useMemo(
        () => {

            return jwtToken === null;



        }, [jwtToken]
    );

    return {
        isLoggedIn,
        token: jwtToken
    };

}

export default useAuth;