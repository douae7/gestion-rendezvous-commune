import { Navigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const ProtectedRoute = ({ children, allowedRoles }) => {
  const { user, isAuthenticated, loading } = useAuth();

  if (loading) return null;

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  const role = user?.role;

  console.log(" ROLE USER:", role);
  console.log(" ALLOWED:", allowedRoles);

  if (allowedRoles && !allowedRoles.includes(role)) {
    const redirectMap = {
      admin: "/admin/dashboard",
      agent: "/agent/dashboard",
      citizen: "/citizen/dashboard"
    };

    return <Navigate to={redirectMap[role] || "/login"} replace />;
  }

  return children;
};

export default ProtectedRoute;