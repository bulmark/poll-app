import React, { useEffect } from "react";
import "./App.css";
import Main from "./pages/Main";
import UserApi from "./api/UserApi";

const testAuthData = {
  username: "moder2",
  password: "moder",
};

function App() {
  useEffect(() => {
    UserApi.auth(testAuthData).then(async function (response) {
      localStorage.setItem("token", response.token);
    });
  }, []);
  return <Main></Main>;
}

export default App;
