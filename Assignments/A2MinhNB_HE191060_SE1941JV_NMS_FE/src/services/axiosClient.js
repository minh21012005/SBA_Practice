import axios from "axios";

const BASE_URL = import.meta?.env?.VITE_API_URL || "http://localhost:8080";
const API_BASE = BASE_URL.replace(/\/$/, "") + "/api";

const axiosClient = axios.create({
  baseURL: API_BASE,
  timeout: 5000,
  headers: {
    "Content-Type": "application/json",
  },
});

// Attach auth token if present
axiosClient.interceptors.request.use((config) => {
  try {
    const token = localStorage.getItem("funews_token");
    if (token && token.trim()) {
      config.headers = config.headers || {};
      config.headers["Authorization"] = `Bearer ${token}`;
    }
  } catch (e) {
    // ignore
  }
  return config;
});

// Global response handler: redirect on 401 and normalize errors
axiosClient.interceptors.response.use(
  (response) => response,
  (error) => {
    const status = error?.response?.status;
    const isAuthRequest = error?.config?.url?.includes("/auth/login") || error?.config?.url?.includes("/auth/me");

    if (status === 401 && !isAuthRequest) {
      try {
        localStorage.removeItem("funews_token");
        localStorage.removeItem("funews_account");
      } catch (e) {}
      // force full redirect to login
      window.location.href = "/login";
    }
    return Promise.reject(error);
  },
);

export default axiosClient;
