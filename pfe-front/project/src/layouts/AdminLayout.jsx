import { Outlet } from 'react-router-dom';
import Sidebar from '../components/Sidebar';
import Navbar from '../components/Navbar';
import { Inbox } from "lucide-react";
import { LayoutDashboard, Users, UserCheck, Calendar, Building2, BarChart3, Settings } from 'lucide-react';

const menuItems = [
  { path: '/admin/dashboard', label: 'Tableau de bord', icon: LayoutDashboard },
  { path: '/admin/citizens', label: 'Citoyens', icon: Users },
  { path: '/admin/agents', label: 'Agents', icon: UserCheck },
  { path: '/admin/appointments', label: 'Rendez-vous', icon: Calendar },
  { path: '/admin/departments', label: 'Services Municipaux', icon: Building2 },
  { path: '/admin/reclamations', label: 'Réclamations', icon: Inbox },
  { path: '/admin/reports', label: 'Rapports', icon: BarChart3 },
  { path: '/admin/settings', label: 'Paramètres', icon: Settings },
];

const AdminLayout = () => (
  <div className="min-h-screen bg-gray-50">
    <Sidebar menuItems={menuItems} title="CityAppointment" subtitle="Administration Municipale" />
    <div className="ml-64 min-h-screen">
      <Navbar />
      <main className="p-6"><Outlet /></main>
    </div>
  </div>
);

export default AdminLayout;
