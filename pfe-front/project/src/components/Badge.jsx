const Badge = ({ children, variant = 'default', className = '' }) => {
  const variants = {
    default: 'bg-gray-100 text-gray-700 border-gray-200',
    primary: 'bg-primary-100 text-primary-700 border-primary-200',
    success: 'bg-emerald-100 text-emerald-700 border-emerald-200',
    warning: 'bg-amber-100 text-amber-700 border-amber-200',
    danger: 'bg-red-100 text-red-700 border-red-200',
    info: 'bg-sky-100 text-sky-700 border-sky-200',
    purple: 'bg-purple-100 text-purple-700 border-purple-200',
  };
  return (
    <span className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium border ${variants[variant]} ${className}`}>
      {children}
    </span>
  );
};

export default Badge;
