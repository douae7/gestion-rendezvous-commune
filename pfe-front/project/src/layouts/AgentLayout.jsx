import { Outlet } from 'react-router-dom';
import Sidebar from '../components/Sidebar';
import Navbar from '../components/Navbar';
import { LayoutDashboard, CalendarCheck, Calendar, Inbox, Clock, User } from 'lucide-react';

const menuItems = [
  { path: '/agent/dashboard', label: 'Tableau de bord', icon: LayoutDashboard },
  { path: '/agent/today', label: 'RDV du Jour', icon: CalendarCheck },
  { path: '/agent/schedule', label: 'Planning', icon: Calendar },
  { path: '/agent/appointments', label: 'Rendez-vous', icon: Inbox },
  { path: '/agent/profile', label: 'Mon Profil', icon: User },
];

const AgentLayout = () => (
  <div className="min-h-screen bg-gray-50">
    <Sidebar menuItems={menuItems} title="CityAppointment" subtitle="Agent Municipal" />
    <div className="ml-64 min-h-screen">
      <Navbar />
      <main className="p-6"><Outlet /></main>
    </div>
  </div>
);

export default AgentLayout;
