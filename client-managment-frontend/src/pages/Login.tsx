import React, { useState } from 'react';
import api from '../api/axiosConfig';
import { useAppDispatch } from '../app/hooks';
import { setCredentials } from '../features/auth/authSlice';
import { useNavigate, Link } from 'react-router-dom';

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState<string | null>(null);
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);
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
      setError("Credenciales incorrectas. Intenta de nuevo.");
    }
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-50 px-4">
      {/* Tarjeta Contenedora */}
      <div className="w-full max-w-sm bg-white p-8 rounded-lg shadow-lg border border-gray-200">
        
        <div className="text-center mb-8">
          <h2 className="text-2xl font-bold text-gray-900">Iniciar Sesión</h2>
          <p className="text-sm text-gray-500 mt-1">Ingresa al sistema de gestión</p>
        </div>

        <form onSubmit={handleSubmit} className="max-w-sm mx-auto">
          
          {/* Email Input*/}
          <div className="mb-5">
            <label htmlFor="email" className="block mb-2 text-sm font-medium text-gray-900">Tu email</label>
            <input 
              type="email" 
              id="email" 
              value={email}
              onChange={e => setEmail(e.target.value)}
              className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 shadow-sm placeholder:text-gray-400" 
              placeholder="name@example.com" 
              required 
            />
          </div>

          {/* Password Input*/}
          <div className="mb-5">
            <label htmlFor="password" className="block mb-2 text-sm font-medium text-gray-900">Tu contraseña</label>
            <input 
              type="password" 
              id="password" 
              value={password}
              onChange={e => setPassword(e.target.value)}
              className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 shadow-sm placeholder:text-gray-400" 
              placeholder="••••••••" 
              required 
            />
          </div>

          {/* Manejo de Errores*/}
          {error && (
            <div className="mb-5">
              <div className="bg-red-50 border border-red-300 text-red-800 text-xs rounded-lg p-3">
                <span className="font-bold">¡Error!</span> {error}
              </div>
            </div>
          )}

          {/* Checkbox*/}
          <div className="flex items-start mb-5">
            <div className="flex items-center h-5">
              <input 
                id="remember" 
                type="checkbox" 
                className="w-4 h-4 border border-gray-300 rounded bg-gray-50 focus:ring-2 focus:ring-blue-200" 
              />
            </div>
            <label htmlFor="remember" className="ms-2 text-sm font-medium text-gray-900 select-none">
              Acepto los <a href="#" className="text-blue-600 hover:underline">términos y condiciones</a>
            </label>
          </div>

          {/* Submit Button*/}
          <button 
            type="submit" 
            className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full px-5 py-2.5 text-center shadow-sm transition-all"
          >
            Entrar
          </button>

          <p className="mt-4 text-sm text-center text-gray-500">
            ¿No tienes cuenta? <Link to="/register" className="text-blue-600 font-medium hover:underline">Regístrate aquí</Link>
          </p>
        </form>
      </div>
    </div>
  );
};

export default Login;