import { useState, useEffect } from 'react';
import { Form, Input, InputNumber, Select, message, Button, DatePicker } from 'antd';
import { UserOutlined, HomeOutlined, DollarOutlined } from '@ant-design/icons';
import axios from 'axios';

const { Option } = Select;

const AddFlat = () => {
  const [form] = Form.useForm();
  const [buildingOptions, setBuildingOptions] = useState([]);

  useEffect(() => {
    const headers = { "user_key": localStorage.getItem("user_key") };
    axios
      .get('http://localhost:8080/building/list', { headers })
      .then((response) => {
        const buildings = response.data.data.map((option) => ({
          label: option.buildingName,
          value: option.buildingId,
        }));
        setBuildingOptions(buildings);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);




  const onFinish = async (values) => {
    const headers = { "user_key": localStorage.getItem("user_key") };
    try {
      axios.post('http://localhost:8080/flat/create/' + values.buildingId, values, { headers })
        .then((response) => {
          if(response.data.code === 200){
            message.success(response.data.message); // display success message
          } else {
            message.error(response.data.message); // display error message
          }
        });
    } catch (error) {
      console.error(error);
    }
  };



  return (
    <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
      <Form form={form} onFinish={onFinish}>
        <Form.Item
          name="flatName"
          label="Name"
          rules={[{ required: true, message: 'Please enter the flat name' }]}
        >
          <Input prefix={<HomeOutlined />} placeholder="Flat Name" />
        </Form.Item>
        <Form.Item
          name="flatArea"
          label="Area"
          rules={[{ required: true, message: 'Please enter the flat size' }]}
        >
          <InputNumber min={0} step={1} placeholder="Flat Size" />
        </Form.Item>
        <Form.Item
          name="flatPrice"
          label="Price"

        >
          <InputNumber
            min={0}
            step={1}
            prefix={<DollarOutlined />}
            placeholder="Flat Price"
          />
        </Form.Item>
        <Form.Item
          name="buildingId"
          label="Building"
          rules={[{ required: true, message: 'Please select the building' }]}
        >
          <Select placeholder="Select Building">
            {buildingOptions.map((option) => (
              <Option key={option.value} value={option.value}>
                {option.label}
              </Option>
            ))}
          </Select>
        </Form.Item>

        <Form.Item
          name="flatSoldOutDate"
          label="Sale Date"
        >
          <DatePicker
            format="DD-MM-YYYY"
            allowClear={false}
            onChange={(dateString) => {
              form.setFieldsValue({ saleDate: dateString });
            }}
          />
        </Form.Item>

        <Form.Item
          name="userId"
          label="Owner"
        >
          <Input
            prefix={<UserOutlined />}
            placeholder="Owner Name"
          />
        </Form.Item>
        <Form.Item>
          <Button onClick={() => window.history.back()} style={{ marginRight: '30px', marginLeft: '30px' }}>Back</Button>
          <Button type="primary" htmlType="submit" style={{ marginLeft: '30px' }}>Confirm</Button>
        </Form.Item>
      </Form>
    </div>
  );
};

export default AddFlat;
