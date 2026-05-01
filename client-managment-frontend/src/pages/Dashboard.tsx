
const Dashboard = () => {
  return (
    <div className="p-10 text-center">
      <h1 className="text-3xl font-bold">¡Bienvenido!</h1> 
      <p className="mt-4 text-gray-600">Usa la barra superior para navegar por tu perfil o gestionar tus datos.</p> 
      <div className="mt-10 grid grid-cols-1 md:grid-cols-2 gap-6 max-w-2xl mx-auto">
        <div className="p-6 bg-white shadow rounded-lg border-t-4 border-blue-500">
          <h2 className="font-bold">Mi Perfil</h2>
          <p className="text-sm text-gray-500">Actualiza tu información personal y direcciones.</p>
        </div>
        <div className="p-6 bg-white shadow rounded-lg border-t-4 border-green-500">
          <h2 className="font-bold">Seguridad</h2>
          <p className="text-sm text-gray-500">Tu cuenta está protegida con autenticación JWT.</p>
        </div>
      </div>
    </div>
  );
};
export default Dashboard;