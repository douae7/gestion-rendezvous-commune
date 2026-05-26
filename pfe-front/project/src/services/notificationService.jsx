import API from './api';

export const getNotifications = async () => { const r = await API.get('/notifications'); return r.data; };
export const markAsRead = async (id) => { const r = await API.put(`/notifications/${id}/read`); return r.data; };
export const markAllAsRead = async () => { const r = await API.put('/notifications/read-all'); return r.data; };
export const deleteNotification = async (id) => { const r = await API.delete(`/notifications/${id}`); return r.data; };
