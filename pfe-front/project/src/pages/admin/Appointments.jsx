import { useState, useEffect } from "react";
import {
  Calendar,
  Check,
  X,
  Clock,
  Eye,
  AlertCircle,
  Search,
} from "lucide-react";

import SearchBar from "../../components/SearchBar";
import Modal from "../../components/Modal";
import StatCard from "../../components/StatCard";

import {
  getAppointments,
  confirmAppointment,
  cancelAppointment,
  completeAppointment,
} from "../../services/appointmentService";

const getStatusLabelFR = (status) => {
  switch (status) {
    case "pending":
      return "En attente";
    case "confirmed":
      return "Confirmé";
    case "completed":
      return "Terminé";
    case "cancelled":
      return "Annulé";
    default:
      return status;
  }
};

const getStatusStyle = (status) => {
  switch (status) {
    case "confirmed":
      return "bg-emerald-100 text-emerald-700 border border-emerald-200";

    case "pending":
      return "bg-amber-100 text-amber-700 border border-amber-200";

    case "completed":
      return "bg-blue-100 text-blue-700 border border-blue-200";

    case "cancelled":
      return "bg-red-100 text-red-700 border border-red-200";

    default:
      return "bg-gray-100 text-gray-700 border border-gray-200";
  }
};

const Appointments = () => {
  const [appointments, setAppointments] = useState([]);
  const [search, setSearch] = useState("");
  const [filter, setFilter] = useState("all");

  const [showView, setShowView] = useState(false);
  const [selected, setSelected] = useState(null);

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // ================= LOAD =================
  const fetchAppointments = async () => {
    try {
      setLoading(true);
      setError(null);

      const res = await getAppointments();

      // ================= SORT RECENT FIRST =================
      const sortedAppointments = (res.data || [])
        .slice()
        .sort((a, b) => {
          const dateA = new Date(`${a.date} ${a.time}`);
          const dateB = new Date(`${b.date} ${b.time}`);

          return dateB - dateA;
        });

      setAppointments(sortedAppointments);
    } catch (err) {
      console.error(err);
      setError("Impossible de charger les rendez-vous");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchAppointments();
  }, []);

  // ================= ACTIONS =================
  const handleAction = async (type, id) => {
    try {
      setError(null);

      if (type === "confirm") await confirmAppointment(id);

      if (type === "cancel") await cancelAppointment(id);

      if (type === "complete") await completeAppointment(id);

      fetchAppointments();
    } catch (err) {
      console.error(err);
      setError("Action impossible");
    }
  };

  // ================= FILTER =================
  const filtered = appointments.filter((a) => {
    const matchSearch =
      a.citizenName
        ?.toLowerCase()
        .includes(search.toLowerCase()) ||
      a.ticket?.toLowerCase().includes(search.toLowerCase()) ||
      a.serviceName
        ?.toLowerCase()
        .includes(search.toLowerCase());

    if (filter === "all") return matchSearch;

    return matchSearch && a.status === filter;
  });

  // ================= UI =================
  return (
    <div className="space-y-8 p-6 bg-gray-50 min-h-screen">
      {/* ================= HEADER ================= */}
      <div className="flex flex-col md:flex-row md:items-center md:justify-between gap-4">
        <div>
          <h1 className="text-3xl font-bold text-gray-800">
            Gestion des Rendez-vous
          </h1>

          <p className="text-gray-500 mt-1">
            Consultez et gérez les rendez-vous citoyens
          </p>
        </div>

        <div className="bg-white rounded-2xl px-4 py-3 shadow-sm border border-gray-100">
          <span className="text-sm text-gray-500">
            Aujourd'hui :
          </span>

          <p className="font-semibold text-gray-700">
            {new Date().toLocaleDateString("fr-FR", {
              weekday: "long",
              day: "numeric",
              month: "long",
              year: "numeric",
            })}
          </p>
        </div>
      </div>

      {/* ================= ERROR ================= */}
      {error && (
        <div className="bg-red-50 border border-red-200 text-red-600 px-4 py-3 rounded-2xl">
          {error}
        </div>
      )}

      {/* ================= STATS ================= */}
      <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-4 gap-5">
        <StatCard
          title="Total RDV"
          value={appointments.length}
          icon={Calendar}
          color="primary"
        />

        <StatCard
          title="Confirmés"
          value={
            appointments.filter(
              (a) => a.status === "confirmed"
            ).length
          }
          icon={Check}
          color="emerald"
        />

        <StatCard
          title="En attente"
          value={
            appointments.filter(
              (a) => a.status === "pending"
            ).length
          }
          icon={AlertCircle}
          color="amber"
        />

        <StatCard
          title="Annulés"
          value={
            appointments.filter(
              (a) => a.status === "cancelled"
            ).length
          }
          icon={X}
          color="red"
        />
      </div>

      {/* ================= FILTERS ================= */}
      <div className="bg-white rounded-3xl p-5 shadow-sm border border-gray-100">
        <div className="flex flex-col xl:flex-row xl:items-center xl:justify-between gap-5">
          {/* FILTER BUTTONS */}
          <div className="flex flex-wrap gap-3">
            {[
              "all",
              "pending",
              "confirmed",
              "completed",
              "cancelled",
            ].map((t) => (
              <button
                key={t}
                onClick={() => setFilter(t)}
                className={`px-4 py-2 rounded-xl text-sm font-medium transition-all duration-200 ${
                  filter === t
                    ? "bg-blue-600 text-white shadow-lg shadow-blue-200"
                    : "bg-gray-100 text-gray-700 hover:bg-gray-200"
                }`}
              >
                {getStatusLabelFR(t)}
              </button>
            ))}
          </div>

          {/* SEARCH */}
          <div className="w-full xl:w-80 relative">
            <div className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400">
              <Search size={18} />
            </div>

            <input
              type="text"
              placeholder="Rechercher rendez-vous..."
              value={search}
              onChange={(e) => setSearch(e.target.value)}
              className="w-full pl-10 pr-4 py-3 rounded-2xl border border-gray-200 bg-gray-50 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
            />
          </div>
        </div>
      </div>

      {/* ================= TABLE ================= */}
      <div className="bg-white rounded-3xl shadow-sm border border-gray-100 overflow-hidden">
        {/* HEADER */}
        <div className="flex flex-col md:flex-row md:items-center md:justify-between gap-4 px-6 py-5 border-b bg-gradient-to-r from-blue-50 to-white">
          <div>
            <h2 className="text-xl font-bold text-gray-800">
              Rendez-vous récents
            </h2>

            <p className="text-sm text-gray-500 mt-1">
              Les rendez-vous les plus récents apparaissent en premier
            </p>
          </div>

          <div className="bg-blue-100 text-blue-700 px-4 py-2 rounded-full text-sm font-semibold">
            {filtered.length} résultat(s)
          </div>
        </div>

        {/* LOADING */}
        {loading ? (
          <div className="py-20 flex flex-col items-center gap-4">
            <div className="w-12 h-12 border-4 border-blue-500 border-t-transparent rounded-full animate-spin"></div>

            <p className="text-gray-500">
              Chargement des rendez-vous...
            </p>
          </div>
        ) : (
          <div className="overflow-x-auto">
            <table className="w-full min-w-[1100px]">
              <thead className="bg-gray-50">
                <tr className="text-left text-sm uppercase tracking-wider text-gray-500">
                  <th className="px-6 py-4 font-semibold">
                    Ticket
                  </th>

                  <th className="px-6 py-4 font-semibold">
                    Citoyen
                  </th>

                  <th className="px-6 py-4 font-semibold">
                    Service
                  </th>

                  <th className="px-6 py-4 font-semibold">
                    Date
                  </th>

                  <th className="px-6 py-4 font-semibold">
                    Heure
                  </th>

                  <th className="px-6 py-4 font-semibold">
                    Statut
                  </th>

                  <th className="px-6 py-4 font-semibold text-center">
                    Actions
                  </th>
                </tr>
              </thead>

              <tbody className="divide-y divide-gray-100">
                {filtered.length > 0 ? (
                  filtered.map((row, index) => (
                    <tr
                      key={row.id || index}
                      className="hover:bg-blue-50/40 transition duration-200"
                    >
                      {/* TICKET */}
                      <td className="px-6 py-4">
                        <span className="font-semibold text-blue-600">
                          #{row.ticket}
                        </span>
                      </td>

                      {/* CITIZEN */}
                      <td className="px-6 py-4">
                        <div className="flex items-center gap-3">
                          <div className="w-11 h-11 rounded-full bg-blue-100 flex items-center justify-center text-blue-600 font-bold text-sm">
                            {row.citizenName
                              ?.charAt(0)
                              ?.toUpperCase()}
                          </div>

                          <div>
                            <p className="font-semibold text-gray-800">
                              {row.citizenName}
                            </p>

                            <p className="text-xs text-gray-400">
                              Citoyen
                            </p>
                          </div>
                        </div>
                      </td>

                      {/* SERVICE */}
                      <td className="px-6 py-4">
                        <span className="bg-gray-100 text-gray-700 px-3 py-1 rounded-full text-xs font-medium">
                          {row.serviceName}
                        </span>
                      </td>

                      {/* DATE */}
                      <td className="px-6 py-4 text-gray-700 font-medium">
                        {row.date}
                      </td>

                      {/* TIME */}
                      <td className="px-6 py-4 text-gray-600">
                        {row.time}
                      </td>

                      {/* STATUS */}
                      <td className="px-6 py-4">
                        <span
                          className={`px-3 py-1 rounded-full text-xs font-semibold ${getStatusStyle(
                            row.status
                          )}`}
                        >
                          {getStatusLabelFR(row.status)}
                        </span>
                      </td>

                      {/* ACTIONS */}
                      <td className="px-6 py-4">
                        <div className="flex items-center justify-center gap-2">
                          {/* VIEW */}
                          <button
                            onClick={() => {
                              setSelected(row);
                              setShowView(true);
                            }}
                            className="w-9 h-9 rounded-xl bg-blue-100 text-blue-600 flex items-center justify-center hover:bg-blue-600 hover:text-white transition"
                          >
                            <Eye size={16} />
                          </button>

                          {/* CONFIRM */}
                          {row.status === "pending" && (
                            <button
                              onClick={() =>
                                handleAction(
                                  "confirm",
                                  row.id
                                )
                              }
                              className="w-9 h-9 rounded-xl bg-emerald-100 text-emerald-600 flex items-center justify-center hover:bg-emerald-600 hover:text-white transition"
                            >
                              <Check size={16} />
                            </button>
                          )}

                          {/* COMPLETE */}
                          {row.status === "confirmed" && (
                            <button
                              onClick={() =>
                                handleAction(
                                  "complete",
                                  row.id
                                )
                              }
                              className="w-9 h-9 rounded-xl bg-amber-100 text-amber-600 flex items-center justify-center hover:bg-amber-500 hover:text-white transition"
                            >
                              <Clock size={16} />
                            </button>
                          )}

                          {/* CANCEL */}
                          {row.status !== "completed" &&
                            row.status !== "cancelled" && (
                              <button
                                onClick={() =>
                                  handleAction(
                                    "cancel",
                                    row.id
                                  )
                                }
                                className="w-9 h-9 rounded-xl bg-red-100 text-red-600 flex items-center justify-center hover:bg-red-600 hover:text-white transition"
                              >
                                <X size={16} />
                              </button>
                            )}
                        </div>
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td
                      colSpan="7"
                      className="text-center py-16 text-gray-400"
                    >
                      Aucun rendez-vous trouvé
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        )}
      </div>

      {/* ================= MODAL ================= */}
      <Modal
        isOpen={showView}
        onClose={() => setShowView(false)}
        title="Détails du rendez-vous"
      >
        {selected && (
          <div className="space-y-4">
            <div className="bg-gray-50 rounded-2xl p-4">
              <p className="text-sm text-gray-500">
                Ticket
              </p>

              <p className="font-semibold text-gray-800">
                #{selected.ticket}
              </p>
            </div>

            <div className="bg-gray-50 rounded-2xl p-4">
              <p className="text-sm text-gray-500">
                Citoyen
              </p>

              <p className="font-semibold text-gray-800">
                {selected.citizenName}
              </p>
            </div>

            <div className="bg-gray-50 rounded-2xl p-4">
              <p className="text-sm text-gray-500">
                Service
              </p>

              <p className="font-semibold text-gray-800">
                {selected.serviceName}
              </p>
            </div>

            <div className="grid grid-cols-2 gap-4">
              <div className="bg-gray-50 rounded-2xl p-4">
                <p className="text-sm text-gray-500">
                  Date
                </p>

                <p className="font-semibold text-gray-800">
                  {selected.date}
                </p>
              </div>

              <div className="bg-gray-50 rounded-2xl p-4">
                <p className="text-sm text-gray-500">
                  Heure
                </p>

                <p className="font-semibold text-gray-800">
                  {selected.time}
                </p>
              </div>
            </div>

            <div className="bg-gray-50 rounded-2xl p-4">
              <p className="text-sm text-gray-500">
                Statut
              </p>

              <span
                className={`inline-flex mt-2 px-3 py-1 rounded-full text-xs font-semibold ${getStatusStyle(
                  selected.status
                )}`}
              >
                {getStatusLabelFR(selected.status)}
              </span>
            </div>
          </div>
        )}
      </Modal>
    </div>
  );
};

export default Appointments;