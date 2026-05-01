import React, { useEffect, useState } from 'react';
import api from '../api/axiosConfig';
import { useAlert } from '../context/AlertContext';

interface UserDetailAdminProps {
  overrideUuid: string; 
  onClose: () => void; 
  onUserUpdated: () => void;
}

const UserDetailAdmin = ({ overrideUuid, onClose, onUserUpdated }: UserDetailAdminProps) => {
  const [userData, setUserData] = useState<any>(null);
  const [loading, setLoading] = useState(true);
  const [isEditing, setIsEditing] = useState(false);
  const { showAlert } = useAlert();

  const [newAddress, setNewAddress] = useState({ calle: '', ciudad: '', departamento: '', tipo: 'CASA' });
  const [newDoc, setNewDoc] = useState({ tipo: 'DUI', valor: '' });

  const fetchFullData = async () => {
    try {
      setLoading(true);
      const res = await api.get(`/v1/users/${overrideUuid}`);
      setUserData(res.data);
    } catch (error) {
      showAlert("Error al obtener detalles","danger");
      onClose();
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => { 
    if (overrideUuid) fetchFullData(); 
  }, [overrideUuid]);

  const handleUpdate = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const updatePayload = {
        nombre: userData.nombre,
        apellido: userData.apellido,
        email: userData.email,
        role: userData.role 
      };

      await api.put(`/v1/users/${overrideUuid}`, updatePayload);
      onUserUpdated();
      await fetchFullData();
      setIsEditing(false);
      showAlert("¡Usuario actualizado!", "success");
    } catch (error: any) {
      showAlert("Error: " + (error.response?.data?.message || "Error al actualizar"),"danger");
    }
  };

  const handleAddAddress = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await api.post(`/v1/users/${overrideUuid}/addresses`, newAddress);
      setNewAddress({ calle: '', ciudad: '', departamento: '', tipo: 'CASA' });
      fetchFullData();
    } catch { showAlert("Error al añadir dirección", "danger"); }
  };

  const deleteAddress = async (addrUuid: string) => {
    if (window.confirm("¿Quitar esta dirección?")) {
      try {
        await api.delete(`/v1/users/${overrideUuid}/addresses/${addrUuid}`);
        fetchFullData();
      } catch { showAlert("Error al borrar dirección", "danger"); }
    }
  };

  const handleAddDocument = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await api.post(`/v1/users/${overrideUuid}/documents`, newDoc);
      setNewDoc({ tipo: 'DUI', valor: '' });
      fetchFullData();
    } catch { showAlert("Error al añadir documento","danger"); }
  };

  const deleteDoc = async (docUuid: string) => {
    if (window.confirm("¿Quitar este documento?")) {
      try {
        await api.delete(`/v1/users/${overrideUuid}/documents/${docUuid}`);
        fetchFullData();
      } catch { showAlert("Error al borrar documento","danger"); }
    }
  };

  if (loading) return <div className="p-20 text-center font-bold text-indigo-600 animate-pulse">Cargando expediente...</div>;

  return (
    <div className="bg-white rounded-3xl overflow-hidden shadow-2xl border border-gray-200">
      {/* HEADER */}
      <div className="bg-gray-900 p-6 text-white flex justify-between items-center">
        <div>
          <h2 className="text-xl font-bold">{userData?.nombre} {userData?.apellido}</h2>
          <p className="text-[10px] opacity-50 font-mono">ID: {userData?.userId}</p>
        </div>
        <div className="flex gap-2">
          <button onClick={() => setIsEditing(!isEditing)} className="bg-blue-600 hover:bg-blue-700 px-4 py-2 rounded-xl text-xs font-bold transition-all">
            {isEditing ? 'Cancelar' : 'Editar'}
          </button>
          <button onClick={onClose} className="bg-gray-700 hover:bg-gray-800 px-4 py-2 rounded-xl text-xs font-bold">Cerrar</button>
        </div>
      </div>

      <div className="p-8 space-y-8 overflow-y-auto max-h-[75vh]">
        {/* INFO PRINCIPAL */}
        <section className="bg-gray-50 p-5 rounded-2xl border border-gray-100">
          {!isEditing ? (
            <div className="grid grid-cols-2 md:grid-cols-3 gap-4">
              <div><p className="text-[9px] text-gray-400 font-bold uppercase">Nombre</p><p className="text-sm font-medium">{userData?.nombre} {userData?.apellido}</p></div>
              <div><p className="text-[9px] text-gray-400 font-bold uppercase">Email</p><p className="text-sm">{userData?.email}</p></div>
              <div><p className="text-[9px] text-gray-400 font-bold uppercase">Rol</p><span className="text-xs font-black text-indigo-600 bg-indigo-50 px-2 py-0.5 rounded">{userData?.role}</span></div>
            </div>
          ) : (
            <form onSubmit={handleUpdate} className="grid grid-cols-1 md:grid-cols-2 gap-3">
              <input className="p-2 border rounded-lg text-sm" value={userData?.nombre} onChange={e => setUserData({...userData, nombre: e.target.value})} />
              <input className="p-2 border rounded-lg text-sm" value={userData?.apellido} onChange={e => setUserData({...userData, apellido: e.target.value})} />
              <input className="p-2 border rounded-lg text-sm" value={userData?.email} onChange={e => setUserData({...userData, email: e.target.value})} />
              <select className="p-2 border rounded-lg text-sm font-bold" value={userData?.role} onChange={e => setUserData({...userData, role: e.target.value})}>
                <option value="USER">USER</option>
                <option value="ADMIN">ADMIN</option>
              </select>
              <button className="md:col-span-2 bg-indigo-600 text-white rounded-lg font-bold text-sm p-2">Guardar Cambios</button>
            </form>
          )}
        </section>

        {/* DIRECCIONES */}
        <section>
          <h3 className="text-[10px] font-black text-gray-400 uppercase mb-3">Direcciones</h3>
          <div className="space-y-2 mb-4">
            {userData?.direcciones?.map((dir: any) => (
              <div key={dir.uuid} className="flex justify-between items-center p-3 bg-white border border-gray-100 rounded-xl shadow-sm">
                <p className="text-xs text-gray-600"><b className="text-indigo-600">{dir.tipo}:</b> {dir.calle}, {dir.ciudad}</p>
                {isEditing && <button onClick={() => deleteAddress(dir.uuid)} className="text-red-500 font-bold px-2">X</button>}
              </div>
            ))}
          </div>
          {isEditing && (
            <form onSubmit={handleAddAddress} className="grid grid-cols-2 md:grid-cols-5 gap-2 bg-blue-50 p-3 rounded-xl border border-blue-100">
              <input className="p-2 border rounded text-xs" placeholder="Calle" value={newAddress.calle} onChange={e => setNewAddress({...newAddress, calle: e.target.value})} required />
              <input className="p-2 border rounded text-xs" placeholder="Ciudad" value={newAddress.ciudad} onChange={e => setNewAddress({...newAddress, ciudad: e.target.value})} required />
              <input className="p-2 border rounded text-xs" placeholder="Depto" value={newAddress.departamento} onChange={e => setNewAddress({...newAddress, departamento: e.target.value})} required />
              <select className="p-2 border rounded text-xs" value={newAddress.tipo} onChange={e => setNewAddress({...newAddress, tipo: e.target.value})}>
                <option value="CASA">CASA</option><option value="TRABAJO">TRABAJO</option><option value="TRABAJO">OTRO</option>
              </select>
              <button className="bg-blue-600 text-white rounded-lg text-[10px] font-bold">AÑADIR</button>
            </form>
          )}
        </section>

        {/* DOCUMENTOS */}
        <section>
          <h3 className="text-[10px] font-black text-gray-400 uppercase mb-3">Documentos</h3>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-3 mb-4">
            {userData?.documentos?.map((doc: any) => (
              <div key={doc.uuid} className="flex justify-between items-center p-3 border border-gray-100 rounded-xl bg-white shadow-sm">
                <p className="text-xs text-gray-600"><b>{doc.tipo}:</b> {doc.valor}</p>
                {isEditing && <button onClick={() => deleteDoc(doc.uuid)} className="text-red-500 font-bold px-2">X</button>}
              </div>
            ))}
          </div>
          {isEditing && (
            <form onSubmit={handleAddDocument} className="flex gap-2 bg-purple-50 p-3 rounded-xl border border-purple-100">
              <select className="p-2 border rounded text-xs" value={newDoc.tipo} onChange={e => setNewDoc({...newDoc, tipo: e.target.value})}>
                <option value="DUI">DUI</option><option value="NIT">NIT</option><option value="NIT">PASAPORTE</option>
              </select>
              <input className="flex-1 p-2 border rounded text-xs" placeholder="Número" value={newDoc.valor} onChange={e => setNewDoc({...newDoc, valor: e.target.value})} required />
              <button className="bg-purple-600 text-white px-4 rounded-lg text-[10px] font-bold">AÑADIR</button>
            </form>
          )}
        </section>
      </div>
    </div>
  );
};

export default UserDetailAdmin;