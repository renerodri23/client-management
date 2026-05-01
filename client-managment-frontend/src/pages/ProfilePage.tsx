import React, { useEffect, useState } from 'react';
import api from '../api/axiosConfig';
import { useAppSelector, useAppDispatch } from '../app/hooks';
import { logout } from '../features/auth/authSlice';
import { useNavigate } from 'react-router-dom';
import { useAlert } from '../context/AlertContext';

const ProfilePage = ({ overrideUuid }: { overrideUuid?: string }) => {
  const { uuid: myUuid } = useAppSelector((state) => state.auth);
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { showAlert } = useAlert();

  const targetUuid = overrideUuid || myUuid;
  const isAdminViewingOthers = !!overrideUuid && overrideUuid !== myUuid;

  const [userData, setUserData] = useState<any>(null);
  const [loading, setLoading] = useState(true);
  const [isEditing, setIsEditing] = useState(false);

  const [newAddress, setNewAddress] = useState({ calle: '', ciudad: '', departamento: '', tipo: 'CASA' });
  const [newDoc, setNewDoc] = useState({ tipo: 'DUI', valor: '' });

  const fetchUserData = async () => {
    if (!targetUuid) return;
    try {
      setLoading(true);
      const res = await api.get(`/v1/users/${targetUuid}`);
      setUserData(res.data);
    } catch (error) {
      console.error("Error al cargar perfil", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => { fetchUserData(); }, [targetUuid]);

  const handleUpdateUser = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await api.put(`/v1/users/${targetUuid}`, {
        nombre: userData.nombre,
        apellido: userData.apellido,
        email: userData.email,
        role: userData.role
      });
      setIsEditing(false);
      showAlert("Perfil actualizado correctamente", 'success');
    } catch (error) { 
      showAlert("Error al actualizar: No tienes permisos o datos inválidos", "danger"); 
    }
  };

  const handleAddAddress = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await api.post(`/v1/users/${targetUuid}/addresses`, newAddress);
      setNewAddress({ calle: '', ciudad: '', departamento: '', tipo: 'CASA' });
      fetchUserData();
    } catch { showAlert("Error al agregar dirección", "danger"); }
  };

  const handleAddDocument = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await api.post(`/v1/users/${targetUuid}/documents`, newDoc);
      setNewDoc({ tipo: 'DUI', valor: '' });
      fetchUserData();
    } catch { showAlert("Error al agregar documento","danger"); }
  };

  const deleteAddress = async (addressUuid: string) => {
    if (window.confirm("¿Quitar dirección?")) {
      try {
        await api.delete(`/v1/users/${targetUuid}/addresses/${addressUuid}`);
        fetchUserData();
      } catch { showAlert("No se pudo eliminar la dirección","danger"); }
    }
  };

  const deleteDoc = async (documentUuid: string) => {
    if (window.confirm("¿Quitar documento?")) {
      try {
        await api.delete(`/v1/users/${targetUuid}/documents/${documentUuid}`);
        fetchUserData();
      } catch { showAlert("No se pudo eliminar el documento","danger"); }
    }
  };

  const handleDeactivate = async () => {
    if (!targetUuid) {
      showAlert("Error: No se pudo identificar el usuario.","danger");
      return;
    }

    const msg = isAdminViewingOthers 
      ? `¿Seguro que deseas desactivar la cuenta de ${userData?.nombre || 'este usuario'}?` 
      : "¿Seguro que deseas desactivar TU cuenta? Se cerrará la sesión de forma inmediata.";
    
    if (window.confirm(msg)) {
      try {
        // El Backend ahora usa @securityService.isOwner(authentication, #id)
        // por lo que el UUID es lo único que necesitamos enviar.
        await api.delete(`/v1/users/${targetUuid}`);
        
        if (!isAdminViewingOthers) {
          showAlert("Tu cuenta ha sido desactivada correctamente.","success");
          dispatch(logout());
          navigate('/login');
        } else {
          showAlert("Cuenta de usuario desactivada","success");
          fetchUserData(); 
        }
      } catch (error: any) {
        console.error("Error al desactivar:", error.response?.data);
        showAlert("Error: " + (error.response?.data?.message || "No autorizado"), "danger");
      }
    }
  };

  if (loading && !userData) return <div className="p-10 text-center font-bold">Cargando perfil...</div>;

  return (
    <div className={`max-w-4xl mx-auto p-8 bg-white shadow-xl rounded-xl border border-gray-200 ${isAdminViewingOthers ? 'mt-2' : 'mt-10 mb-20'}`}>
      
      {/* CABECERA */}
      <div className="flex justify-between items-center border-b pb-4 mb-8">
        <div>
          <h1 className="text-2xl font-bold text-gray-800">
            {isAdminViewingOthers ? `Editando a: ${userData.nombre}` : 'Mi Perfil'}
          </h1>
          {isAdminViewingOthers && <span className="text-xs font-bold text-blue-500 uppercase">Modo Administrador</span>}
        </div>
        <button 
          onClick={() => setIsEditing(!isEditing)}
          className={`px-4 py-2 rounded font-bold transition-all ${isEditing ? 'bg-gray-200 text-gray-700' : 'bg-blue-600 text-white hover:bg-blue-700'}`}
        >
          {isEditing ? 'Cancelar edición' : 'Editar Perfil'}
        </button>
      </div>

      {/* DATOS PERSONALES */}
      <section className="mb-10">
        <h2 className="text-lg font-bold text-gray-700 mb-4 uppercase tracking-wider">Información Básica</h2>
        {!isEditing ? (
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6 bg-gray-50 p-6 rounded-lg border border-gray-100">
            <div><p className="text-xs font-bold text-gray-400">NOMBRE</p><p className="text-gray-800">{userData.nombre} {userData.apellido}</p></div>
            <div><p className="text-xs font-bold text-gray-400">CORREO</p><p className="text-gray-800">{userData.email}</p></div>
          </div>
        ) : (
          <form onSubmit={handleUpdateUser} className="space-y-4 bg-blue-50 p-6 rounded-lg">
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <input className="p-2 border rounded" value={userData.nombre} onChange={e => setUserData({...userData, nombre: e.target.value})} placeholder="Nombre" />
              <input className="p-2 border rounded" value={userData.apellido} onChange={e => setUserData({...userData, apellido: e.target.value})} placeholder="Apellido" />
            </div>
            <input className="w-full p-2 border rounded" value={userData.email} onChange={e => setUserData({...userData, email: e.target.value})} placeholder="Email" />
            <button className="bg-blue-700 text-white px-6 py-2 rounded font-bold">Guardar Cambios</button>
          </form>
        )}
      </section>

      {/* DIRECCIONES */}
      <section className="mb-10">
        <h2 className="text-lg font-bold text-gray-700 mb-4 uppercase tracking-wider">Direcciones</h2>
        <div className="space-y-3 mb-4">
          {userData?.direcciones?.map((dir: any) => (
            <div key={dir.uuid} className="flex justify-between items-center p-3 border rounded bg-white shadow-sm">
              <span className="text-sm">
                <b className="text-blue-600">{dir.tipo}:</b> {dir.calle}, {dir.ciudad}, {dir.departamento}
              </span>
              {isEditing && (
                <button onClick={() => deleteAddress(dir.uuid)} className="text-red-500 font-bold text-xs border border-red-200 px-2 py-1 rounded hover:bg-red-50">QUITAR</button>
              )}
            </div>
          ))}
        </div>
        {isEditing && (
          <form onSubmit={handleAddAddress} className="flex flex-wrap gap-2 bg-gray-50 p-4 rounded-lg border-dashed border-2 border-gray-200">
            <input className="flex-1 p-1 border text-sm rounded" placeholder="Calle" value={newAddress.calle} onChange={e => setNewAddress({...newAddress, calle: e.target.value})} required />
            <input className="flex-1 p-1 border text-sm rounded" placeholder="Ciudad" value={newAddress.ciudad} onChange={e => setNewAddress({...newAddress, ciudad: e.target.value})} required />
            <input className="flex-1 p-1 border text-sm rounded" placeholder="Depto." value={newAddress.departamento} onChange={e => setNewAddress({...newAddress, departamento: e.target.value})} required />
            <select className="p-1 border text-sm rounded" value={newAddress.tipo} onChange={e => setNewAddress({...newAddress, tipo: e.target.value})}>
              <option value="CASA">CASA</option><option value="TRABAJO">TRABAJO</option><option value="FACTURACION">FACTURACIÓN</option><option value="OTRO">OTRO</option>
            </select>
            <button className="bg-green-600 text-white px-4 py-1 rounded text-xs font-bold">AÑADIR</button>
          </form>
        )}
      </section>

      {/* DOCUMENTOS */}
      <section className="mb-10">
        <h2 className="text-lg font-bold text-gray-700 mb-4 uppercase tracking-wider">Documentos</h2>
        <div className="space-y-3 mb-4">
          {userData?.documentos?.map((doc: any) => (
            <div key={doc.uuid} className="flex justify-between items-center p-3 border rounded bg-white shadow-sm">
              <span className="text-sm">
                <b className="text-purple-600">{doc.tipo}:</b> {doc.valor}
              </span>
              {isEditing && (
                <button onClick={() => deleteDoc(doc.uuid)} className="text-red-500 font-bold text-xs border border-red-200 px-2 py-1 rounded hover:bg-red-50">QUITAR</button>
              )}
            </div>
          ))}
        </div>
        {isEditing && (
          <form onSubmit={handleAddDocument} className="flex gap-2 bg-gray-50 p-4 rounded-lg border-dashed border-2 border-gray-200">
            <select className="p-1 border text-sm rounded" value={newDoc.tipo} onChange={e => setNewDoc({...newDoc, tipo: e.target.value})}>
              <option value="DUI">DUI</option><option value="PASAPORTE">PASAPORTE</option><option value="NIT">NIT</option><option value="NRC">NRC</option>
            </select>
            <input className="flex-1 p-1 border text-sm rounded" placeholder="Número" value={newDoc.valor} onChange={e => setNewDoc({...newDoc, valor: e.target.value})} required />
            <button className="bg-purple-600 text-white px-4 py-1 rounded text-xs font-bold">AÑADIR</button>
          </form>
        )}
      </section>

      {/* GESTION DE CUENTA */}
      <div className="pt-8 border-t border-gray-200">
        <h3 className="text-sm font-bold text-gray-400 uppercase mb-4 tracking-widest">Gestión de Cuenta</h3>
        <button 
          onClick={handleDeactivate}
          className="text-red-600 font-bold text-sm hover:underline"
        >
          {isAdminViewingOthers ? 'Desactivar cuenta de usuario' : 'Desactivar mi cuenta permanentemente'}
        </button>
      </div>
    </div>
  );
};

export default ProfilePage;