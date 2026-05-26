import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import App from './App';
import './index.css';

window.global = window;
window.process = { env: {} };
window.Buffer = window.Buffer || [];
createRoot(document.getElementById('root')).render(
  <StrictMode>
    <App />
  </StrictMode>
);
