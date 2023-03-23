// screens/QuestionsScreen.js

import React, { useState, useEffect } from 'react';
import { FlatList } from 'react-native';
import axios from 'axios';
import Question from '../components/Question';
import QuestionForm from '../components/QuestionForm';

const QuestionsScreen = () => {
  const [questions, setQuestions] = useState([]);

  useEffect(() => {
    axios.get('https://your-api-url.com/questions')
      .then(response => {
        setQuestions(response.data);
      })
      .catch(error => {
        console.log(error);
      });
  }, []);

  const handleQuestionPress = (question) => {
    // Navigate to the question detail screen
  };

  const handleQuestionSubmit = (question) => {
    axios.post('https://your-api-url.com/questions', question)
      .then(response => {
        setQuestions([...questions, response.data]);
      })
      .catch(error => {
        console.log(error);
      });
  };

  return (
    <>
      <FlatList
        data={questions}
        renderItem={({ item }) => <Question question={item} onPress={() => handleQuestionPress(item)} />}
        keyExtractor={item => item.id}
      />
      <QuestionForm onSubmit={handleQuestionSubmit} />
    </>
  );
};

export default QuestionsScreen;
