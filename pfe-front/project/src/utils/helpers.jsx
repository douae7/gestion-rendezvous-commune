export const formatDate = (date) => {
  if (!date) return '';
  return new Date(date).toLocaleDateString('fr-FR', {
    day: '2-digit',
    month: '2-digit',
    year: 'numeric'
  });
};

export const formatTime = (date) => {
  if (!date) return '';
  return new Date(date).toLocaleTimeString('fr-FR', {
    hour: '2-digit',
    minute: '2-digit'
  });
};

export const formatDateTime = (date) => {
  if (!date) return '';
  return `${formatDate(date)} - ${formatTime(date)}`;
};

/* =======================
   STATUS (IMPORTANT FIX)
   ======================= */
// ⚠ backend: CONFIRMED / CANCELLED / PENDING etc
export const getStatusColor = (status) => {
  const key = (status || '').toUpperCase();

  const colors = {
    CONFIRMED: 'bg-emerald-100 text-emerald-700 border-emerald-200',
    CANCELLED: 'bg-red-100 text-red-700 border-red-200',
    PENDING: 'bg-blue-100 text-blue-700 border-blue-200',
    COMPLETED: 'bg-purple-100 text-purple-700 border-purple-200',
    POSTPONED: 'bg-orange-100 text-orange-700 border-orange-200',
  };

  return colors[key] || 'bg-gray-100 text-gray-700 border-gray-200';
};

export const getStatusLabel = (status) => {
  const key = (status || '').toUpperCase();

  const labels = {
    CONFIRMED: 'Confirmé',
    CANCELLED: 'Annulé',
    PENDING: 'En attente',
    COMPLETED: 'Terminé',
    POSTPONED: 'Reporté',
  };

  return labels[key] || status;
};

export const getRoleLabel = (role) => {
  const labels = {
    admin: 'Administrateur',
    citizen: 'Citoyen',
    agent: 'Agent Municipal'
  };
  return labels[role] || role;
};

export const getRoleColor = (role) => {
  const colors = {
    admin: 'bg-primary-100 text-primary-700',
    citizen: 'bg-emerald-100 text-emerald-700',
    agent: 'bg-amber-100 text-amber-700'
  };
  return colors[role] || 'bg-gray-100 text-gray-700';
};

/* =======================
   TICKET (IMPROVED)
   ======================= */
export const generateTicketNumber = () => {
  const d = new Date();

  const datePart = `${d.getFullYear()}${String(d.getMonth() + 1).padStart(2, '0')}${String(d.getDate()).padStart(2, '0')}`;

  const randomPart = Math.floor(Math.random() * 9999)
    .toString()
    .padStart(4, '0');

  return `MUN-${datePart}-${randomPart}`;
};

/* =======================
   TIME AGO
   ======================= */
export const timeAgo = (date) => {
  if (!date) return '';

  const s = Math.floor((new Date() - new Date(date)) / 1000);

  if (s < 60) return "À l'instant";

  const m = Math.floor(s / 60);
  if (m < 60) return `Il y a ${m} min`;

  const h = Math.floor(m / 60);
  if (h < 24) return `Il y a ${h}h`;

  const d = Math.floor(h / 24);
  if (d < 30) return `Il y a ${d}j`;

  return `Il y a ${Math.floor(d / 30)} mois`;
};

/* =======================
   SERVICES (OPTION FRONT)
   ======================= */
export const MUNICIPAL_SERVICES = [
  { id: 1, name: 'État Civil', description: 'Actes de naissance, mariage, décès' },
  { id: 2, name: 'Légalisation Signature', description: 'Légalisation de signatures et documents' },
  { id: 3, name: 'Certificat de Résidence', description: 'Attestation de résidence' },
  { id: 4, name: 'Urbanisme', description: 'Permis de construire, plans urbains' },
  { id: 5, name: 'Permis de Construire', description: 'Demande de permis de construire' },
  { id: 6, name: 'Taxe Locale', description: 'Paiement taxes et impôts locaux' },
  { id: 7, name: 'Réclamation', description: 'Dépôt de réclamations' },
  { id: 8, name: 'Assistance Sociale', description: 'Aide sociale et solidarité' },
];

/* =======================
   TIME SLOTS
   ======================= */
export const TIME_SLOTS = [
  '08:00', '08:30', '09:00', '09:30',
  '10:00', '10:30', '11:00', '11:30',
  '14:00', '14:30', '15:00', '15:30',
  '16:00', '16:30'
];

/* =======================
   LANGUAGES
   ======================= */
export const LANGUAGES = [
  { code: 'fr', label: 'Français', flag: 'FR' },
  { code: 'ar', label: 'العربية', flag: 'AR' },
  { code: 'en', label: 'English', flag: 'EN' },
];