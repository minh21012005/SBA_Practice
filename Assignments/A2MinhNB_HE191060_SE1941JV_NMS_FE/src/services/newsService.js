import axiosClient from "./axiosClient";
import { unwrapPageResponse, mapId } from "./responseMapper";

export const getNews = async (params) => {
  const res = await axiosClient.get("/news", { params });
  return unwrapPageResponse(res);
};

export const getMyNews = async (params) => {
  const res = await axiosClient.get("/my/news", { params });
  return unwrapPageResponse(res);
};

export const createNews = async (data) => {
  const res = await axiosClient.post("/news", data);
  return { ...res, data: mapId(res.data) };
};

export const updateNews = async (id, data) => {
  const res = await axiosClient.put(`/news/${id}`, data);
  return { ...res, data: mapId(res.data) };
};

export const deleteNews = (id) => axiosClient.delete(`/news/${id}`);
