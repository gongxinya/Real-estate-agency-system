import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import React from "react";
import Login from "../src/components/Login";
import Register from "../src/components/Register";
import FaltTable from "../src/components/FaltTable";
import GuestTable from "./components/FlatTable_Guest";
import AddFlat from "../src/components/AddFlat";
import AdminScreen from '../src/screens/AdminScreen';
import Routes from "../src/Routes"
import UserProfile from '../src/components/UserProfile';
import UserForm from '../src/components/UserForm';
import RolePermissionTable from "../src/components/RolePermissionTable";
import BuildingTable from "../src/components/BuildingTable";
import AddBuilding from '../src/components/AddBuilding';
import BuildingTable_Guest from "../src/components/BuildingTable_Guest";
import GuestScreen from '../src/screens/GuestScreen';


const App = () => {
  return (
    <Router>
      <div className="App">
        <Routes />
      </div>
    </Router>
    // <div>
    //   <GuestScreen/>
    // </div>
  );
};

export default App;
