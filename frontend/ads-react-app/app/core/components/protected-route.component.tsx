import type React from "react";
import { useAuth } from "../hooks/useAuth";
import { useNavigate } from "react-router";
import { useEffect } from "react";

interface Props {
  children: React.ReactNode;
}

export const ProtectedRoute = ({ children }: Props) => {
  const { currentUser, isLoading } = useAuth();
  const navigate = useNavigate();

  console.log(currentUser);

  useEffect(() => {
    if (!isLoading && !currentUser) {
      navigate("/login", { replace: true });
    }
  }, [currentUser, isLoading, navigate]);

  return <>{children}</>;
};
