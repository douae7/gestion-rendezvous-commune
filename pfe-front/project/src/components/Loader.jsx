const Loader = () => (
  <div className="min-h-screen flex items-center justify-center bg-gray-50">
    <div className="flex flex-col items-center gap-4">
      <div className="relative w-16 h-16">
        <div className="absolute inset-0 rounded-full border-4 border-primary-100"></div>
        <div className="absolute inset-0 rounded-full border-4 border-transparent border-t-primary-600 animate-spin"></div>
      </div>
      <p className="text-gray-500 font-medium text-sm">Chargement...</p>
    </div>
  </div>
);

export default Loader;
