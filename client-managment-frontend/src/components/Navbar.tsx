import { Link, useNavigate } from 'react-router-dom';
import { useAppDispatch, useAppSelector } from '../app/hooks';
import { logout } from '../features/auth/authSlice';

const Navbar = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  
  const { uuid, role, isAuthenticated } = useAppSelector((state) => state.auth);

  if (!isAuthenticated) return null;

  return (
    <nav className="bg-gray-800 text-white p-4 flex justify-between items-center shadow-lg">
      <div className="flex gap-4 items-center">
        <Link to="/dashboard" className="font-bold text-xl">CMS Client</Link>
        
        {/* Usamos el uuid de Redux para la URL */}
        <Link to={`/profile/${uuid}`} className="hover:text-blue-300">Mi Perfil</Link>
        
        {role === 'ADMIN' && (
          <Link to="/admin" className="bg-blue-600 px-3 py-1 rounded text-sm hover:bg-blue-500">Panel Admin</Link>
        )}
      </div>
      
      <button onClick={() => { dispatch(logout()); navigate('/login'); }} 
        className="bg-red-500 px-3 py-1 rounded text-sm hover:bg-red-600 transition-colors">
        Cerrar Sesión
      </button>
    </nav>
  );
};

export default Navbar;