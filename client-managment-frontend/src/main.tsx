import React from 'react';
import { createRoot } from 'react-dom/client';
import { Provider } from 'react-redux';
import { store } from './app/store';
import App from './App';
import './index.css';
import { AlertProvider } from './context/AlertContext';

const container = document.getElementById('root');
if (container) {
  createRoot(container).render(
    <React.StrictMode>
      <AlertProvider>
        <Provider store={store}>
          <App />
        </Provider>
      </AlertProvider>
    </React.StrictMode>
  );
}