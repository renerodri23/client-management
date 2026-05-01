
interface AlertProps {
  message: string;
  type: 'info' | 'danger' | 'success' | 'warning' | 'dark';
  onClose: () => void;
}

const Alert = ({ message, type, onClose }: AlertProps) => {
  // Mapeo con colores estándar de Tailwind que funcionan al 100%
  const styles = {
    info: {
      container: "text-blue-800 bg-blue-50 border-blue-200",
      button: "bg-blue-50 text-blue-500 focus:ring-blue-400 hover:bg-blue-200"
    },
    danger: {
      container: "text-red-800 bg-red-50 border-red-200",
      button: "bg-red-50 text-red-500 focus:ring-red-400 hover:bg-red-200"
    },
    success: {
      container: "text-green-800 bg-green-50 border-green-200",
      button: "bg-green-50 text-green-500 focus:ring-green-400 hover:bg-green-200"
    },
    warning: {
      container: "text-yellow-800 bg-yellow-50 border-yellow-200",
      button: "bg-yellow-50 text-yellow-500 focus:ring-yellow-400 hover:bg-yellow-200"
    },
    dark: {
      container: "text-gray-800 bg-gray-100 border-gray-300",
      button: "bg-gray-100 text-gray-500 focus:ring-gray-400 hover:bg-gray-200"
    }
  };

  const currentStyle = styles[type];

  return (
    <div className={`flex items-center p-4 mb-4 text-sm rounded-xl border shadow-lg animate-in fade-in slide-in-from-right-5 ${currentStyle.container}`} role="alert">
      <svg className="w-4 h-4 shrink-0" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
        <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M10 11h2v5m-2 0h4m-2.592-8.5h.01M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z"/>
      </svg>
      
      <div className="ms-3 text-sm font-bold">
        {message}
      </div>

      <button 
        onClick={onClose}
        type="button" 
        className={`ms-auto -mx-1.5 -my-1.5 rounded-lg inline-flex items-center justify-center h-8 w-8 transition-colors focus:ring-2 p-1.5 ${currentStyle.button}`}
      >
        <span className="sr-only">Close</span>
        <svg className="w-4 h-4" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
          <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M6 18 17.94 6M18 18 6.06 6"/>
        </svg>
      </button>
    </div>
  );
};

export default Alert;