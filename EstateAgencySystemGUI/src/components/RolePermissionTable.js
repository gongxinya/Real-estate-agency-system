import React, { useState } from 'react';
import { Table, Tag, Button, Modal, Form, Input, Select, message } from 'antd';
import { PlusOutlined, CloseOutlined } from '@ant-design/icons';

const { Option } = Select;

const testData = [
  {
    role: 'Admin',
    permissions: ['Create', 'Read', 'Update', 'Delete'],
  },
  {
    role: 'Manager',
    permissions: ['Read', 'Update'],
  },
  {
    role: 'Guest',
    permissions: ['Read'],
  },
];

const App = () => {
  const [data, setData] = useState(testData);

  const permissions = ['Create', 'Read', 'Update', 'Delete'];

  const handleAddPermission = (record, selectedPermission) => {
    if (selectedPermission) {
      const permissions = record.permissions;
      if (permissions.indexOf(selectedPermission) === -1) {
        const newPermissions = [...permissions, selectedPermission];
        const newData = data.map(item => {
          if (item.role === record.role) {
            return { ...item, permissions: newPermissions };
          }
          return item;
        });
        setData(newData);
      } else {
        message.error(`Role "${record.role}" already has permission "${selectedPermission}"`);
      }
    }
  };
  

  const handleRemovePermission = (record, permission) => {
    const newPermissions = record.permissions.filter(p => p !== permission);
    const newData = data.map(item => {
      if (item.role === record.role) {
        return { ...item, permissions: newPermissions };
      }
      return item;
    });
    setData(newData);
  };

  const AddPermissionButton = ({ record }) => {
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [selectedPermission, setSelectedPermission] = useState(null);

    const handleOk = () => {
      handleAddPermission(record, selectedPermission);
      setIsModalVisible(false);
    };



    return (

      <>
        <Button type="dashed" onClick={() => setIsModalVisible(true)} icon={<PlusOutlined />} />
        <Modal
          title={`Add permission to role "${record.role}"`}
          visible={isModalVisible}
          onCancel={() => setIsModalVisible(false)}
          onOk={handleOk}
        >
          <Form layout="vertical">
            <Form.Item label="Permission">
              <Select value={selectedPermission} onChange={setSelectedPermission}>
                {permissions.map(permission => (
                  <Option key={permission} value={permission}>
                    {permission}
                  </Option>
                ))}
              </Select>
            </Form.Item>
          </Form>
        </Modal>
      </>
    );
  };

  const columns = [
    {
      title: 'Role',
      dataIndex: 'role',
    },
    {
      title: 'Permissions',
      dataIndex: 'permissions',
      render: (permissions, record) => (
        <>
          {permissions.map(permission => (
            <Tag
              key={`${record.role}-${permission}`}
              closable
              onClose={() => handleRemovePermission(record, permission)}
            >
              {permission}
            </Tag>
          ))}
          <AddPermissionButton record={record} />
        </>
      ),
    },
  ];


  const handleUpdateData = () => {
    // Do something to upload the data of this page
    console.log(data);
  };



  return (
    <>
      <Button
        type="primary"
        style={{ float: "right", margin: "16px" }}
        onClick={handleUpdateData}
      >
        Update
      </Button>
      <Table columns={columns} dataSource={data} />
    </>
  );

};

export default App;
