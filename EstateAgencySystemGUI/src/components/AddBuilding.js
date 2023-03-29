import { Form, Input, Select, Button, message } from 'antd';
import { HomeOutlined, EnvironmentOutlined } from '@ant-design/icons';
import axios from 'axios';

const { Option } = Select;

const AddBuilding = () => {
  const [form] = Form.useForm();


  const onFinish = async (values) => {
    const headers = { "user_key": localStorage.getItem("user_key") };
    try {
      await axios.post('http://localhost:8080/building/create', values, { headers })
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
          name="buildingName"
          label="Name"
          rules={[{ required: true, message: 'Please enter the building name' }]}
        >
          <Input prefix={<HomeOutlined />} placeholder="building Name" />
        </Form.Item>

        <Form.Item
          name="buildingAddress"
          label="Address"
          rules={[{ required: true, message: 'Please enter the building name' }]}
        >
          <Input prefix={<EnvironmentOutlined />} placeholder="building Name" />
        </Form.Item>

        <Form.Item>
          <Button onClick={() => window.history.back()} style={{ marginRight: '30px', marginLeft: '30px' }}>Back</Button>
          <Button type="primary" htmlType="submit" style={{ marginLeft: '30px' }}>Confirm</Button>
        </Form.Item>
      </Form>
    </div>
  );
};

export default AddBuilding;
