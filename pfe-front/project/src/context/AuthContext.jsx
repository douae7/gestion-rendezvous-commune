import { createContext, useContext, useState, useEffect } from "react";

const AuthContext = createContext(null);

export const useAuth = () => useContext(AuthContext);

// 🔥 NORMALIZE ROLE
const normalizeRole = (role) =>
  role?.toString()?.replace("ROLE_", "")?.toLowerCase();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [token, setToken] = useState(null);
  const [loading, setLoading] = useState(true);

  // LOAD LOCAL STORAGE
  useEffect(() => {
    const savedUser = localStorage.getItem("user");
    const savedToken = localStorage.getItem("token");

    if (savedUser && savedToken) {
      const parsed = JSON.parse(savedUser);

      setUser({
        ...parsed,
        role: normalizeRole(parsed.role)
      });

      setToken(savedToken);
    }

    setLoading(false);
  }, []);

  // LOGIN SAVE
  const login = (data, tokenValue = "logged") => {
    const normalizedUser = {
      ...data,
      role: normalizeRole(data.role)
    };

    localStorage.setItem("user", JSON.stringify(normalizedUser));
    localStorage.setItem("token", tokenValue);

    setUser(normalizedUser);
    setToken(tokenValue);
  };

  const logout = () => {
    localStorage.removeItem("user");
    localStorage.removeItem("token");
    setUser(null);
    setToken(null);
  };

  return (
    <AuthContext.Provider
      value={{
        user,
        token,
        loading,
        login,
        logout,
        isAuthenticated: !!token,
        role: user?.role
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};