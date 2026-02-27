import axiosClient from "./axiosClient";
import { unwrapPageResponse, mapId } from "./responseMapper";

export const getCategories = async (params) => {
  const res = await axiosClient.get("/categories", { params });
  return unwrapPageResponse(res);
};

export const createCategory = async (data) => {
  const res = await axiosClient.post("/categories", data);
  return { ...res, data: mapId(res.data) };
};

export const updateCategory = async (id, data) => {
  const res = await axiosClient.put(`/categories/${id}`, data);
  return { ...res, data: mapId(res.data) };
};

export const deleteCategory = (id) => axiosClient.delete(`/categories/${id}`);
