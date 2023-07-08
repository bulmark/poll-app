import "../assets/index.css";
import { Menu, Layout } from "antd";
import React from "react";
import { menuItems } from "../../constants/MenuItems";

const { Sider } = Layout;

const CustomSider = ({ selectedMenuItem, handleMenuItemSelect }) => {
  return (
    <Sider className="sider-menu">
      <Menu
        className="sider-menu"
        mode="inline"
        selectedKeys={[selectedMenuItem]}
        onSelect={handleMenuItemSelect}
      >
        <Menu.SubMenu title="Polls">
          <Menu.Item key={menuItems.available}>Available</Menu.Item>
          <Menu.Item key={menuItems.created}>Created</Menu.Item>
          {/*<Menu.Item key={menuItems.tracked}>Tracked</Menu.Item>*/}
        </Menu.SubMenu>
        <Menu.Item key={menuItems.createPoll}>Create poll</Menu.Item>
        <Menu.Item key={menuItems.changePassword}>Change password</Menu.Item>
        <Menu.Item key={menuItems.adminPanel}>Admin panel</Menu.Item>
      </Menu>
    </Sider>
  );
};

export default CustomSider;
