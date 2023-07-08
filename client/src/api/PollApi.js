import { api } from "./config/axiosConfig";

const PollApi = {
  get: async function (id) {
    const response = await api.request({
      url: `/polls/${id}`,
      method: "GET",
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    });

    return response.data;
  },

  getAvailable: async function (page, limit) {
    const response = await api.request({
      url: `/polls/available`,
      method: "GET",
      params: {
        page: page,
        limit,
      },
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    });

    return response.data;
  },

  getCreated: async function (page, limit) {
    const response = await api.request({
      url: `/polls/created`,
      method: "GET",
      params: {
        page: page,
        limit,
      },
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    });

    return response.data;
  },
};

export default PollApi;
