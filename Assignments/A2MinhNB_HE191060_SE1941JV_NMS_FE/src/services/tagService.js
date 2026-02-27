import axiosClient from "./axiosClient";
import { unwrapPageResponse, mapId } from "./responseMapper";

export const getTags = async (params) => {
  const res = await axiosClient.get("/tags", { params });
  return unwrapPageResponse(res);
};

export const createTag = async (data) => {
  const res = await axiosClient.post("/tags", data);
  return { ...res, data: mapId(res.data) };
};

export const updateTag = async (id, data) => {
  const res = await axiosClient.put(`/tags/${id}`, data);
  return { ...res, data: mapId(res.data) };
};

export const deleteTag = (id) => axiosClient.delete(`/tags/${id}`);
