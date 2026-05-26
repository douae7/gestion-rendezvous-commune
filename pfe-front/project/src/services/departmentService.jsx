import API from './api';

export const getAllDepartments = async () => { const r = await API.get('/departments'); return r.data; };
export const getDepartmentById = async (id) => { const r = await API.get(`/departments/${id}`); return r.data; };
export const createDepartment = async (data) => { const r = await API.post('/departments', data); return r.data; };
export const updateDepartment = async (id, data) => { const r = await API.put(`/departments/${id}`, data); return r.data; };
export const deleteDepartment = async (id) => { const r = await API.delete(`/departments/${id}`); return r.data; };
