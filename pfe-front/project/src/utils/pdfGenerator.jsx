import { jsPDF } from 'jspdf';
import { formatDate, formatTime, generateTicketNumber } from './helpers';

/* ===============================
   SAFE HELPERS (ROBUSTES)
=============================== */

const safeText = (v) => (v ? String(v) : 'N/A');

const extractDate = (apt) =>
  apt.date || apt.startDateTime?.split('T')[0] || 'N/A';

const extractTime = (apt) =>
  apt.time || apt.startDateTime?.split('T')[1]?.slice(0, 5) || 'N/A';

const extractService = (apt) =>
  apt.serviceName ||
  apt.department?.name ||
  'Service';

const extractCitizen = (apt) =>
  apt.citizenName ||
  apt.citizen?.name ||
  'Citoyen';

const extractAgent = (apt) =>
  apt.agentName ||
  apt.agent?.name ||
  'Agent inconnu';

const extractOffice = (apt) =>
  apt.bureau ||
  apt.agent?.bureau ||
  'Bureau non défini';


/* =========================================
   PDF RENDEZ-VOUS
========================================= */

export const generateAppointmentPDF = (appointment) => {
  const doc = new jsPDF();

  const ticket =
    appointment.ticket ||
    appointment.ticketNumber ||
    generateTicketNumber();

  const citizenName = extractCitizen(appointment);
  const serviceName = extractService(appointment);
  const agentName = extractAgent(appointment);
  const office = extractOffice(appointment);
  const date = extractDate(appointment);
  const time = extractTime(appointment);

  let y = 55;

  /* ================= HEADER ================= */
  doc.setFillColor(30, 58, 95);
  doc.rect(0, 0, 210, 40, 'F');

  doc.setTextColor(255, 255, 255);
  doc.setFontSize(18);
  doc.text('MUNICIPALITÉ', 105, 18, { align: 'center' });

  doc.setFontSize(12);
  doc.text('Confirmation de Rendez-vous', 105, 30, { align: 'center' });

  /* ================= TICKET ================= */
  doc.setFillColor(37, 99, 235);
  doc.roundedRect(25, y - 5, 160, 18, 3, 3, 'F');

  doc.setFontSize(14);
  doc.text(`Ticket: ${ticket}`, 105, y + 6, { align: 'center' });

  y += 30;

  /* ================= INFOS ================= */
  doc.setTextColor(30, 58, 95);
  doc.setFontSize(11);

  const fields = [
    ['Citoyen', citizenName],
    ['Service', serviceName],
    ['Agent', agentName],
    ['Bureau', office],
    ['Date', date],
    ['Heure', time],
    ['Statut', appointment.status || 'pending'],
  ];

  fields.forEach(([label, value]) => {
    doc.setFont('helvetica', 'bold');
    doc.text(`${label}:`, 25, y);

    doc.setFont('helvetica', 'normal');
    doc.text(safeText(value), 70, y);

    y += 10;
  });

  y += 5;
  doc.line(25, y, 185, y);
  y += 10;

  /* ================= INSTRUCTIONS ================= */
  doc.setFont('helvetica', 'bold');
  doc.text('Instructions :', 25, y);
  y += 8;

  doc.setFont('helvetica', 'normal');

  const instructions = [
    'Présentez-vous 10 min avant',
    'Apportez vos documents',
    'Annulation 24h avant',
    'Gardez votre ticket'
  ];

  instructions.forEach((line) => {
    doc.text(`- ${line}`, 25, y);
    y += 7;
  });

  /* ================= FOOTER ================= */
  doc.setFillColor(30, 58, 95);
  doc.rect(0, 270, 210, 27, 'F');

  doc.setTextColor(255, 255, 255);
  doc.setFontSize(9);

  doc.text('Document généré automatiquement', 105, 280, { align: 'center' });

  doc.text(
    `Généré le ${formatDate(new Date())} à ${formatTime(new Date())}`,
    105,
    288,
    { align: 'center' }
  );

  doc.save(`RendezVous_${citizenName}.pdf`);
};


/* =========================================
   PDF HISTORIQUE
========================================= */

export const generateHistoryPDF = (citizen, appointments) => {
  const doc = new jsPDF();

  const citizenName =
    citizen?.name ||
    citizen?.fullName ||
    citizen?.username ||
    'Citoyen';

  let y = 70;

  /* HEADER */
  doc.setFillColor(30, 58, 95);
  doc.rect(0, 0, 210, 40, 'F');

  doc.setTextColor(255, 255, 255);
  doc.setFontSize(18);
  doc.text('Historique des Rendez-vous', 105, 25, { align: 'center' });

  /* CITIZEN */
  doc.setTextColor(30, 58, 95);
  doc.setFontSize(12);
  doc.text(`Citoyen : ${citizenName}`, 25, 55);

  /* TABLE HEADER */
  doc.setFillColor(37, 99, 235);
  doc.rect(10, y - 5, 190, 8, 'F');

  doc.setTextColor(255, 255, 255);
  doc.setFontSize(9);

  doc.text('Service', 12, y);
  doc.text('Agent', 55, y);
  doc.text('Bureau', 90, y);
  doc.text('Date', 125, y);
  doc.text('Heure', 160, y);

  y += 10;

  /* ROWS */
  doc.setTextColor(30, 58, 95);

  appointments.forEach((apt, i) => {
    if (y > 260) {
      doc.addPage();
      y = 20;
    }

    const service = extractService(apt);
    const agent = extractAgent(apt);
    const office = extractOffice(apt);
    const date = extractDate(apt);
    const time = extractTime(apt);

    if (i % 2 === 0) {
      doc.setFillColor(243, 244, 246);
      doc.rect(10, y - 5, 190, 8, 'F');
    }

    doc.text(service, 12, y);
    doc.text(agent, 55, y);
    doc.text(office, 90, y);
    doc.text(date, 125, y);
    doc.text(time, 160, y);

    y += 8;
  });

  /* FOOTER */
  doc.setFillColor(30, 58, 95);
  doc.rect(0, 270, 210, 27, 'F');

  doc.setTextColor(255, 255, 255);
  doc.setFontSize(9);

  doc.text('Municipalité - Historique', 105, 284, { align: 'center' });

  doc.save(`Historique_${citizenName}.pdf`);
};