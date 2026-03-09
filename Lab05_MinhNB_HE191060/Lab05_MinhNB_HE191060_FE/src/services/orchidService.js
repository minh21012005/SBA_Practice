import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/orchids';

export const getAllOrchids = () => {
    return axios.get(API_BASE_URL);
};

export const getOrchidById = (id) => {
    return axios.get(`${API_BASE_URL}/${id}`);
};

export const createOrchid = (data) => {
    return axios.post(API_BASE_URL, data);
};

export const updateOrchid = (id, data) => {
    return axios.put(`${API_BASE_URL}/${id}`, data);
};

export const deleteOrchid = (id) => {
    return axios.delete(`${API_BASE_URL}/${id}`);
};
