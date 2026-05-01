import { Link, useNavigate } from 'react-router-dom';
import { useAppDispatch, useAppSelector } from '../app/hooks';
import { logout } from '../features/auth/authSlice';

const Navbar = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  
  const { uuid, role, isAuthenticated } = useAppSelector((state) => state.auth);

  if (!isAuthenticated) return null;

  return (
    <nav className="bg-white border-b border-gray-200 shadow-sm font-sans">
      {/* Contenedor con el max-width y padding de Flowbite */}
      <div className="max-w-screen-xl flex flex-wrap items-center justify-between mx-auto p-4">
        
        {/* Lado Izquierdo: Logo + Links */}
        <div className="flex items-center gap-8">
          {/* Placeholder para tu Imagen/Logo */}
          <Link to="/dashboard" className="flex items-center space-x-3">
            <div className="h-8 w-8 bg-indigo-600 rounded-lg flex items-center justify-center text-white font-bold">
              {/* <img src="logo.svg" className="h-8" alt="Logo" /> */}
              C
            </div>
            <span className="self-center text-xl font-semibold tracking-tight text-gray-900">
              CMS Client
            </span>
          </Link>

          {/* Links de navegación con la fuente y color de Flowbite */}
          <div className="hidden md:flex items-center gap-6">
            <Link 
              to={`/profile/${uuid}`} 
              className="text-sm font-medium text-gray-600 hover:text-indigo-600 transition-colors"
            >
              Mi Perfil
            </Link>

            {role === 'ADMIN' && (
              <Link 
                to="/admin" 
                className="text-sm font-bold text-white bg-indigo-600 px-4 py-2 rounded-lg hover:bg-indigo-700 transition-all shadow-sm"
              >
                Panel Admin
              </Link>
            )}
          </div>
        </div>

        {/* Lado Derecho: Botón de Cerrar Sesión */}
        <div className="flex items-center">
          <button 
            onClick={() => { dispatch(logout()); navigate('/login'); }} 
            className="text-sm font-bold text-red-600 hover:text-white border border-red-600 hover:bg-red-600 px-4 py-2 rounded-lg transition-all"
          >
            Cerrar Sesión
          </button>
        </div>

      </div>
    </nav>
  );
};
export default Navbar;