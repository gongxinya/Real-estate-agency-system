// components/Question.js

import React from 'react';
import { Text, TouchableOpacity } from 'react-native';

const Question = ({ question, onPress }) => {
  return (
    <TouchableOpacity onPress={onPress}>
      <Text>{question.title}</Text>
      <Text>{question.content}</Text>
    </TouchableOpacity>
  );
};

export default Question;
