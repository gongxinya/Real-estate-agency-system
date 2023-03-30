import { useEffect, useState } from 'react';
import { Table, Button, Space, Input, Select, message } from 'antd';
import { EditOutlined, DeleteOutlined, CheckOutlined, CloseOutlined } from '@ant-design/icons';
import axios from 'axios';


const { Option } = Select;

const App = () => {
  const [data, setData] = useState([]);
  const [rawData, setRawData] = useState([]);
  const [editableRowId, setEditableRowId] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      const headers = { "user_key": localStorage.getItem("user_key") };
      axios.get('http://localhost:8080/user/list', { headers }).then((response) => {
        setData(response.data.data);
        setRawData(response.data.data);
        console.log(response.data.data);
      });
    };
    fetchData();
  }, []);

  const columns = [
    {
      title: 'ID',
      dataIndex: 'userId',
      sorter: (a, b) => a.userId - b.userId,
      width: '10%',
    },
    {
      title: 'Name',
      dataIndex: 'userName',
      sorter: (a, b) => a.userName.localeCompare(b.userName),
      width: '15%',
      render: (text, record) => {
        const isEditable = record.userId === editableRowId;
        return (
          <div
            onClick={() => {
              if (!isEditable) {
                setEditableRowId(record.userId);
              }
            }}
          >
            {isEditable ? (
              <Input
                value={text}
                onChange={(e) =>
                  handleInputChange({ ...record, userName: e.target.value })
                }
              />
            ) : (
              text
            )}
          </div>
        );
      },
    },
    {
      title: 'Phone',
      dataIndex: 'userPhone',
      sorter: (a, b) => a.userPhone - b.userPhone,
      width: '15%',
      render: (text, record) => {
        const isEditable = record.flatId === editableRowId;
        return (
          <div
            onClick={() => {
              if (!isEditable) {
                setEditableRowId(record.flatId);
              }
            }}
          >
            {isEditable ? (
              <Input
                value={text}
                onChange={(e) => {
                  const value = e.target.value.trim();
                  if (!isNaN(value)) {
                    handleInputChange({ ...record, userPhone: value });
                  }
                }}
              />
            ) : (
              text
            )}
          </div>
        );
      },
    },
    {
      title: 'Email',
      dataIndex: 'userEmail',
      sorter: (a, b) => a.userEmail.localeCompare(b.userEmail),
      width: '15%',
      render: (text, record) => {
        const isEditable = record.userId === editableRowId;
        return (
          <div
            onClick={() => {
              if (!isEditable) {
                setEditableRowId(record.userId);
              }
            }}
          >
            {isEditable ? (
              <Input
                value={text}
                onChange={(e) =>
                  handleInputChange({ ...record, userEmail: e.target.value })
                }
              />
            ) : (
              text
            )}
          </div>
        );
      },
    },
    {
      title: 'Adress',
      dataIndex: 'userAddress',
      sorter: (a, b) => {
        if (a.userAddress && b.userAddress) {
          return a.userAddress.localeCompare(b.userAddress);
        } else {
          return 0;
        }
      },
      width: '15%',
      render: (text, record) => {
        const isEditable = record.userId === editableRowId;
        return (
          <div
            onClick={() => {
              if (!isEditable) {
                setEditableRowId(record.userId);
              }
            }}
          >
            {isEditable ? (
              <Input
                value={text}
                onChange={(e) =>
                  handleInputChange({ ...record, userAddress: e.target.value })
                }
              />
            ) : (
              text
            )}
          </div>
        );
      },
    },
    {
      title: 'Password',
      dataIndex: 'userPassword',
      width: '15%',
      render: (text, record) => {
        const isEditable = record.userId === editableRowId;
        return (
          <div
            onClick={() => {
              if (!isEditable) {
                setEditableRowId(record.userId);
              }
            }}
          >
            {isEditable ? (
              <Input
                value={text}
                onChange={(e) =>
                  handleInputChange({ ...record, userPassword: e.target.value })
                }
              />
            ) : (
              text
            )}
          </div>
        );
      },
    },
    {
      title: 'Role',
      dataIndex: 'serialVersionUID',
      width: '15%',
    },
    {
      title: "Actions",
      key: "actions",
      width: "10%",
      render: (text, record) => {
        const isEditing = record.userId === editableRowId;
        return (
          <Space size="middle">
            {isEditing ? (
              <>
                <Button type="primary" onClick={() => { handleUpdateSubmit(record); setEditableRowId(null) }}>
                  <CheckOutlined />
                </Button>
                <Button onClick={() => setEditableRowId(null)}>
                  <CloseOutlined />
                </Button>
              </>
            ) : (
              <>
                <Button type="primary" onClick={() => setEditableRowId(record.userId)}>
                  <EditOutlined />
                </Button>
                <Button type="danger" onClick={() => handleDelete(record.userId)}>
                  <DeleteOutlined />
                </Button>
              </>
            )}
          </Space>
        );
      },
    },
  ];

  const handleInputChange = (updatedRecord) => {
    console.log(updatedRecord)
    const updatedData = data.map((record) => {
        if (record.userId === updatedRecord.userId) {
            return { ...record, ...updatedRecord };
        } else {
            return record;
        }
    });
    setData(updatedData);
    // console.log(updatedData)
};

const handleUpdateSubmit = (updatedRecord) => {



    // const rawRowData = rawData.find(item => item.userId === updatedRecord.userId)


    // Object.keys(rawRowData).forEach((field) => {
    //     if (rawRowData[field] === updatedRecord[field] && field !== 'userId') {
    //       updatedRecord[field] = null;
    //     }
    // });

    // console.log(updatedRecord)


    const headers = { "user_key": localStorage.getItem("user_key") };

    // // Send another HTTP request to get the data
    // axios.get('http://localhost:8080/user/list', { headers }).then((response) => {
    //     setData(response.data.data);
    // });

    // Send an HTTP request to update the data
    axios.put('http://localhost:8080/user/update/' + updatedRecord.userId, updatedRecord
        , { headers })
        .then((response) => {
          if(response.data.code === 200){
            message.success(response.data.message); // display success message
          } else {
            message.error(response.data.message); // display error message
          }
            console.log(response.data.message)
            axios.get('http://localhost:8080/user/list', { headers }).then((response) => {
                setData(response.data.data);
                // setRawData(response.data.data);
            });
        });

    setEditableRowId(null);
};

const handleDelete = (userId) => {
    const headers = {
        "user_key": localStorage.getItem("user_key")
    };
        axios.delete('http://localhost:8080/user/delete/' + userId
        , { headers })
        .then((response) => {
          if(response.data.code === 200){
            message.success(response.data.message); // display success message
            setData(data.filter((record) => record.userId !== userId));
          } else {
            message.error(response.data.message); // display error message
          }
            console.log(response.data)
        });
};

  


  const onChange = (pagination, filters, sorter, extra) => {
    console.log('params', pagination, filters, sorter, extra);
  };

 
  return (
    <>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <h2 style={{ margin: 20 }}>User List</h2>
      </div>
      <Table columns={columns} dataSource={data} onChange={onChange} />
    </>
  );
};



export default App;
