import api from "../api/api";

export const getAllPublications = async () => {
    const response = await api.get("/publications");
    return response.data;
};

export const getPublicationById = async (id) => {
    const response = await api.get(`/publications/${id}`);
    return response.data;
};

export const createPublication = async (data) => {
    const response = await api.post("/publications", data);
    return response.data;
};

export const updatePublication = async (id, data) => {
    const response = await api.put(`/publications/${id}`, data);
    return response.data;
};

export const deletePublication = async (id) => {
    const response = await api.delete(`/publications/${id}`);
    return response.data;
};
