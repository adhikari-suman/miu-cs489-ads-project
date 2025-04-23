import type React from "react";
import { useAuth } from "../hooks/useAuth";
import { replace, useNavigate } from "react-router";
import { useEffect } from "react";

interface Props {
    children: React.ReactNode
}

export const ProtectedRoute = ({children}:Props)=>{
    const currentUser = useAuth();
    const navigate = useNavigate();

    useEffect(()=>{
        if(currentUser === null){
            navigate('/login', {replace: true});
        }
    },
    [currentUser]
    );

    return <>{children}</>;
};