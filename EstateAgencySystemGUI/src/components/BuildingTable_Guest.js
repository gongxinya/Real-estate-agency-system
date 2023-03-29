import { useEffect, useState } from 'react';
import { Table } from 'antd';
import axios from 'axios';


const App = () => {
    const [data, setData] = useState([]);

    useEffect(() => {

        const fetchData = async () => {
            const headers = { "user_key": localStorage.getItem("user_key") };
            axios.get('http://localhost:8080/building/list'
                , { headers }
            ).then((response) => {
                console.log(response.data.data)
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
        },
        {
            title: 'Adress',
            dataIndex: 'buildingAddress',
            sorter: (a, b) => a.buildingAddress.localeCompare(b.buildingAddress),
            width: '10%',
        },
    ];


    return (
        <>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <h2 style={{ margin: 20 }}>Building List</h2>
            </div>
            <Table columns={columns} dataSource={data} />;

        </>
    )





}
export default App;
