import { useState } from "react";
import api from '../api/axiosConfig';
import { useNavigate } from 'react-router-dom';
import { TIPO_DIRECCION, TIPO_DOCUMENTO } from '../api/constants';

const Register = () => {
  const navigate = useNavigate();
  const [showPassword, setShowPassword] = useState(false);
  const [generalError, setGeneralError] = useState("");
  const [fieldErrors, setFieldErrors] = useState<Record<string, string>>({});

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
      alert("Usuario creado con éxito");
      navigate('/login');
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
    <div className="max-w-3xl p-8 mx-auto mt-10 bg-white rounded-lg shadow-xl border mb-10">
      <h2 className="mb-6 text-2xl font-bold text-gray-800 border-b pb-2">Crear Cuenta</h2>
      
      {generalError && (
        <div className="mb-4 p-3 bg-red-100 border-l-4 border-red-500 text-red-700 font-medium">
          {generalError}
        </div>
      )}

      <form onSubmit={handleSubmit} className="space-y-6">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div className="flex flex-col">
            <input 
              className={`p-2 border rounded ${fieldErrors.nombre ? 'border-red-500' : ''}`}
              placeholder="Nombre" 
              onChange={e => setFormData({...formData, nombre: e.target.value})} 
              required 
            />
            {fieldErrors.nombre && <span className="text-red-500 text-xs mt-1">{fieldErrors.nombre}</span>}
          </div>
          <div className="flex flex-col">
            <input 
              className={`p-2 border rounded ${fieldErrors.apellido ? 'border-red-500' : ''}`}
              placeholder="Apellido" 
              onChange={e => setFormData({...formData, apellido: e.target.value})} 
              required 
            />
            {fieldErrors.apellido && <span className="text-red-500 text-xs mt-1">{fieldErrors.apellido}</span>}
          </div>
        </div>
        
        <div className="flex flex-col">
          <input 
            type="email" 
            className={`p-2 border rounded ${fieldErrors.email ? 'border-red-500' : ''}`}
            placeholder="Email" 
            onChange={e => setFormData({...formData, email: e.target.value})} 
            required 
          />
          {fieldErrors.email && <span className="text-red-500 text-xs mt-1">{fieldErrors.email}</span>}
        </div>
        
        <div className="flex flex-col relative">
          <input 
            type={showPassword ? "text" : "password"} 
            className={`p-2 border rounded pr-20 ${fieldErrors.password ? 'border-red-500' : ''}`}
            placeholder="Contraseña" 
            onChange={e => setFormData({...formData, password: e.target.value})} 
            required 
          />
          <button 
            type="button"
            className="absolute right-3 top-2 text-sm text-blue-600 font-medium"
            onClick={() => setShowPassword(!showPassword)}
          >
            {showPassword ? "Ocultar" : "Mostrar"}
          </button>
          {fieldErrors.password && <span className="text-red-500 text-xs mt-1">{fieldErrors.password}</span>}
        </div>

        <div className="p-4 bg-blue-50 rounded-lg space-y-4 border border-blue-100">
          <div className="flex justify-between items-center">
            <h3 className="font-bold text-blue-800">Direcciones</h3>
            <button type="button" onClick={() => setFormData({...formData, direcciones: [...formData.direcciones, { calle: '', ciudad: '', departamento: '', tipo: 'CASA' }]})} className="bg-blue-500 text-white px-3 py-1 rounded text-xs hover:bg-blue-600">+ Añadir</button>
          </div>
          {formData.direcciones.map((dir, i) => (
            <div key={i} className="grid grid-cols-1 md:grid-cols-4 gap-2 bg-white p-3 rounded border">
              <input className="p-1 border text-sm" placeholder="Calle" onChange={e => handleAddressChange(i, 'calle', e.target.value)} required />
              <input className="p-1 border text-sm" placeholder="Ciudad" onChange={e => handleAddressChange(i, 'ciudad', e.target.value)} required />
              <input className="p-1 border text-sm" placeholder="Depto" onChange={e => handleAddressChange(i, 'departamento', e.target.value)} required />
              <select className="p-1 border text-sm" value={dir.tipo} onChange={e => handleAddressChange(i, 'tipo', e.target.value)}>
                {TIPO_DIRECCION.map(t => <option key={t} value={t}>{t}</option>)}
              </select>
            </div>
          ))}
        </div>

        <div className="p-4 bg-gray-50 rounded-lg space-y-4 border border-gray-200">
          <div className="flex justify-between items-center">
            <h3 className="font-bold text-gray-800">Documentos</h3>
            <button type="button" onClick={() => setFormData({...formData, documentos: [...formData.documentos, { tipo: 'DUI', valor: '' }]})} className="bg-gray-500 text-white px-3 py-1 rounded text-xs hover:bg-gray-600">+ Añadir</button>
          </div>
          {formData.documentos.map((doc, i) => (
            <div key={i} className="grid grid-cols-1 md:grid-cols-2 gap-2 bg-white p-3 rounded border">
              <select className="p-1 border text-sm" value={doc.tipo} onChange={e => handleDocChange(i, 'tipo', e.target.value)}>
                {TIPO_DOCUMENTO.map(t => <option key={t} value={t}>{t}</option>)}
              </select>
              <input className="p-1 border text-sm" placeholder="Número" onChange={e => handleDocChange(i, 'valor', e.target.value)} required />
            </div>
          ))}
        </div>

        <button type="submit" className="w-full p-3 text-white bg-green-600 rounded-lg font-bold hover:bg-green-700 shadow-md transition-colors">
          Crear Cuenta
        </button>
      </form>
    </div>
  );
};

export default Register;