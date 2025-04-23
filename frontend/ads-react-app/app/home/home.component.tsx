import { ProtectedRoute } from "~/core/components/protected-route.component";
import { useAuth } from "~/core/hooks/useAuth";

export default function Home() {
  return (
    <ProtectedRoute>
      <h1>Hello world!</h1>
    </ProtectedRoute>
  );
}
