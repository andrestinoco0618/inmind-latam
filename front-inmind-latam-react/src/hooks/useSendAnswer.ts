import { useState } from 'react';
import { sendAnswer, DataSendAnswer, AnswerData } from '../api/questionService';

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
      console.error("Error al enviar la respuesta:", err);
      setError('Error sending answer');
    } finally {
      setLoading(false);
    }
  };

  return { submitAnswer, loading, error, newQuestion }; 
};

export default useSendAnswer;
