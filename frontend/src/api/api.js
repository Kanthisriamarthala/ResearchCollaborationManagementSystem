import axios from "axios";

const api = axios.create({
    baseURL: "http://localhost:8081/api",
    headers: { "Content-Type": "application/json" }
});

api.interceptors.response.use(
    (response) => {
        if (response.data && typeof response.data === "object" && "data" in response.data) {
            response.data = response.data.data;
        }
        return response;
    },
    (error) => {
        const message = error.response?.data?.message || error.message || "Something went wrong";
        return Promise.reject(new Error(message));
    }
);

export default api;
