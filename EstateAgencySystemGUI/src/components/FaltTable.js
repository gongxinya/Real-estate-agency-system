import { useEffect, useState } from 'react';
import { Table, Button, Space, Input, Select, DatePicker, message } from 'antd';
import { EditOutlined, DeleteOutlined, CheckOutlined, CloseOutlined } from '@ant-design/icons';
import { Link } from 'react-router-dom';
import axios from 'axios';
import moment from 'moment';


const App = () => {
  const [data, setData] = useState([]);
  const [rawData, setRawData] = useState([]);
  const [buildingOptions, setBuildingOptions] = useState([]);
  const [editableRowId, setEditableRowId] = useState(null);


  useEffect(() => {
    const fetchData = async () => {
    const headers = { "user_key": localStorage.getItem("user_key") };

    axios.get('http://localhost:8080/flat/list'
      , { headers }
    ).then((response) => {
      setData(response.data.data);
      setRawData(response.data.data);
      console.log(response.data.data)
    });

    axios.get('http://localhost:8080/building/list'
      , { headers }
    ).then((response) => {
      setBuildingOptions(response.data.data.map((option) => ({
        value: option.buildingId,
        label: option.buildingName,
      })));


    });
  };
  fetchData();

  }, []);


  const columns = [
    {
      title: 'ID',
      dataIndex: 'flatId',
      sorter: (a, b) => a.flatId - b.flatId,
      width: '10%',
    },
    {
      title: 'Name',
      dataIndex: 'flatName',
      sorter: (a, b) => a.flatName - b.flatName,
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
                onChange={(e) =>
                  handleInputChange({ ...record, flatName: e.target.value })
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
      title: 'Area',
      dataIndex: 'flatArea',
      sorter: (a, b) => a.flatArea - b.flatArea,
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
                    handleInputChange({ ...record, flatArea: value });
                  }
                }
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
      title: 'Price',
      dataIndex: 'flatPrice',
      sorter: (a, b) => a.flatPrice - b.flatPrice,
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
                    handleInputChange({ ...record, flatPrice: value });
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
      title: 'Building',
      dataIndex: 'buildingId',
      filters: buildingOptions.map((option) => ({
        text: option.label,
        value: option.value,
      })),
      filterMode: 'multiple',
      filterSearch: true,
      onFilter: (value, record) => record.buildingId === value,
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
              <Select
                defaultValue={text}
                options={buildingOptions}
                onChange={(value) =>
                  handleInputChange({ ...record, buildingId: value })
                }
              />
            ) : (
              buildingOptions.find((option) => option.value === text)?.label
            )}
          </div>
        );
      },
    },
    {
      title: 'Sold Date',
      dataIndex: 'flatSoldOutDate',
      sorter: (a, b) => a.flatSoldOutDate - b.flatSoldOutDate,
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
              <DatePicker
                defaultValue={moment(text, 'DD-MM-YYYY')}
                format={'DD-MM-YYYY'}
                onChange={(date, dateString) =>
                  handleInputChange({ ...record, flatSoldOutDate: dateString })
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
      title: 'Owner',
      dataIndex: 'userId',
      sorter: (a, b) => a.userId - b.userId,
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
                onChange={(e) =>
                  handleInputChange({ ...record, userId: e.target.value })
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
      title: "Actions",
      key: "actions",
      width: "10%",
      render: (text, record) => {
        const isEditing = record.flatId === editableRowId;
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
                <Button type="primary" onClick={() => setEditableRowId(record.flatId)}>
                  <EditOutlined />
                </Button>
                <Button type="danger" onClick={() => handleDelete(record.flatId)}>
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
    const updatedData = data.map((record) => {
      if (record.flatId === updatedRecord.flatId) {
        return { ...record, ...updatedRecord };
      } else {
        return record;
      }
    });
    setData(updatedData);
  };

  const handleUpdateSubmit = (updatedRecord) => {
    const rawRowData = rawData.find(item => item.flatId === updatedRecord.flatId)
    console.log('raw data:')
    console.log(rawRowData)
    console.log('new data:')
    console.log(updatedRecord)
    


  //   Object.keys(rawRowData).forEach((field) => {
  //     if (rawRowData[field] === updatedRecord[field] && field !== 'flatId') {
  //       updatedRecord[field] = null;
  //     }
  // });
  console.log('after data:')
  console.log(updatedRecord)
  
  const headers = { "user_key": localStorage.getItem("user_key") };

  // Send another HTTP request to get the data
  axios.get('http://localhost:8080/flat/list', { headers }).then((response) => {
    setData(response.data.data);
  });






    // Send an HTTP request to update the data
    axios.put('http://localhost:8080/flat/update/' + updatedRecord.flatId, updatedRecord
      , { headers })
      .then((response) => {
        if(response.data.code === 200){
          message.success(response.data.message); // display success message
        } else {
          message.error(response.data.message); // display error message
        }
        console.log(response.data.message)
  axios.get('http://localhost:8080/flat/list', { headers }).then((response) => {
    setData(response.data.data);
    setRawData(response.data.data);
  });
      });

    setEditableRowId(null);
  };

  const handleDelete = (flatId) => {
    const headers = {
      "user_key": localStorage.getItem("user_key")
    };
      axios.delete('http://localhost:8080/flat/delete/' + flatId
      , { headers })
      .then((response) => {
        if(response.data.code === 200){
          setData(data.filter((record) => record.flatId !== flatId));
          message.success(response.data.message); // display success message
        } else {
          message.error(response.data.message); // display error message
        }
        
      });
  };


  return (
    <>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <h2 style={{ margin: 20 }}>Flat List</h2>
        <Link to="/addFlat">
          <Button type="primary" style={{ right: 20 }}>Add Flat</Button>
        </Link>
      </div>
      <Table columns={columns} dataSource={data} />;

    </>
  )
};

export default App;




