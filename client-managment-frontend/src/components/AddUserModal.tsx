import React, { useState } from "react";
import api from '../api/axiosConfig';
import { TIPO_DIRECCION, TIPO_DOCUMENTO } from '../api/constants';
import { useAlert } from '../context/AlertContext';

const AddUserModal = ({ onClose, onUserCreated }: { onClose: () => void, onUserCreated: () => void }) => {
  const [showPassword, setShowPassword] = useState(false);
  const [generalError, setGeneralError] = useState("");
  const [fieldErrors, setFieldErrors] = useState<Record<string, string>>({});
  const { showAlert } = useAlert();

  const [formData, setFormData] = useState({
    nombre: '',
    apellido: '',
    email: '',
    password: '',
    role: 'USER', 
    direcciones: [{ calle: '', ciudad: '', departamento: '', tipo: 'CASA' }],
    documentos: [{ tipo: 'DUI', valor: '' }]
  });

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setGeneralError("");
    setFieldErrors({});

    try {
      await api.post('/v1/users', formData);
      showAlert("Usuario creado con éxito", "success");
      onUserCreated(); 
      onClose();       
    } catch (error: any) {
      const data = error.response?.data;
      if (data?.errors) {
        setFieldErrors(data.errors);
      } else {
        setGeneralError(data?.message || "Ocurrió un error inesperado");
      }
    }
  };

  const handleAddressChange = (index: number, field: string, value: string) => {
    const newDirecciones = [...formData.direcciones];
    (newDirecciones[index] as any)[field] = value;
    setFormData({ ...formData, direcciones: newDirecciones });
  };

  const handleDocChange = (index: number, field: string, value: string) => {
    const newDocumentos = [...formData.documentos];
    (newDocumentos[index] as any)[field] = value;
    setFormData({ ...formData, documentos: newDocumentos });
  };

  return (
    <div className="fixed inset-0 bg-black/60 backdrop-blur-sm flex items-center justify-center z-[60] p-4">
      <div className="bg-white w-full max-w-3xl max-h-[95vh] overflow-y-auto rounded-3xl shadow-2xl p-8">
        <div className="flex justify-between items-center mb-6 border-b pb-4">
          <h2 className="text-2xl font-bold text-gray-800">Registrar Nuevo Cliente</h2>
          <button onClick={onClose} className="text-gray-400 hover:text-gray-600 font-bold">✕</button>
        </div>

        {generalError && (
          <div className="mb-4 p-3 bg-red-100 border-l-4 border-red-500 text-red-700 text-sm">
            {generalError}
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-6">
          {/* DATOS BÁSICOS + ROL */}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <input className={`p-2 border rounded-lg ${fieldErrors.nombre ? 'border-red-500' : ''}`} placeholder="Nombre" onChange={e => setFormData({...formData, nombre: e.target.value})} required />
            <input className={`p-2 border rounded-lg ${fieldErrors.apellido ? 'border-red-500' : ''}`} placeholder="Apellido" onChange={e => setFormData({...formData, apellido: e.target.value})} required />
            <input type="email" className={`p-2 border rounded-lg ${fieldErrors.email ? 'border-red-500' : ''}`} placeholder="Email" onChange={e => setFormData({...formData, email: e.target.value})} required />
            
            <div className="relative">
              <input type={showPassword ? "text" : "password"} className={`w-full p-2 border rounded-lg ${fieldErrors.password ? 'border-red-500' : ''}`} placeholder="Contraseña" onChange={e => setFormData({...formData, password: e.target.value})} required />
              <button type="button" className="absolute right-3 top-2 text-xs text-blue-600 font-bold" onClick={() => setShowPassword(!showPassword)}>
                {showPassword ? "Ocultar" : "Ver"}
              </button>
            </div>

            <div className="md:col-span-2">
              <label className="text-[10px] font-black text-gray-400 uppercase tracking-widest ml-1">Asignar Rol del Sistema</label>
              <select 
                className="w-full p-2 border rounded-lg bg-gray-50 font-bold text-gray-700"
                value={formData.role}
                onChange={e => setFormData({...formData, role: e.target.value})}
              >
                <option value="USER">USUARIO (CLIENTE)</option>
                <option value="ADMIN">ADMINISTRADOR</option>
              </select>
            </div>
          </div>

          {/* DIRECCIONES MÚLTIPLES */}
          <div className="p-4 bg-blue-50 rounded-2xl border border-blue-100 space-y-3">
            <div className="flex justify-between items-center">
              <h3 className="text-sm font-bold text-blue-800 uppercase">Direcciones</h3>
              <button type="button" onClick={() => setFormData({...formData, direcciones: [...formData.direcciones, { calle: '', ciudad: '', departamento: '', tipo: 'CASA' }]})} className="bg-blue-600 text-white px-3 py-1 rounded-full text-[10px] font-bold hover:bg-blue-700">+ Añadir</button>
            </div>
            {formData.direcciones.map((dir, i) => (
              <div key={i} className="grid grid-cols-1 md:grid-cols-4 gap-2 bg-white p-3 rounded-xl border border-blue-100">
                <input className="p-2 border rounded text-xs" placeholder="Calle" onChange={e => handleAddressChange(i, 'calle', e.target.value)} required />
                <input className="p-2 border rounded text-xs" placeholder="Ciudad" onChange={e => handleAddressChange(i, 'ciudad', e.target.value)} required />
                <input className="p-2 border rounded text-xs" placeholder="Depto" onChange={e => handleAddressChange(i, 'departamento', e.target.value)} required />
                <select className="p-2 border rounded text-xs bg-gray-50" value={dir.tipo} onChange={e => handleAddressChange(i, 'tipo', e.target.value)}>
                  {TIPO_DIRECCION.map(t => <option key={t} value={t}>{t}</option>)}
                </select>
              </div>
            ))}
          </div>

          {/* DOCUMENTOS MÚLTIPLES */}
          <div className="p-4 bg-gray-50 rounded-2xl border border-gray-200 space-y-3">
            <div className="flex justify-between items-center">
              <h3 className="text-sm font-bold text-gray-700 uppercase">Documentos de Identidad</h3>
              <button type="button" onClick={() => setFormData({...formData, documentos: [...formData.documentos, { tipo: 'DUI', valor: '' }]})} className="bg-gray-800 text-white px-3 py-1 rounded-full text-[10px] font-bold hover:bg-black">+ Añadir</button>
            </div>
            {formData.documentos.map((doc, i) => (
              <div key={i} className="grid grid-cols-1 md:grid-cols-2 gap-2 bg-white p-3 rounded-xl border border-gray-200">
                <select className="p-2 border rounded text-xs bg-gray-50" value={doc.tipo} onChange={e => handleDocChange(i, 'tipo', e.target.value)}>
                  {TIPO_DOCUMENTO.map(t => <option key={t} value={t}>{t}</option>)}
                </select>
                <input className="p-2 border rounded text-xs" placeholder="Número / Valor" onChange={e => handleDocChange(i, 'valor', e.target.value)} required />
              </div>
            ))}
          </div>

          <button type="submit" className="w-full p-4 text-white bg-indigo-600 rounded-2xl font-bold hover:bg-indigo-700 shadow-lg shadow-indigo-100 transition-all">
            Finalizar y Crear Usuario
          </button>
        </form>
      </div>
    </div>
  );
};

export default AddUserModal;