// components/Answer.js

import React from 'react';
import { Text, TouchableOpacity } from 'react-native';

const Answer = ({ answer, onDelete }) => {
  return (
    <TouchableOpacity onPress={onDelete}>
      <Text>{answer.content}</Text>
    </TouchableOpacity>
  );
};

export default Answer;
