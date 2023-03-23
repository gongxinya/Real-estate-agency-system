// screens/QuestionDetailScreen.js

import React, { useState, useEffect } from 'react';
import { FlatList } from 'react-native';
import axios from 'axios';
import Question from '../components/Question';
import Answer from '../components/Answer';
import AnswerForm from '../components/AnswerForm';

const QuestionDetailScreen = ({ route }) => {
  const { questionId } = route.params;
  const [question, setQuestion] = useState(null);
  const [answers, setAnswers] = useState([]);

  useEffect(() => {
    axios.get(`https://your-api-url.com/questions/${questionId}`)
      .then(response => {
        setQuestion(response.data);
      })
      .catch(error => {
        console.log(error);
      });

    axios.get(`https://your-api-url.com/questions/${questionId}/answers`)
      .then(response => {
        setAnswers(response.data);
      })
      .catch(error => {
        console.log(error);
      });
  }, []);

  const handleAnswerSubmit = (answer) => {
    axios.post(`https://your-api-url.com/questions/${questionId}/answers`, answer)
      .then(response => {
        setAnswers([...answers, response.data]);
      })
      .catch(error => {
        console.log(error);
      });
  };

  const handleAnswerDelete = (answerId) => {
    axios.delete(`https://your-api-url.com/answers/${answerId}`)
      .then(response => {
        setAnswers(answers.filter(answer => answer.id !== answerId));
      })
      .catch(error => {
        console.log(error);
      });
  };

  if (!question) {
    return null;
  }

  return (
    <>
      <Question question={question} />
      <FlatList
        data={answers}
        renderItem={({ item }) => <Answer answer={item} onDelete={() => handleAnswerDelete(item.id)} />}
        keyExtractor={item => item.id}
      />
      <AnswerForm onSubmit={handleAnswerSubmit} />
    </>
  );
};

export default QuestionDetailScreen;
