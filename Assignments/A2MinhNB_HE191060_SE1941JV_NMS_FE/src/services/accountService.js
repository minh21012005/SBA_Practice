import axiosClient from "./axiosClient";
import { unwrapPageResponse, mapId } from "./responseMapper";

export const getAccounts = async (params) => {
  const res = await axiosClient.get("/accounts", { params });
  return unwrapPageResponse(res);
};

export const createAccount = async (data) => {
  const res = await axiosClient.post("/accounts", data);
  return { ...res, data: mapId(res.data) };
};

export const updateAccount = async (id, data) => {
  const res = await axiosClient.put(`/accounts/${id}`, data);
  return { ...res, data: mapId(res.data) };
};

export const deleteAccount = (id) => axiosClient.delete(`/accounts/${id}`);

export const getProfile = async () => {
  const res = await axiosClient.get(`/accounts/me`);
  return { ...res, data: mapId(res.data) };
};

export const updateProfile = async (data) => {
  const res = await axiosClient.put(`/accounts/me`, data);
  return { ...res, data: mapId(res.data) };
};
