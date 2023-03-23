const axios = require('axios');

// Create a new question
exports.createQuestion = (req, res) => {
  const { title, content } = req.body;
  axios.post('https://your-api-url.com/questions', {
    title: title,
    content: content,
  })
    .then(response => {
      res.status(201).json(response.data);
    })
    .catch(error => {
      console.log(error);
      res.status(500).json({ message: 'Internal server error' });
    });
};

// Get all questions
exports.getAllQuestions = (req, res) => {
  axios.get('https://your-api-url.com/questions')
    .then(response => {
      res.status(200).json(response.data);
    })
    .catch(error => {
      console.log(error);
      res.status(500).json({ message: 'Internal server error' });
    });
};

// Get a specific question by ID
exports.getQuestionById = (req, res) => {
  const questionId = req.params.id;
  axios.get(`https://your-api-url.com/questions/${questionId}`)
    .then(response => {
      res.status(200).json(response.data);
    })
    .catch(error => {
      console.log(error);
      res.status(500).json({ message: 'Internal server error' });
    });
};

// Update a question by ID
exports.updateQuestionById = (req, res) => {
  const questionId = req.params.id;
  const { title, content } = req.body;
  axios.put(`https://your-api-url.com/questions/${questionId}`, {
    title: title,
    content: content,
  })
    .then(response => {
      res.status(200).json(response.data);
    })
    .catch(error => {
      console.log(error);
      res.status(500).json({ message: 'Internal server error' });
    });
};

// Delete a question by ID
exports.deleteQuestionById = (req, res) => {
  const questionId = req.params.id;
  axios.delete(`https://your-api-url.com/questions/${questionId}`)
    .then(response => {
      res.status(204).send();
    })
    .catch(error => {
      console.log(error);
      res.status(500).json({ message: 'Internal server error' });
    });
};

// Create a new answer for a question
exports.createAnswer = (req, res) => {
  const questionId = req.params.id;
  const { content } = req.body;
  axios.post(`https://your-api-url.com/questions/${questionId}/answers`, {
    content: content,
  })
    .then(response => {
      res.status(201).json(response.data);
    })
    .catch(error => {
      console.log(error);
      res.status(500).json({ message: 'Internal server error' });
    });
};

// Update an answer for a question
exports.updateAnswer = (req, res) => {
  const { id, content } = req.body;
  axios.put(`https://your-api-url.com/answers/${id}`, {
    content: content,
  })
    .then(response => {
      res.status(200).json(response.data);
    })
    .catch(error => {
      console.log(error);
      res.status(500).json({ message: 'Internal server error' });
    });
};

// Delete an answer for a question
exports.deleteAnswer = (req, res) => {
    const answerId = req.params.id;
    axios.delete(`https://your-api-url.com/answers/${answerId}`)
      .then(response => {
        res.status(204).send();
      })
      .catch(error => {
        console.log(error);
        res.status(500).json({ message: 'Internal server error' });
      });
  };
  