import { useEffect, useState } from 'react';
import api from '../api/axiosConfig';
import UserDetailAdmin from '../components/UserDetailAdmin';
import AddUserModal from '../components/AddUserModal';

const AdminPage = () => {
  const [users, setUsers] = useState<any[]>([]);
  const [search, setSearch] = useState('');
  const [selectedEmail, setSelectedEmail] = useState<string | null>(null);
  const [showAddModal, setShowAddModal] = useState(false);
  const [loading, setLoading] = useState(false);

  // 1. Cargar lista de usuarios
  const loadUsers = async () => {
    try {
      setLoading(true);
      const res = await api.get('/v1/users');
      setUsers(res.data);
    } catch (error) {
      console.error("Error al cargar usuarios", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => { loadUsers(); }, []);

  // 2. Buscar por Email exacto
  const handleSearch = async () => {
    if (!search) return loadUsers();
    try {
      const res = await api.get(`/v1/users/email/${search}`);
      setUsers([res.data]); // Metemos el resultado único en el array de la tabla
    } catch { 
      alert("Usuario no encontrado con ese email"); 
    }
  };

  // 3. Cambiar estado (Activar/Desactivar)
  const handleToggleStatus = async (uuid: string) => {
    if (window.confirm("¿Seguro que deseas cambiar el estado de este usuario?")) {
      try {
        await api.delete(`/v1/users/${uuid}`);
        loadUsers();
      } catch {
        alert("Error al procesar la solicitud");
      }
    }
  };

  // 4. Descargar Reporte CSV
  const downloadReport = async () => {
    try {
      const response = await api.get('/v1/users/reporte', { responseType: 'blob' });
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', 'reporte_clientes.csv');
      document.body.appendChild(link);
      link.click();
      link.remove();
    } catch {
      alert("Error al descargar el reporte");
    }
  };

  return (
    <div className="p-8 bg-gray-50 min-h-screen font-sans">
      <div className="max-w-7xl mx-auto">
        
        {/* ENCABEZADO Y ACCIONES */}
        <div className="flex flex-col md:flex-row justify-between items-center mb-10 gap-4">
          <div>
            <h1 className="text-3xl font-black text-gray-800 tracking-tight italic">SISTEMA ADMIN</h1>
            <p className="text-gray-500 text-sm">Gestión centralizada de clientes y roles</p>
          </div>
          <div className="flex gap-3">
            <button 
              onClick={downloadReport} 
              className="bg-white border-2 border-green-600 text-green-600 hover:bg-green-600 hover:text-white px-6 py-2.5 rounded-2xl font-bold shadow-sm transition-all flex items-center gap-2 text-sm"
            >
              DESCARGAR CSV
            </button>
            <button 
              onClick={() => setShowAddModal(true)} 
              className="bg-indigo-600 hover:bg-indigo-700 text-white px-6 py-2.5 rounded-2xl font-bold shadow-lg shadow-indigo-200 transition-all text-sm"
            >
              + REGISTRAR CLIENTE
            </button>
          </div>
        </div>

        {/* BUSCADOR ESTILO MINIMALISTA */}
        <div className="flex gap-3 mb-8 bg-white p-3 rounded-2xl shadow-sm border border-gray-100">
          <div className="flex-1 flex items-center px-3">
            <span className="text-gray-400 mr-2"></span>
            <input 
              className="w-full bg-transparent border-none focus:ring-0 text-gray-700 outline-none" 
              placeholder="Buscar por email del cliente..." 
              value={search} 
              onChange={e => setSearch(e.target.value)}
              onKeyPress={(e) => e.key === 'Enter' && handleSearch()}
            />
          </div>
          <button onClick={handleSearch} className="bg-gray-900 hover:bg-black text-white px-8 py-2 rounded-xl font-bold transition-all text-sm">
            BUSCAR
          </button>
        </div>

        {/* TABLA PRINCIPAL */}
        <div className="bg-white shadow-2xl rounded-3xl overflow-hidden border border-gray-100">
          <table className="w-full text-left border-collapse">
            <thead className="bg-gray-50/50 border-b border-gray-100">
              <tr>
                <th className="p-5 text-[10px] font-black text-gray-400 uppercase tracking-[0.2em]">Nombre Completo</th>
                <th className="p-5 text-[10px] font-black text-gray-400 uppercase tracking-[0.2em]">Email</th>
                <th className="p-5 text-[10px] font-black text-gray-400 uppercase tracking-[0.2em] text-center">Rol</th>
                <th className="p-5 text-[10px] font-black text-gray-400 uppercase tracking-[0.2em] text-center">Estado</th>
                <th className="p-5 text-[10px] font-black text-gray-400 uppercase tracking-[0.2em] text-right">Opciones</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-50">
              {loading ? (
                <tr><td colSpan={5} className="p-20 text-center font-bold text-gray-300 animate-pulse">Cargando base de datos...</td></tr>
              ) : users.length === 0 ? (
                <tr><td colSpan={5} className="p-20 text-center font-bold text-gray-400">No hay usuarios registrados.</td></tr>
              ) : (
                users.map((u) => (
                  <tr key={u.uuid} className="hover:bg-indigo-50/30 transition-colors">
                    <td className="p-5">
                      <p className="font-bold text-gray-800">{u.nombre} {u.apellido}</p>
                      <p className="text-[10px] text-gray-400 font-mono">{u.uuid}</p>
                    </td>
                    <td className="p-5 text-sm text-gray-600">{u.email}</td>
                    <td className="p-5 text-center">
                      <span className={`px-3 py-1 rounded-full text-[9px] font-black border ${u.role === 'ADMIN' ? 'bg-amber-50 text-amber-600 border-amber-200' : 'bg-blue-50 text-blue-600 border-blue-200'}`}>
                        {u.role}
                      </span>
                    </td>
                    <td className="p-5 text-center">
                      <span className={`inline-flex items-center gap-1.5 px-3 py-1 rounded-full text-[10px] font-bold ${u.active ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700'}`}>
                        <span className={`w-1.5 h-1.5 rounded-full ${u.active ? 'bg-green-500' : 'bg-red-500'}`}></span>
                        {u.active ? 'ACTIVO' : 'INACTIVO'}
                      </span>
                    </td>
                    <td className="p-5 text-right space-x-2">
                      <button 
                        onClick={() => setSelectedEmail(u.email)}
                        className="bg-indigo-50 text-indigo-600 hover:bg-indigo-600 hover:text-white px-4 py-2 rounded-xl font-bold text-[10px] transition-all"
                      >
                        DETALLES
                      </button>
                      <button 
                        onClick={() => handleToggleStatus(u.uuid)} 
                        className={`px-4 py-2 rounded-xl text-[10px] font-bold transition-all ${u.active ? 'bg-red-50 text-red-600 hover:bg-red-600 hover:text-white' : 'bg-green-50 text-green-600 hover:bg-green-600 hover:text-white'}`}
                      >
                        {u.active ? 'DESACTIVAR' : 'ACTIVAR'}
                      </button>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      </div>

      {/* MODAL 1: REGISTRO COMPLETO (Multidirección / Multidocumento) */}
      {showAddModal && (
        <AddUserModal 
          onClose={() => setShowAddModal(false)} 
          onUserCreated={loadUsers} 
        />
      )}

      {/* MODAL 2: DETALLES Y EDICIÓN */}
      {selectedEmail && (
        <div className="fixed inset-0 bg-black/60 backdrop-blur-md flex items-center justify-center z-50 p-4">
          <div className="w-full max-w-4xl max-h-[90vh] overflow-hidden shadow-2xl rounded-3xl animate-in fade-in zoom-in duration-200">
            <UserDetailAdmin 
              userEmail={selectedEmail} 
              onClose={() => {
                setSelectedEmail(null)
              }}
              onUserUpdated={loadUsers}
 
            />
          </div>
        </div>
      )}
    </div>
  );
};

export default AdminPage;