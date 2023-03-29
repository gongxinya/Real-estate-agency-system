import React, { useState } from "react";
import { Layout, Menu } from "antd";

import FlatTable from "../components/FaltTable";
import UserTable from "../components/UserTable";
import BuildingTable from "../components/BuildingTable";
import RolePermissionTable from "../components/RolePermissionTable";
import MyHeader from "../components/MyHeader";

const { Content } = Layout;

const AdminScreen = () => {
  const [currentMenu, setCurrentMenu] = useState("UserList");



  const handleMenuClick = (e) => {
    setCurrentMenu(e.key);
  };


  return (
    <Layout style={{ minHeight: "100vh" }}>
      <div style={{ position: "relative" }}>
        <Menu onClick={handleMenuClick} selectedKeys={[currentMenu]} mode="horizontal">

          <Menu.Item key="UserList">
            User List
          </Menu.Item>

          <Menu.Item key="FlatList">
            Flat List
          </Menu.Item>

          <Menu.Item key="BuildingList">
            Building List
          </Menu.Item>

          <Menu.Item key="RoleList">
            Role List
          </Menu.Item>

        </Menu>

        <MyHeader />

      </div>
      <Content style={{ padding: "10px" }}>
        {currentMenu === "UserList" && (
          <div>
            <UserTable />
          </div>
        )}
        {currentMenu === "FlatList" && (
          <div>
            <FlatTable />
          </div>
        )}
        {currentMenu === "BuildingList" && (
          <div>
            <BuildingTable />
          </div>
        )}
        {currentMenu === "RoleList" && (
          <div>
            <RolePermissionTable />
          </div>
        )}
      </Content>
    </Layout>
  );
};

export default AdminScreen;
