import { ProtectedRoute } from "~/core/components/protected-route";
import { useAuth } from "~/core/hooks/useAuth";

export default function Home(){
    
    const isLoggedIn = useAuth();


    return (
    <ProtectedRoute>
        <h1>Hello world!</h1>
    </ProtectedRoute>
    );
}