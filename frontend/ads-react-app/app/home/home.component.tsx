import { ProtectedRoute } from "~/core/components/protected-route.component";
import { useAuth } from "~/core/hooks/useAuth";
import { Role } from "~/core/utils/auth";
import PatientDashboard from "./patient-dashboard.component";

export default function Home() {
  let { currentUser, isLoading } = useAuth();

  if (isLoading) {
    return <>Loading...</>;
  }

  const role = currentUser?.role;

  return (
    <ProtectedRoute>
      {role === Role.PATIENT && <PatientDashboard />}
      {role === Role.DENTIST && <h1>Dentist Dashboard</h1>}
      {role === Role.OFFICE_ADMIN && <h1>Office Admin Dashboard</h1>}
    </ProtectedRoute>
  );
}
