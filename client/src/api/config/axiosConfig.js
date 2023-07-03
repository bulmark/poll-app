import axios from "axios";

export const api = axios.create({
  baseURL: "http://localhost:8080",
});

const errorHandler = (error) => {
  console.log(error);
  return Promise.reject(error);
};

api.interceptors.response.use(undefined, (error) => {
  return errorHandler(error);
});
