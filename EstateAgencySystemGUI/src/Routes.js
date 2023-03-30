import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import React from "react";
import Login from "../src/components/Login";
import Register from "../src/components/Register";
import AddFlat from "../src/components/AddFlat";
import AdminScreen from '../src/screens/AdminScreen';
import UserProfile from "../src/components/UserProfile";
import ManagerScreen from '../src/screens/ManagerScreen';
import GuestScreen from '../src/screens/GuestScreen';
import RolePermissionTable from '../src/components/RolePermissionTable';
import AddBuilding from '../src/components/AddBuilding';


const App = () => {
  return (
    <Router>
      <Switch>
        <Route path="/register">
          <Register />
        </Route>

        <Route path="/adminScreen">
          <AdminScreen />
        </Route>

        <Route path="/guestScreen">
          <GuestScreen />
        </Route>

        <Route path="/managerScreen">
          <ManagerScreen />
        </Route>
        
        <Route path="/addFlat">
          <AddFlat />
        </Route>

        <Route path="/userProfile">
          <UserProfile />
        </Route>

        <Route path="/addBuilding">
          <AddBuilding />
        </Route>

        <Route path="/rolePermissionTable">
          <RolePermissionTable />
        </Route>

        <Route path="/">
          <Login />
        </Route>
        
      </Switch>
    </Router>
  );
};

export default App;