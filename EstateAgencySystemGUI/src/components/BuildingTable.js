import { useEffect, useState } from 'react';
import { Table, Button, Space, Input, message } from 'antd';
import { EditOutlined, DeleteOutlined, CheckOutlined, CloseOutlined } from '@ant-design/icons';
import { Link } from 'react-router-dom';
import axios from 'axios';

const BuildingTable = () => {
    const [data, setData] = useState([]);
    const [editableRowId, setEditableRowId] = useState(null);

    useEffect(() => {

        const fetchData = async () => {
            const headers = { "user_key": localStorage.getItem("user_key") };
            axios.get('http://localhost:8080/building/list'
                , { headers }
            ).then((response) => {
                setData(response.data.data);
            });
        };
        fetchData();
    }, []);

    const columns = [
        {
            title: 'ID',
            dataIndex: 'buildingId',
            sorter: (a, b) => a.buildingId - b.buildingId,
            width: '10%',
        },
        {
            title: 'Name',
            dataIndex: 'buildingName',
            sorter: (a, b) => a.buildingName.localeCompare(b.buildingName),
            width: '10%',
            render: (text, record) => {
                const isEditable = record.buildingId === editableRowId;
                return (
                    <div
                        onClick={() => { // after clicking, the row becomes editable
                            if (!isEditable) {
                                setEditableRowId(record.buildingId);
                            }
                        }}
                    >
                        {isEditable ? (
                            <Input
                                value={text}
                                onChange={(e) =>
                                    handleInputChange({ ...record, buildingName: e.target.value })
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
            dataIndex: 'buildingAddress',
            sorter: (a, b) => a.buildingAddress.localeCompare(b.buildingAddress),
            width: '10%',
            render: (text, record) => {
                const isEditable = record.buildingId === editableRowId;
                return (
                    <div
                        onClick={() => {
                            if (!isEditable) {
                                setEditableRowId(record.buildingId);
                            }
                        }}
                    >
                        {isEditable ? (
                            <Input
                                value={text}
                                onChange={(e) =>
                                    handleInputChange({ ...record, buildingAddress: e.target.value })
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
                const isEditing = record.buildingId === editableRowId;
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
                                <Button type="primary" onClick={() => setEditableRowId(record.buildingId)}>
                                    <EditOutlined />
                                </Button>
                                <Button type="danger" onClick={() => handleDelete(record.buildingId)}>
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
            if (record.buildingId === updatedRecord.buildingId) {
                return { ...record, ...updatedRecord };
            } else {
                return record;
            }
        });
        setData(updatedData);
    };

    const handleUpdateSubmit = (updatedRecord) => {


        const headers = { "user_key": localStorage.getItem("user_key") };

        // Send another HTTP request to get the data
        axios.get('http://localhost:8080/building/list', { headers }).then((response) => {
            setData(response.data.data);
        });

        // Send an HTTP request to update the data
        axios.put('http://localhost:8080/building/update/' + updatedRecord.buildingId, updatedRecord
            , { headers })
            .then((response) => {
                if (response.data.code === 200) {
                    message.success(response.data.message); // display success message
                } else {
                    message.error(response.data.message); // display error message
                }
                axios.get('http://localhost:8080/building/list', { headers }).then((response) => { //Get the latest modified data list from the server
                    setData(response.data.data);
                });
            });

        setEditableRowId(null); // After the update is complete, all rows will enter an unmodifiable state
    };

    const handleDelete = (buildingId) => {
        const headers = {
            "user_key": localStorage.getItem("user_key")
        };
        axios.delete('http://localhost:8080/building/delete/' + buildingId
            , { headers })
            .then((response) => {
                if (response.data.code === 200) {
                    message.success(response.data.message); // display success message
                    setData(data.filter((record) => record.buildingId !== buildingId));
                } else {
                    message.error(response.data.message); // display error message
                }

            });
    };



    return (
        <>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <h2 style={{ margin: 20 }}>Building List</h2>
                <Link to="/addBuilding">
                    <Button type="primary" style={{ right: 20 }}>Add Building</Button>
                </Link>
            </div>
            <Table columns={columns} dataSource={data} />;

        </>
    )

}
export default BuildingTable;
