import { BrowserRouter, Routes, Route, Navigate, useLocation } from 'react-router-dom';
import { useAppSelector } from '../app/hooks';
import Login from '../pages/Login'; 
import Register from '../pages/Register';
import AdminPage from '../pages/AdminPage';
import ProfilePage from '../pages/ProfilePage'; 
import Dashboard from '../pages/Dashboard';
import Navbar from '../components/Navbar'; 
import type { JSX } from 'react';

const PrivateRoute = ({ children, roleRequired }: { children: JSX.Element, roleRequired?: string }) => {
  const { isAuthenticated, role } = useAppSelector((state) => state.auth); 
  if (!isAuthenticated) return <Navigate to="/login" />; 
  if (roleRequired && role !== roleRequired) return <Navigate to="/dashboard" />;
  return children;
};

const AppContent = () => {
  const location = useLocation();
  
  const hideNavbar = ['/login', '/register'].includes(location.pathname);

  return (
    <>
      {!hideNavbar && <Navbar />}
      
      <Routes>
        <Route path="/login" element={<Login />} /> 
        <Route path="/register" element={<Register />} /> 
        
        <Route path="/profile/:id" element={
          <PrivateRoute>
            <ProfilePage />
          </PrivateRoute>
        } />

        <Route path="/dashboard" element={
          <PrivateRoute>
            <Dashboard />
          </PrivateRoute>
        } /> 

        <Route path="/admin" element={
          <PrivateRoute roleRequired="ADMIN">
            <AdminPage />
          </PrivateRoute>
        } /> 

        <Route path="*" element={<Navigate to="/login" />} /> 
      </Routes>
    </>
  );
};

export const AppRouter = () => {
  return (
    <BrowserRouter> 
      <AppContent />
    </BrowserRouter>
  );
};