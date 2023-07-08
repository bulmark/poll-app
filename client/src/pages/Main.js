import "./assets/index.css";
import React, { useState } from "react";
import { Layout } from "antd";
import Logo from "../components/header/Logo";
import LogInOutButton from "../components/header/LogInOutButton";
import AvailablePollsTable from "../components/tables/AvailablePollsTable";
import CreatedPollsTable from "../components/tables/CreatedPollsTable";
import TrackedPollTable from "../components/tables/TrackedPollTable";
import Sider from "../components/sider/Sider";
import PollConstructor from "../components/PollConstructor";
import { menuItems } from "../constants/MenuItems";

const { Header, Content } = Layout;

const Main = () => {
  const [content, setContent] = useState(() => (
    <AvailablePollsTable></AvailablePollsTable>
  ));
  const [selectedMenuItem, setSelectedMenuItem] = useState(menuItems.available);

  const handleMenuItemSelect = ({ key }) => {
    setSelectedMenuItem(key);
    switch (key) {
      case menuItems.available:
        setContent(() => <AvailablePollsTable></AvailablePollsTable>);
        break;
      case menuItems.created:
        setContent(() => <CreatedPollsTable></CreatedPollsTable>);
        break;
      case menuItems.tracked:
        setContent(() => <TrackedPollTable></TrackedPollTable>);
        break;
      case menuItems.createPoll:
        setContent(() => <PollConstructor></PollConstructor>);
    }
  };

  return (
    <Layout>
      <Header className="header">
        <Logo></Logo>
        <LogInOutButton></LogInOutButton>
      </Header>
      <Layout>
        <Sider
          a="dsd"
          selectedMenuItem={selectedMenuItem}
          handleMenuItemSelect={handleMenuItemSelect}
        />
        <Content className="content">{content}</Content>
      </Layout>
    </Layout>
  );
};
export default Main;
