import React, { createContext, useContext, useState, useCallback } from 'react';
import Alert from '../components/Alert';

type AlertType = 'info' | 'danger' | 'success' | 'warning' | 'dark';

interface AlertContextType {
  showAlert: (message: string, type: AlertType) => void;
}

const AlertContext = createContext<AlertContextType | undefined>(undefined);

export const AlertProvider = ({ children }: { children: React.ReactNode }) => {
  const [alert, setAlert] = useState<{ message: string; type: AlertType } | null>(null);

  const showAlert = useCallback((message: string, type: AlertType) => {
    setAlert({ message, type });
    setTimeout(() => setAlert(null), 5000);
  }, []);

  return (
    <AlertContext.Provider value={{ showAlert }}>
      {children}
      <div className="fixed top-5 right-5 z-[9999] w-full max-w-sm">
        {alert && (
          <Alert 
            message={alert.message} 
            type={alert.type} 
            onClose={() => setAlert(null)} 
          />
        )}
      </div>
    </AlertContext.Provider>
  );
};

export const useAlert = () => {
  const context = useContext(AlertContext);
  if (!context) throw new Error("useAlert debe usarse dentro de un AlertProvider");
  return context;
};