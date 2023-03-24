// components/AnswerForm.js

import React, { useState } from 'react';
import { Text, TextInput, Button } from 'react-native';

const AnswerForm = ({ onSubmit }) => {
  const [content, setContent] = useState('');

  const handleSubmit = () => {
    onSubmit({ content });
    setContent('');
  };

  return (
    <>
      <Text>Content:</Text>
      <TextInput value={content} onChangeText={setContent} />
      <Button title="Submit" onPress={handleSubmit} />
    </>
  );
};

export default AnswerForm;
