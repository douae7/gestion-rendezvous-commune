import { useState, useEffect } from 'react';
import axios from 'axios';
import { Save, Check, X, Sun, Moon, Grid } from 'lucide-react';
import StatCard from '../../components/StatCard';

const API = "http://localhost:8080/api/availability";

const DAYS = ['Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi'];
const SLOTS = ['08:00 - 12:00', '14:00 - 16:30'];

const Availability = () => {

  const [schedule, setSchedule] = useState({});
  const [loading, setLoading] = useState(false);
  const [saved, setSaved] = useState(false);

  // LOAD
  useEffect(() => {

    const user = JSON.parse(localStorage.getItem("user"));
    const agentId = user?.id;
    if (!agentId) return;

    axios.get(`${API}/agent/${agentId}`)
      .then(res => {

        const map = {};

        res.data.forEach(item => {
          const key = `${item.day}-${item.slot}`;
          map[key] = item.available ? 'available' : 'unavailable';
        });

        DAYS.forEach(day => {
          SLOTS.forEach(slot => {
            const key = `${day}-${slot}`;
            if (!map[key]) map[key] = 'available';
          });
        });

        setSchedule(map);
      });

  }, []);

  // TOGGLE
  const toggle = (key) => {
    setSchedule(prev => ({
      ...prev,
      [key]: prev[key] === 'available' ? 'unavailable' : 'available'
    }));
  };

  // =========================
  // TEMPLATES (NEW)
  // =========================

  const setAll = () => {
    const s = {};
    DAYS.forEach(d => {
      SLOTS.forEach(sl => {
        s[`${d}-${sl}`] = 'available';
      });
    });
    setSchedule(s);
  };

  const setMorning = () => {
    const s = {};
    DAYS.forEach(d => {
      SLOTS.forEach(sl => {
        s[`${d}-${sl}`] = sl.startsWith('08') ? 'available' : 'unavailable';
      });
    });
    setSchedule(s);
  };

  const setAfternoon = () => {
    const s = {};
    DAYS.forEach(d => {
      SLOTS.forEach(sl => {
        s[`${d}-${sl}`] = sl.startsWith('14') ? 'available' : 'unavailable';
      });
    });
    setSchedule(s);
  };

  // SAVE
  const handleSave = async () => {

    const user = JSON.parse(localStorage.getItem("user"));
    const agentId = user?.id;
    if (!agentId) return;

    const payload = Object.entries(schedule).map(([key, value]) => {
      const dashIndex = key.indexOf('-');
      const day = key.substring(0, dashIndex).trim();
      const slot = key.substring(dashIndex + 1).trim();

      return {
        day,
        slot,
        available: value === 'available',
        agentId
      };
    });

    try {
      setLoading(true);

      await axios.post(API, payload, {
        headers: { "Content-Type": "application/json" }
      });

      setSaved(true);
      setTimeout(() => setSaved(false), 2000);

    } catch (err) {
      console.error(err.response?.data || err.message);
    } finally {
      setLoading(false);
    }
  };

  const available = Object.values(schedule).filter(v => v === 'available').length;
  const unavailable = Object.values(schedule).filter(v => v === 'unavailable').length;

  const cellColor = {
    available: "bg-emerald-100 text-emerald-700 border border-emerald-200 hover:bg-emerald-200 transition",
    unavailable: "bg-gray-100 text-gray-500 border border-gray-200 hover:bg-gray-200 transition"
  };

  return (
    <div className="space-y-6 p-6 bg-gray-50 min-h-screen">

      {/* HEADER */}
      <div className="flex justify-between items-center bg-white p-4 rounded-xl shadow-sm">

        <h2 className="text-xl font-semibold text-gray-800">Disponibilité Agent</h2>

        <button
          onClick={handleSave}
          disabled={loading}
          className="bg-blue-600 hover:bg-blue-700 text-white px-5 py-2 rounded-lg flex items-center gap-2 transition"
        >
          <Save size={16} />
          {loading ? "Sauvegarde..." : saved ? "Enregistré !" : "Enregistrer"}
        </button>

      </div>

      {/* STATS */}
      <div className="grid grid-cols-2 gap-4">
        <StatCard title="Disponibles" value={available} icon={Check} />
        <StatCard title="Indisponibles" value={unavailable} icon={X} />
      </div>

      {/* TEMPLATES BUTTONS */}
      <div className="flex flex-wrap gap-3">

        <button onClick={setAll} className="btn-modern">
          <Grid size={16} /> Tout disponible
        </button>

        <button onClick={setMorning} className="btn-modern">
          <Sun size={16} /> Matin
        </button>

        <button onClick={setAfternoon} className="btn-modern">
          <Moon size={16} /> Après-midi
        </button>

      </div>

      {/* TABLE */}
      <div className="bg-white p-4 rounded-xl shadow overflow-x-auto">

        <table className="w-full border-collapse">

          <thead>
            <tr className="text-gray-600 border-b">
              <th className="text-left p-3">Créneaux</th>
              {DAYS.map(d => (
                <th key={d} className="text-center p-3">{d}</th>
              ))}
            </tr>
          </thead>

          <tbody>

            {SLOTS.map(slot => (
              <tr key={slot} className="border-b">

                <td className="p-3 font-medium text-gray-700">{slot}</td>

                {DAYS.map(day => {

                  const key = `${day}-${slot}`;
                  const status = schedule[key] || 'available';

                  return (
                    <td key={key} className="p-2 text-center">

                      <button
                        onClick={() => toggle(key)}
                        className={`w-full py-3 rounded-lg text-sm font-medium ${cellColor[status]}`}
                      >
                        {status === 'available'
                          ? <Check size={14} />
                          : <X size={14} />
                        }
                      </button>

                    </td>
                  );

                })}

              </tr>
            ))}

          </tbody>

        </table>

      </div>

      {/* STYLE BUTTONS */}
      <style>{`
        .btn-modern {
          display:flex;
          align-items:center;
          gap:6px;
          padding:8px 12px;
          border-radius:10px;
          background:white;
          border:1px solid #e5e7eb;
          font-size:13px;
          transition:0.2s;
        }
        .btn-modern:hover {
          background:#f3f4f6;
          transform:translateY(-1px);
        }
      `}</style>

    </div>
  );
};

export default Availability;