import { createContext, useContext, useState, useEffect } from "react";

const ThemeContext = createContext();

export const useTheme = () => {
  const context = useContext(ThemeContext);
  if (!context) {
    throw new Error("useTheme must be used within a ThemeProvider");
  }
  return context;
};

export const ThemeProvider = ({ children }) => {
  const [darkMode, setDarkMode] = useState(() => {
    // Récupérer la préférence depuis localStorage ou système
    if (typeof window !== "undefined") {
      return localStorage.getItem("darkMode") === "true" || 
             (!localStorage.getItem("darkMode") && window.matchMedia("(prefers-color-scheme: dark)").matches);
    }
    return true; // Par défaut dark mode
  });

  // Sauvegarder dans localStorage
  useEffect(() => {
    localStorage.setItem("darkMode", darkMode);
    if (darkMode) {
      document.documentElement.classList.add("dark");
    } else {
      document.documentElement.classList.remove("dark");
    }
  }, [darkMode]);

  // Écouter les changements de préférence système
  useEffect(() => {
    const mediaQuery = window.matchMedia("(prefers-color-scheme: dark)");
    const handleChange = () => {
      if (!localStorage.getItem("darkMode")) {
        setDarkMode(mediaQuery.matches);
      }
    };
    mediaQuery.addEventListener("change", handleChange);
    return () => mediaQuery.removeEventListener("change", handleChange);
  }, []);

  const toggleTheme = () => {
    setDarkMode((prev) => !prev);
  };

  return (
    <ThemeContext.Provider value={{ darkMode, toggleTheme, setDarkMode }}>
      {children}
    </ThemeContext.Provider>
  );
};