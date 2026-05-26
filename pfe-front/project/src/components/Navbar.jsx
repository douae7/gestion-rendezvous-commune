import { useAuth } from '../context/AuthContext';
import { useLocation } from 'react-router-dom';
import { Moon, Sun, Globe, Menu } from 'lucide-react';
import { useState } from 'react';
import NotificationBell from './NotificationBell';
import { getRoleLabel } from '../utils/helpers';

const Navbar = () => {
  const { user } = useAuth();
  const location = useLocation();
  const [lang, setLang] = useState('fr');

  const titles = {
    '/admin/dashboard': 'Tableau de bord', '/admin/citizens': 'Gestion des Citoyens', '/admin/agents': 'Gestion des Agents',
    '/admin/appointments': 'Rendez-vous', '/admin/departments': 'Services Municipaux', '/admin/reports': 'Rapports & Statistiques', '/admin/settings': 'Paramètres',
    '/citizen/dashboard': 'Mon Espace', '/citizen/book': 'Réserver un Rendez-vous', '/citizen/appointments': 'Mes Rendez-vous',
    '/citizen/history': 'Historique des Demandes', '/citizen/profile': 'Mon Profil',
    '/citizen/reclamation': 'Réclamations','/citizen/appointment/:id': 'Détail du Rendez-vous',
    '/agent/dashboard': 'Tableau de bord', '/agent/today': 'Rendez-vous du Jour', '/agent/schedule': 'Planning',
    '/agent/requests': 'Demandes', '/agent/availability': 'Disponibilité', '/agent/profile': 'Mon Profil',
  };


  return (
    <header className="sticky top-0 z-30 bg-white/80 backdrop-blur-md border-b border-gray-100">
      <div className="flex items-center justify-between px-6 py-3">
        <div className="flex items-center gap-4">
          <button className="lg:hidden p-2 hover:bg-gray-100 rounded-lg"><Menu size={20} className="text-gray-600" /></button>
          <div>
            <h2 className="text-lg font-semibold text-gray-900">{titles[location.pathname] || 'CityAppointment'}</h2>
            <p className="text-xs text-gray-400">Municipalité - Espace Digital</p>
          </div>
        </div>
        <div className="flex items-center gap-2">
          
          <NotificationBell />
          <div className="flex items-center gap-3 ml-2 pl-4 border-l border-gray-200">
            <div className="w-9 h-9 bg-primary-100 rounded-full flex items-center justify-center">
              <span className="text-primary-700 font-semibold text-sm">{user?.name?.charAt(0)?.toUpperCase() || 'U'}</span>
            </div>
            <div className="hidden sm:block">
              <p className="text-sm font-medium text-gray-900">{user?.name || 'Utilisateur'}</p>
              <p className="text-[10px] text-gray-400">{getRoleLabel(user?.role)}</p>
            </div>
          </div>
        </div>
      </div>
    </header>
  );
};

export default Navbar;
