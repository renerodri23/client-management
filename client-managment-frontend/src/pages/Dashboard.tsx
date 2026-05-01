import { useAppSelector } from '../app/hooks';

const Dashboard = () => {
  const { role} = useAppSelector((state) => state.auth);

  return (
    <div className="max-w-6xl mx-auto p-6 md:p-10 space-y-8">
      {/* SECCIÓN DE BIENVENIDA */}
      <div className="bg-white border border-gray-200 rounded-xl p-8 shadow-sm">
        <h1 className="text-3xl font-extrabold text-gray-900">
          Panel de Control
        </h1>
        <p className="text-gray-600 mt-2">
          Bienvenido,
          Desde este panel puede gestionar su información personal y visualizar el estado de su cuenta.
        </p>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        {/* COLUMNA PRINCIPAL: ACCESOS RÁPIDOS */}
        <div className="lg:col-span-2 grid grid-cols-1 md:grid-cols-2 gap-6">
          <div className="p-6 bg-white border border-gray-200 rounded-xl hover:border-blue-400 transition-all group">
            <h2 className="text-lg font-bold text-gray-800 group-hover:text-blue-700 transition-colors">
              Información de Perfil
            </h2>
            <p className="text-sm text-gray-500 mt-2">
              Edición de datos básicos, gestión de direcciones residenciales y documentos de identificación fiscal.
            </p>
          </div>

          <div className="p-6 bg-white border border-gray-200 rounded-xl hover:border-green-400 transition-all group">
            <h2 className="text-lg font-bold text-gray-800 group-hover:text-green-700 transition-colors">
              Seguridad y Sesión
            </h2>
            <p className="text-sm text-gray-500 mt-2">
              Estado de autenticación mediante tokens JWT y control de acceso basado en el rol de {role}.
            </p>
          </div>

          {role === 'ADMIN' && (
            <div className="md:col-span-2 p-6 bg-blue-50 border border-blue-100 rounded-xl">
              <h2 className="text-lg font-bold text-blue-900">Privilegios de Administrador</h2>
              <p className="text-sm text-blue-700 mt-1">
                Usted tiene acceso al Panel de Administración para la gestión global de usuarios y auditoría del sistema.
              </p>
            </div>
          )}
        </div>

        {/* PANEL LATERAL: DETALLES DEL PROYECTO */}
        <div className="bg-gray-900 text-white p-8 rounded-xl shadow-lg flex flex-col justify-between">
          <div>
            <h3 className="text-sm font-bold uppercase tracking-widest text-gray-400 mb-6">
              Ficha Técnica
            </h3>
            <p className="text-sm leading-relaxed text-gray-300">
              Sistema Integral de Gestión de Clientes (CMS) desarrollado para el manejo centralizado de identidades y datos de contacto.
            </p>
            
            <ul className="mt-8 space-y-4">
              <li className="flex flex-col">
                <span className="text-xs font-bold text-gray-500 uppercase">Frontend</span>
                <span className="text-sm font-medium">React, TypeScript, Tailwind CSS</span>
              </li>
              <li className="flex flex-col">
                <span className="text-xs font-bold text-gray-500 uppercase">Backend</span>
                <span className="text-sm font-medium">Java, Spring Boot</span>
              </li>
              <li className="flex flex-col">
                <span className="text-xs font-bold text-gray-500 uppercase">DataBase</span>
                <span className="text-sm font-medium">MySQL</span>
              </li>
            </ul>
          </div>
          <div className="mt-10 pt-6 border-t border-gray-800">
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;