// App.js

import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import QuestionsScreen from './screens/QuestionsScreen';
import QuestionDetailScreen from './screens/QuestionDetailScreen';

const Stack = createStackNavigator();

const App = () => {
  return (
    <NavigationContainer>
      <Stack.Navigator>
        <Stack.Screen name="Questions" component={QuestionsScreen} />
        <Stack.Screen name="QuestionDetail" component={QuestionDetailScreen} />
      </Stack.Navigator>
    </NavigationContainer>
  );
};

export default App;
