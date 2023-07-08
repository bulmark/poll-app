import "../assets/index.css";
import "./header.css";
import React from "react";
import { Button } from "antd";
import { LogoutOutlined } from "@ant-design/icons";

const LogInOutButton = () => {
  return (
    <Button className="log-out-button" icon={<LogoutOutlined />}>
      Log out
    </Button>
  );
};

export default LogInOutButton;
