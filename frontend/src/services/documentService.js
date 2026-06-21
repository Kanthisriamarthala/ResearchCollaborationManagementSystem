import api from "../api/api";

export const getAllDocuments = async () => {
    const response = await api.get("/documents");
    return response.data;
};

export const createDocument = async (data) => {
    const response = await api.post("/documents", data);
    return response.data;
};

export const deleteDocument = async (id) => {
    const response = await api.delete(`/documents/${id}`);
    return response.data;
};
