import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import QuestionListScreen from './screens/QuestionListScreen';
import QuestionDetailScreen from './screens/QuestionDetailScreen';
import AnswerFormScreen from './screens/AnswerFormScreen';

const Stack = createStackNavigator();

const App = () => {
  return (
    <NavigationContainer>
      <Stack.Navigator>
        <Stack.Screen name="QuestionList" component={QuestionListScreen} />
        <Stack.Screen name="QuestionDetail" component={QuestionDetailScreen} />
        <Stack.Screen name="AnswerForm" component={AnswerFormScreen} />
      </Stack.Navigator>
    </NavigationContainer>
  );
};

export default App;
