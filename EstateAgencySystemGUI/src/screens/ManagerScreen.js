import React, { useState } from "react";
import { Layout, Menu } from "antd";
import FlatTable from '../components/FaltTable'
import BuildingTable from '../components/FaltTable'
import MyHeader from '../components/MyHeader'

const { Content } = Layout;

const ManagerScreen = () => {
    const [currentMenu, setCurrentMenu] = useState("FlatList");
  
    const handleMenuClick = (e) => {
      setCurrentMenu(e.key);
    };
  
  
    return (
      <Layout style={{ minHeight: "100vh" }}>
        <div style={{ position: "relative" }}>
          <Menu onClick={handleMenuClick} selectedKeys={[currentMenu]} mode="horizontal">
  

            <Menu.Item key="FlatList">
              Flat List
            </Menu.Item>
  
            <Menu.Item key="BuildingList">
              Building List
            </Menu.Item>
  
          </Menu>
  
          <MyHeader />
  
        </div>
        <Content style={{ padding: "10px" }}>
         
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
         
        </Content>
      </Layout>
    );
  };
  
  export default ManagerScreen;
  