import React, { useState, useEffect } from 'react';
import { Form, Input, Button, Select } from 'antd';
import axios from 'axios';

const { Option } = Select;

const UserForm = ({ userId }) => {
    const [form] = Form.useForm();
    const [user, setUser] = useState({});
    const [roles, setRoles] = useState([]);

    useEffect(() => {
        // Fetch user data from the server
        axios.get(`http://localhost:8080/user/${userId}`).then((response) => {
            console.log(response.data);
            setUser(response.data);
            form.setFieldsValue(response.data);
        });

        // Fetch available roles from the server
        axios.get(`http://localhost:8080/roles`).then((response) => {
            console.log(response.data);
            setRoles(response.data);
        });
    }, [form, userId]);

    const handleRoleChange = (value) => {
        const selectedRole = roles.find((role) => role.name === value);
        form.setFieldsValue({ permissions: selectedRole.permissions });
    };

    const handleUpdate = (values) => {
        console.log(values);
        // Send updated user data to the server
        axios.put(`http://localhost:8080/user/${userId}`, values).then((response) => {
            console.log(response.data);
            setUser(response.data);
            form.setFieldsValue(response.data);
        });
    };

    return (
        <div style={{ display: 'flex', flexDirection: 'column', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
            <h1 style={{ textAlign: 'center', marginBottom: '30px' }}>User Detail</h1>
            <Form form={form} onFinish={handleUpdate} initialValues={user}>
                <Form.Item name="id" label="ID">
                    <Input disabled />
                </Form.Item>
                <Form.Item name="name" label="Name">
                    <Input />
                </Form.Item>
                <Form.Item name="phone" label="Phone">
                    <Input />
                </Form.Item>
                <Form.Item name="email" label="Email">
                    <Input />
                </Form.Item>
                <Form.Item name="address" label="Address">
                    <Input />
                </Form.Item>
                <Form.Item name="password" label="Password">
                    <Input.Password />
                </Form.Item>
                <Form.Item name="role" label="Role">
                    <Select disabled onChange={handleRoleChange}>
                        {roles.map((role) => (
                            <Option key={role.id} value={role.name}>
                                {role.name}
                            </Option>
                        ))}
                    </Select>
                </Form.Item>
                <Form.Item name="permissions" label="Permissions">
                    <Select mode="multiple" disabled>
                        {user.role && roles.find((role) => role.name === user.role).permissions.map((permission) => (
                            <Option key={permission} value={permission}>
                                {permission}
                            </Option>
                        ))}
                    </Select>
                </Form.Item>
                <Form.Item>
                    <Button onClick={() => window.history.back()} style={{ marginLeft: '30px', marginRight: '60px' }}>Back</Button>
                    <Button type="primary" htmlType="submit">
                        Update
                    </Button>
                </Form.Item>
            </Form>
        </div>

    );

};

export default UserForm;
