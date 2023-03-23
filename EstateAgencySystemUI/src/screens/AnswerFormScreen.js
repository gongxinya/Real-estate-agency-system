import React, { useState } from 'react';
import { View, TextInput, Button } from 'react-native';
import axios from 'axios';

const AnswerFormScreen = ({ route, navigation }) => {
  const [answer, setAnswer] = useState('');

  const handleAnswerChange = text => {
    setAnswer(text);
  };

  const handleSubmit = async () => {
    const { questionId } = route.params;
    try {
      const response = await axios.post(`http://your-api-url/answers`, { answer, questionId });
      navigation.goBack();
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <View>
      <TextInput
        multiline={true}
        numberOfLines={4}
        value={answer}
        onChangeText={handleAnswerChange}
        placeholder="Type your answer here"
      />
      <Button title="Submit" onPress={handleSubmit} />
    </View>
  );
};

export default AnswerFormScreen;
