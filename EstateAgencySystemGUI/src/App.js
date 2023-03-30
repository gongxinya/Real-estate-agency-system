import { BrowserRouter as Router } from 'react-router-dom';
import React from "react";
import Routes from "../src/Routes"
import { useState, useEffect } from 'react';
import axios from 'axios';


const App = () => {
  const [isConnected, setIsConnected] = useState(true);

  useEffect(() => {
    const checkServerConnection = async () => {
      try {
        await axios.get('http://localhost:8080/health'); // check network connection status
        setIsConnected(true);
      } catch (error) {
        setIsConnected(false);
      }
    };

    checkServerConnection();
    const intervalId = setInterval(checkServerConnection, 5000); // repeatedly call the checkServerConnection() function every 5000 milliseconds
    return () => clearInterval(intervalId);
  }, []);

  return (
    <div>
      {!isConnected && (
        <p>The internet has been lost.</p>  // when the network connection is disconnected, prompt above the screen
      )}
      {/* allows the application to render different components based on the current URL path */}
      {<Router> 
        <div className="App">
          <Routes />
        </div>
      </Router>}
    </div>
  );
};

export default App;
