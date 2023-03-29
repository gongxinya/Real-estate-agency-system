import React, { useState } from "react";
import { Layout, Menu } from "antd";
import { EditOutlined, DeleteOutlined } from '@ant-design/icons';
import { Link } from 'react-router-dom';
import { HomeOutlined, LogoutOutlined } from "@ant-design/icons";
import FlatTable_Guest from '../components/FlatTable_Guest'
import BuildingTable_Guest from '../components/BuildingTable_Guest'
import MyHeader from '../components/MyHeader'

const { Content } = Layout;

const GuestScreen = () => {
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
              <FlatTable_Guest />
            </div>
          )}
          {currentMenu === "BuildingList" && (
            <div>
              <BuildingTable_Guest />
            </div>
          )}
         
        </Content>
      </Layout>
    );
  };
  
  export default GuestScreen;
  