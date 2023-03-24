// components/QuestionForm.js

import React, { useState } from 'react';
import { Text, TextInput, Button } from 'react-native';

const QuestionForm = ({ onSubmit }) => {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');

  const handleSubmit = () => {
    onSubmit({ title, content });
    setTitle('');
    setContent('');
  };

  return (
    <>
      <Text>Title:</Text>
      <TextInput value={title} onChangeText={setTitle} />
      <Text>Content:</Text>
      <TextInput value={content} onChangeText={setContent} />
      <Button title="Submit" onPress={handleSubmit} />
    </>
  );
};

export default QuestionForm;
