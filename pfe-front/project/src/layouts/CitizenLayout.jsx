import { Outlet } from 'react-router-dom';
import Sidebar from '../components/Sidebar';
import Navbar from '../components/Navbar';
import ChatPopup from '../components/ChatPopup'; // ✅ FIX

import {
  LayoutDashboard,
  CalendarPlus,
  CalendarDays,
  Send,
  Clock,
  User,
  Calendar
} from 'lucide-react';

const menuItems = [
  { path: '/citizen/dashboard', label: 'Mon Espace', icon: LayoutDashboard },
  { path: '/citizen/book', label: 'Réserver RDV', icon: CalendarPlus },
  { path: '/citizen/appointments', label: 'Mes Rendez-vous', icon: CalendarDays },
  { path: '/citizen/history', label: 'Historique', icon: Clock },
  { path: '/citizen/reclamation', label: 'Envoyer une réclamation', icon: Send },
  { path: '/citizen/profile', label: 'Mon Profil', icon: User }
];

const CitizenLayout = () => (
  <div className="min-h-screen bg-gray-50">

    <Sidebar
      menuItems={menuItems}
      title="CityAppointment"
      subtitle="Espace Citoyen"
    />

    <div className="ml-64 min-h-screen">
      <Navbar />
      <main className="p-6">
        <Outlet />
      </main>
    </div>

    {/* 💬 CHAT POPUP */}
   

  </div>
);

export default CitizenLayout;