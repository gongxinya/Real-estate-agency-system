import { Button, message } from "antd";
import { Link } from 'react-router-dom';
import { HomeOutlined, LogoutOutlined } from "@ant-design/icons";
import axios from 'axios';
import { useHistory } from 'react-router-dom';


const MyHeader = () => {
  const history = useHistory();

  const handleLogout = () => {
    const headers = { "user_key": localStorage.getItem("user_key") };

    axios.get('http://localhost:8080/user/logout', { headers })
      .then((response) => {
        message.success(response.data.message)
        // Clear any user data from local storage or state
        localStorage.clear();
        // Redirect the user to the login page
        history.push('/login');
        console.log(response.data)
      })
      .catch((error) => {
        // Handle any error that occurs during the API request
        console.log(error);
      });
  };

  return (
    <>
      <Link to="/userProfile">
        <Button type="link" icon={<HomeOutlined />} style={{ position: "absolute", top: 10, right: 100 }}>
          My Profile
        </Button>
      </Link>

      <Button type="link" icon={<LogoutOutlined />} onClick={handleLogout} style={{ position: "absolute", top: 10, right: 0 }}>
        Log Out
      </Button>
    </>

  );
};

export default MyHeader;
