import React, { createContext, useState, useContext, ReactNode } from "react";

interface User {
  email: string;
  token: string;
}

interface UserContextType {
  user: User | null;
  login: (userData: User) => void;
  setEmailOnly: (email: string) => void;
  logout: () => void;
}

const UserContext = createContext<UserContextType | null>(null);

export const useUserContext = () => useContext(UserContext)!;

interface Props {
  children: ReactNode;
}

export const UserProvider: React.FC<Props> = ({ children }) => {
  const [user, setUser] = useState<User | null>(null);

  const login = (userData: User) => {
    setUser(userData);
    localStorage.setItem("jwtToken", userData.token);
  };

  const setEmailOnly = (email: string) => {
    setUser((prevUser) => ({ ...prevUser, email }));
  };

  const logout = () => {
    setUser(null);
    localStorage.removeItem("jwtToken");
  };

  const value = { user, login, setEmailOnly, logout };

  return <UserContext.Provider value={value}>{children}</UserContext.Provider>;
};
