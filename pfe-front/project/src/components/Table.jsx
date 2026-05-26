const Table = ({ columns, data, onRowClick, emptyMessage = 'Aucune donnée disponible' }) => {
  if (!data?.length) {
    return <div className="card p-8 text-center"><p className="text-gray-400 text-sm">{emptyMessage}</p></div>;
  }

  return (
    <div className="card overflow-hidden">
      <div className="overflow-x-auto">
        <table className="w-full">
          <thead>
            <tr className="bg-gray-50 border-b border-gray-100">
              {columns.map((col) => (
                <th key={col.key} className="px-6 py-3 text-left text-xs font-semibold text-gray-500 uppercase tracking-wider">{col.label}</th>
              ))}
            </tr>
          </thead>
          <tbody className="divide-y divide-gray-50">
            {data.map((row, i) => (
              <tr key={row.id || i} onClick={() => onRowClick?.(row)} className={`hover:bg-gray-50/50 transition-colors ${onRowClick ? 'cursor-pointer' : ''}`}>
                {columns.map((col) => (
                  <td key={col.key} className="px-6 py-4 text-sm text-gray-700 whitespace-nowrap">
                    {col.render ? col.render(row[col.key], row) : row[col.key]}
                  </td>
                ))}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Table;
