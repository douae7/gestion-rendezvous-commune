import { NavLink, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { LogOut, ChevronLeft, ChevronRight } from 'lucide-react';
import { useState } from 'react';

const Sidebar = ({ menuItems, title, subtitle }) => {
  const [collapsed, setCollapsed] = useState(false);
  const { logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => { logout(); navigate('/'); };

  return (
    <aside className={`fixed left-0 top-0 h-screen bg-white border-r border-gray-100 flex flex-col z-40 transition-all duration-300 ${collapsed ? 'w-20' : 'w-64'}`}>
      <div className="p-5 border-b border-gray-100">
        <div className="flex items-center gap-3">
          <div className="w-10 h-10 bg-primary-600 rounded-xl flex items-center justify-center flex-shrink-0">
            <span className="text-white font-bold text-lg">C</span>
          </div>
          {!collapsed && (
            <div className="overflow-hidden">
              <h1 className="text-sm font-bold text-gray-900 truncate">{title}</h1>
              <p className="text-[10px] text-gray-400 truncate">{subtitle}</p>
            </div>
          )}
        </div>
      </div>
      <nav className="flex-1 p-3 space-y-1 overflow-y-auto scrollbar-thin">
        {menuItems.map((item) => (
          <NavLink key={item.path} to={item.path} className={({ isActive }) => (isActive ? 'sidebar-link-active' : 'sidebar-link')}>
            <item.icon size={20} className="flex-shrink-0" />
            {!collapsed && <span className="truncate">{item.label}</span>}
          </NavLink>
        ))}
      </nav>
      <div className="p-3 border-t border-gray-100 space-y-1">
        <button onClick={handleLogout} className="sidebar-link w-full text-red-600 hover:bg-red-50 hover:text-red-700">
          <LogOut size={20} className="flex-shrink-0" />
          {!collapsed && <span>Déconnexion</span>}
        </button>
        <button onClick={() => setCollapsed(!collapsed)} className="sidebar-link w-full">
          {collapsed ? <ChevronRight size={20} /> : <ChevronLeft size={20} />}
          {!collapsed && <span>Réduire</span>}
        </button>
      </div>
    </aside>
  );
};

export default Sidebar;
