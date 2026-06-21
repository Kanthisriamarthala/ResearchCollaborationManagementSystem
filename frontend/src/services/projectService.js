import api from "../api/api";

export const getAllProjects = async () => {
    const response = await api.get("/projects");
    return response.data;
};

export const getProjectById = async (id) => {
    const response = await api.get(`/projects/${id}`);
    return response.data;
};

export const createProject = async (project) => {
    const response = await api.post("/projects", project);
    return response.data;
};

export const updateProject = async (id, project) => {
    const response = await api.put(`/projects/${id}`, project);
    return response.data;
};

export const deleteProject = async (id) => {
    const response = await api.delete(`/projects/${id}`);
    return response.data;
};

export const searchProjects = async (keyword) => {
    const response = await api.get(`/projects/search?keyword=${keyword}`);
    return response.data;
};
