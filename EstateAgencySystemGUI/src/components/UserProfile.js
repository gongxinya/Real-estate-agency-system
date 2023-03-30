import React, { useEffect, useState } from 'react';
import { Form, Input, Button, message } from 'antd';
import axios from 'axios';

const UserProfile = () => {
    const [form] = Form.useForm();
    const [showPassword, setShowPassword] = useState(false);
    const [userData, setUserData] = useState({});

    useEffect(() => {
        const headers = { "user_key": localStorage.getItem("user_key") };
        axios.get('http://localhost:8080/user/view', { headers })
            .then((response) => {
                setUserData(response.data.data)
                form.setFieldsValue({
                    userId: response.data.data.userId,
                    userName: response.data.data.userName,
                    userEmail: response.data.data.userEmail,
                    userPhone: response.data.data.userPhone,
                    userAddress: response.data.data.userAddress,
                    userPassword: response.data.data.userPassword,
                });
            });
    }, []);

    const handleFormSubmit = (values) => {
        const headers = { "user_key": localStorage.getItem("user_key") };
        const formFields = form.getFieldsValue(true);
        const updatedFields = values;
        Object.keys(formFields).forEach((field) => {
            if (formFields[field] === userData[field] && field !== 'userId' && field !== 'userEmail') {
                updatedFields[field] = null;
            }
        });
  
        axios.put('http://localhost:8080/user/update', updatedFields, { headers })
            .then((response) => {
                if (response.data.code === 200) {
                    message.success(response.data.message); // display success message
                } else {
                    message.error(response.data.message); // display error message
                }
            });
    };
    return (
        <div style={{ display: 'flex', flexDirection: 'column', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
            <h1 style={{ textAlign: 'center', marginBottom: '30px' }}>Profile</h1>
            <Form
                form={form}
                layout="vertical"
                onFinish={handleFormSubmit}
            >
                <Form.Item
                    label="ID"
                    name="userId"
                >
                    <Input disabled />
                </Form.Item>
                <Form.Item
                    label="Name"
                    name="userName"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your name!',
                            value: 'name'
                        },
                    ]}
                >
                    <Input />
                </Form.Item>
                <Form.Item
                    label="Email"
                    name='userEmail'
                    initialValue={userData.userEmail}
                    rules={[
                        {
                            required: true,
                            message: 'Please input your email!',
                        },
                        {
                            type: 'email',
                            message: 'Please enter a valid email!',
                        },
                    ]}
                >
                    <Input />
                </Form.Item>
                <Form.Item
                    label="Phone"
                    name="userPhone"
                    rules={[
                        {
                            pattern: /^(\+)?([0-9]){10,14}$/,
                            message: 'Please enter a valid phone!',
                        },
                    ]}
                >
                    <Input />
                </Form.Item>
                <Form.Item
                    label="Address"
                    name="userAddress"
                >
                    <Input />
                </Form.Item>
                <Form.Item
                    label="Password"
                    name="userPassword"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your password!',
                        },
                    ]}
                >
                    {showPassword ? (
                        <Input.Password name="password" defaultValue={userData.password} />
                    ) : (
                        <Input.Password name="password" value="********" />
                    )}
                </Form.Item>
                <Form.Item>
                    <Button onClick={() => window.history.back()} style={{ marginRight: '30px' }}>Back</Button>
                    <Button type="primary" htmlType="submit">Update</Button>
                </Form.Item>
            </Form>
        </div>
    );
};

export default UserProfile;
