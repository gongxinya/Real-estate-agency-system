import { useEffect, useState } from 'react';
import { Table, Button } from 'antd';
import { Link } from 'react-router-dom';
import axios from 'axios';


const App = () => {
  const [data, setData] = useState([]);
  const [buildingOptions, setBuildingOptions] = useState([]);



  useEffect(() => {
    const fetchData = async () => {
    const headers = { "user_key": localStorage.getItem("user_key") };

    axios.get('http://localhost:8080/flat/list'
      , { headers }
    ).then((response) => {
      setData(response.data.data);
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
    },

    {
      title: 'Area',
      dataIndex: 'flatArea',
      sorter: (a, b) => a.flatArea - b.flatArea,
      width: '15%',

    },
    {
      title: 'Price',
      dataIndex: 'flatPrice',
      sorter: (a, b) => a.flatPrice - b.flatPrice,
      width: '15%',
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
    },
    {
      title: 'Sold Date',
      dataIndex: 'flatSoldOutDate',
      sorter: (a, b) => a.flatSoldOutDate - b.flatSoldOutDate,
      width: '15%',
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




