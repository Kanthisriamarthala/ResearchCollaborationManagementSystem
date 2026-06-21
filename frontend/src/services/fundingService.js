import api from "../api/api";

export const getAllFunding = async () => {
    const response = await api.get("/funding");
    return response.data;
};

export const getFundingById = async (id) => {
    const response = await api.get(`/funding/${id}`);
    return response.data;
};

export const createFunding = async (data) => {
    const response = await api.post("/funding", data);
    return response.data;
};

export const updateFunding = async (id, data) => {
    const response = await api.put(`/funding/${id}`, data);
    return response.data;
};

export const deleteFunding = async (id) => {
    const response = await api.delete(`/funding/${id}`);
    return response.data;
};
