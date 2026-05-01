import { useState } from "react";
import api from '../api/axiosConfig';
import { useNavigate, Link } from 'react-router-dom';
import { TIPO_DIRECCION, TIPO_DOCUMENTO } from '../api/constants';
import { useAlert } from '../context/AlertContext';

const Register = () => {
  const navigate = useNavigate();
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
      showAlert("Usuario creado con éxito","success");
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

  const inputClass = (error?: string) => `
    bg-gray-50 border ${error ? 'border-red-500' : 'border-gray-300'} 
    text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 
    block w-full p-2.5 shadow-sm placeholder-gray-400
  `;

  const labelClass = (error?: string) => `
    block mb-2 text-sm font-medium ${error ? 'text-red-700' : 'text-gray-900'}
  `;

  return (
    <div className="min-h-screen bg-gray-50 py-10 px-4">
      <div className="max-w-3xl mx-auto bg-white p-8 rounded-lg shadow-lg border border-gray-200">
        
        <div className="text-center mb-8">
          <h2 className="text-3xl font-bold text-gray-900">Crear Cuenta</h2>
          <p className="text-sm text-gray-500 mt-2">Completa la información para registrarte en el sistema</p>
        </div>

        {generalError && (
          <div className="p-4 mb-6 text-sm text-red-800 rounded-lg bg-red-50 border border-red-200" role="showAlert">
            <span className="font-medium">¡Error!</span> {generalError}
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-6">
          
          {/* Nombre y Apellido */}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
              <label className={labelClass(fieldErrors.nombre)}>Nombre</label>
              <input 
                className={inputClass(fieldErrors.nombre)}
                placeholder="Ej. Juan" 
                onChange={e => setFormData({...formData, nombre: e.target.value})} 
                required 
              />
              {fieldErrors.nombre && <p className="mt-2 text-xs text-red-600">{fieldErrors.nombre}</p>}
            </div>
            <div>
              <label className={labelClass(fieldErrors.apellido)}>Apellido</label>
              <input 
                className={inputClass(fieldErrors.apellido)}
                placeholder="Ej. Pérez" 
                onChange={e => setFormData({...formData, apellido: e.target.value})} 
                required 
              />
              {fieldErrors.apellido && <p className="mt-2 text-xs text-red-600">{fieldErrors.apellido}</p>}
            </div>
          </div>
          
          {/* Email */}
          <div>
            <label className={labelClass(fieldErrors.email)}>Tu email</label>
            <input 
              type="email" 
              className={inputClass(fieldErrors.email)}
              placeholder="nombre@correo.com" 
              onChange={e => setFormData({...formData, email: e.target.value})} 
              required 
            />
            {fieldErrors.email && <p className="mt-2 text-xs text-red-600">{fieldErrors.email}</p>}
          </div>
          
          {/* Password */}
          <div className="relative">
            <label className={labelClass(fieldErrors.password)}>Contraseña</label>
            <input 
              type={showPassword ? "text" : "password"} 
              className={inputClass(fieldErrors.password)}
              placeholder="••••••••" 
              onChange={e => setFormData({...formData, password: e.target.value})} 
              required 
            />
            <button 
              type="button"
              className="absolute right-3 top-9 text-sm text-blue-600 font-medium hover:text-blue-800"
              onClick={() => setShowPassword(!showPassword)}
            >
              {showPassword ? "Ocultar" : "Mostrar"}
            </button>
            {fieldErrors.password && <p className="mt-2 text-xs text-red-600">{fieldErrors.password}</p>}
          </div>

          <hr className="h-px my-8 bg-gray-200 border-0" />

          {/* Sección Direcciones */}
          <div className="p-6 bg-blue-50 rounded-lg border border-blue-100">
            <div className="flex justify-between items-center mb-4">
              <h3 className="text-lg font-bold text-blue-900">Direcciones</h3>
              <button 
                type="button" 
                onClick={() => setFormData({...formData, direcciones: [...formData.direcciones, { calle: '', ciudad: '', departamento: '', tipo: 'CASA' }]})} 
                className="text-white bg-blue-600 hover:bg-blue-700 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-xs px-3 py-2"
              >
                + Añadir Dirección
              </button>
            </div>
            
            {formData.direcciones.map((dir, i) => (
              <div key={i} className="grid grid-cols-1 md:grid-cols-4 gap-3 bg-white p-4 rounded-lg border border-blue-200 mb-3 shadow-sm">
                <input className={inputClass()} placeholder="Calle" onChange={e => handleAddressChange(i, 'calle', e.target.value)} required />
                <input className={inputClass()} placeholder="Ciudad" onChange={e => handleAddressChange(i, 'ciudad', e.target.value)} required />
                <input className={inputClass()} placeholder="Depto" onChange={e => handleAddressChange(i, 'departamento', e.target.value)} required />
                <select className={inputClass()} value={dir.tipo} onChange={e => handleAddressChange(i, 'tipo', e.target.value)}>
                  {TIPO_DIRECCION.map(t => <option key={t} value={t}>{t}</option>)}
                </select>
              </div>
            ))}
          </div>

          {/* Sección Documentos */}
          <div className="p-6 bg-gray-50 rounded-lg border border-gray-200">
            <div className="flex justify-between items-center mb-4">
              <h3 className="text-lg font-bold text-gray-900">Documentos</h3>
              <button 
                type="button" 
                onClick={() => setFormData({...formData, documentos: [...formData.documentos, { tipo: 'DUI', valor: '' }]})} 
                className="text-white bg-gray-600 hover:bg-gray-700 focus:ring-4 focus:ring-gray-300 font-medium rounded-lg text-xs px-3 py-2"
              >
                + Añadir Documento
              </button>
            </div>
            
            {formData.documentos.map((doc, i) => (
              <div key={i} className="grid grid-cols-1 md:grid-cols-2 gap-3 bg-white p-4 rounded-lg border border-gray-200 mb-3 shadow-sm">
                <select className={inputClass()} value={doc.tipo} onChange={e => handleDocChange(i, 'tipo', e.target.value)}>
                  {TIPO_DOCUMENTO.map(t => <option key={t} value={t}>{t}</option>)}
                </select>
                <input className={inputClass()} placeholder="Número identificador" onChange={e => handleDocChange(i, 'valor', e.target.value)} required />
              </div>
            ))}
          </div>

          {/* Botón de envío */}
          <button 
            type="submit" 
            className="w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-bold rounded-lg text-sm px-5 py-3 text-center transition-all shadow-md"
          >
            Crear Cuenta de Usuario
          </button>

          <div className="text-center mt-4">
            <p className="text-sm text-gray-500">
              ¿Ya tienes una cuenta? <Link to="/login" className="text-blue-600 font-bold hover:underline">Inicia sesión aquí</Link>
            </p>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Register;