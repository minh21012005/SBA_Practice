import axiosClient from "./axiosClient";
import { mapId } from "./responseMapper";

export const login = async (email, password) => {
  const res = await axiosClient.post("/auth/login", { email, password });
  // res.data -> { token, account }
  const token = res.data?.token;
  const account = mapId(res.data?.account || {});
  // store token directly (it's already a string, no need to JSON.stringify)
  if (token) localStorage.setItem("funews_token", token);
  if (account) localStorage.setItem("funews_account", JSON.stringify(account));
  return { ...res, data: { token, account } };
};

export const me = async () => {
  const res = await axiosClient.get("/auth/me");
  return { ...res, data: mapId(res.data) };
};
