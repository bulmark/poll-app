import { api } from "./config/axiosConfig";

const UserApi = {
  auth: async function (authData) {
    const response = await api.request({
      url: "/signin",
      method: "POST",
      data: authData,
    });

    return response.data;
  },
};

export default UserApi;
