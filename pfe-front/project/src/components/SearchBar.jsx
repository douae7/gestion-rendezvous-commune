import { Search } from 'lucide-react';
import { useState } from 'react';

const SearchBar = ({ placeholder = 'Rechercher...', onSearch, className = '' }) => {
  const [query, setQuery] = useState('');

  const handleSubmit = (e) => { e.preventDefault(); onSearch?.(query); };
  const handleChange = (e) => { setQuery(e.target.value); if (!e.target.value) onSearch?.(''); };

  return (
    <form onSubmit={handleSubmit} className={`relative ${className}`}>
      <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" size={18} />
      <input type="text" value={query} onChange={handleChange} placeholder={placeholder} className="input-field pl-10 pr-4" />
    </form>
  );
};

export default SearchBar;
