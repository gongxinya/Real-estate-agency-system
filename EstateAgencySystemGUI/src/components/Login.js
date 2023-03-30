import { LockOutlined, UserOutlined } from '@ant-design/icons';
import { Button, Form, Input, Alert, message } from 'antd';
import { Link, useHistory } from 'react-router-dom';
import axios from 'axios';
import { useState } from 'react';

const App = () => {
  const history = useHistory(); // Initialize useHistory
  const [error, setError] = useState(null);
  const onFinish = (values) => {
    axios.post('http://localhost:8080/user/login',
      {
        userEmail: values.email,
        userPassword: values.password,
      })
      .then((response) => {
        var responseData = response.data;
        var loginMessage = responseData.message;
        var code = responseData.code;
        var data = responseData.data;
        // conditional judgment is carried out through the user's authority, and different pages are entered according to the judgment result
        if (code !== 200) {
          setError(response.data.message)
          message.error(response.data.message)
        } else {
          localStorage.setItem("user_key", data.user_key);
          const headers = { "user_key": localStorage.getItem("user_key") };
          axios.get('http://localhost:8080/user/list'
            , { headers }
          ).then((response) => {
            if (response.data.code === 200) {
              message.success(loginMessage)
              history.push('/adminScreen');
            } else {
              axios.post('http://localhost:8080/building/create',
                {
                  buildingName: null,
                  buildingAddress: null,
                }
                , { headers }
              ).then((response) => {
                message.success(loginMessage)
                response.data.code === 500 ? history.push('/managerScreen') : history.push('/guestScreen')
              })
            }
          });
        }
      });

  };

  return (
    <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
      <Form
        name="normal_login"
        className="login-form"
        initialValues={{
          remember: true,
        }}
        onFinish={onFinish}
      >
        {error && <Alert message={error} type="error" />}
        <Form.Item
          name="email"
          rules={[
            {
              required: true,
              message: 'Please input your email!',
            },
          ]}
        >
          <Input prefix={<UserOutlined className="site-form-item-icon" />}
            placeholder="Email"
          />
        </Form.Item>
        <Form.Item
          name="password"
          rules={[
            {
              required: true,
              message: 'Please input your Password!',
            },
          ]}
        >
          <Input
            prefix={<LockOutlined className="site-form-item-icon" />}
            type="password"
            placeholder="Password"
          />
        </Form.Item>
        <Form.Item>

          <Link to="/register">
            <Button type="primary" className="login-form-button" style={{ marginRight: '30px', marginLeft: '10px' }}>
              Register
            </Button>
          </Link>

          <Button type="primary" htmlType="submit" className="login-form-button"
          >
            Log in
          </Button>

        </Form.Item>
      </Form>
    </div>
  );
};
export default App;