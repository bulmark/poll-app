import { api } from "./config/axiosConfig";
import { defineCancelApiObject } from "./config/axiosUtils";

export const PollApi = {
    token: null,

  get: async function (id, cancel = false) {
      const response = await api.request({
          url: `/polls/:id`,
          method: "GET",
          headers: {
              Authorization: `Bearer ${token}`
          }
      });

      return response.dataz
      )
  },
};
