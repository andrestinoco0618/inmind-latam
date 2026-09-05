import { useState } from 'react';
import { sendAnswer, DataSendAnswer, AnswerData } from '../api/QuestionsClientService/questionService';

const useSendAnswer = () => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [newQuestion, setNewQuestion] = useState<AnswerData>(); 

  const submitAnswer = async (answerData: DataSendAnswer) => {
    setLoading(true);
    setError(null);
    try {
      const responseData = await sendAnswer(answerData);
      setNewQuestion(responseData);
    } catch (err) {
      setError('Error sending answer');
    } finally {
      setLoading(false);
    }
  };

  return { submitAnswer, loading, error, newQuestion, setNewQuestion}; 
};

export default useSendAnswer;
