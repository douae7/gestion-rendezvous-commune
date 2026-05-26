import API from './api';

export const getAllCitizens = async () => { const r = await API.get('/users?role=citizen'); return r.data; };
export const getAllAgents = async () => { const r = await API.get('/users?role=agent'); return r.data; };
export const getUserById = async (id) => { const r = await API.get(`/users/${id}`); return r.data; };
export const updateUser = async (id, data) => { const r = await API.put(`/users/${id}`, data); return r.data; };
export const deleteUser = async (id) => { const r = await API.delete(`/users/${id}`); return r.data; };
export const updateProfile = async (data) => { const r = await API.put('/users/profile', data); return r.data; };
