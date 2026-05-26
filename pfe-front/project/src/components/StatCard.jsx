const StatCard = ({ title, value, icon: Icon, trend, trendValue, color = 'primary' }) => {
  const colorMap = {
    primary: 'bg-primary-50 text-primary-600',
    emerald: 'bg-emerald-50 text-emerald-600',
    amber: 'bg-amber-50 text-amber-600',
    red: 'bg-red-50 text-red-600',
    purple: 'bg-purple-50 text-purple-600',
    sky: 'bg-sky-50 text-sky-600',
  };

  return (
    <div className="card p-6">
      <div className="flex items-start justify-between">
        <div className="flex-1">
          <p className="text-sm font-medium text-gray-500 mb-1">{title}</p>
          <p className="text-2xl font-bold text-gray-900">{value}</p>
          {trend && (
            <div className="flex items-center gap-1 mt-2">
              <span className={`text-xs font-medium ${trend === 'up' ? 'text-emerald-600' : 'text-red-500'}`}>
                {trend === 'up' ? '+' : '-'}{trendValue}%
              </span>
              <span className="text-xs text-gray-400">vs mois dernier</span>
            </div>
          )}
        </div>
        <div className={`p-3 rounded-xl ${colorMap[color]}`}>
          <Icon size={24} />
        </div>
      </div>
    </div>
  );
};

export default StatCard;
