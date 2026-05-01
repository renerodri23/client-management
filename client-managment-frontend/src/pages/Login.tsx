import React, { useState } from 'react';
import api from '../api/axiosConfig';
import { useAppDispatch } from '../app/hooks';
import { setCredentials } from '../features/auth/authSlice';
import { useNavigate, Link } from 'react-router-dom';

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const res = await api.post('/auth/login', { email, password });
      const { token } = res.data;
      
      const userRes = await api.get(`/v1/users/email/${email}`, {
        headers: { Authorization: `Bearer ${token}` }
      });
      
      const { role, userId } = userRes.data;
      dispatch(setCredentials({ token, email, role, userId }));
      navigate(role === 'ADMIN' ? '/admin' : '/dashboard');
    } catch (error) {
      alert("Error en login: Revisa tus credenciales");
    }
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100">
      <form onSubmit={handleSubmit} className="p-8 bg-white shadow-lg rounded-xl w-96">
        <h2 className="mb-6 text-2xl font-bold text-center text-gray-800">Bienvenido</h2>
        <input type="email" placeholder="Email" className="w-full p-2 mb-4 border rounded" 
          onChange={e => setEmail(e.target.value)} required />
        <input type="password" placeholder="Password" className="w-full p-2 mb-4 border rounded" 
          onChange={e => setPassword(e.target.value)} required />
        <button className="w-full bg-blue-600 text-white py-2 rounded font-bold hover:bg-blue-700">Entrar</button>
        <p className="mt-4 text-sm text-center">
          ¿No tienes cuenta? <Link to="/register" className="text-blue-500 hover:underline">Regístrate aquí</Link>
        </p>
      </form>
    </div>
  );
};
export default Login;