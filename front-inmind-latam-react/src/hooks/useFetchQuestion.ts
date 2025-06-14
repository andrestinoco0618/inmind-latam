import { useState, useEffect } from 'react';
import { fetchInitialQuestions, GetQuestions } from '../api/questionService';

const useFetchQuestions = () => {
  const [questions, setQuestions] = useState<GetQuestions>();  
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const loadQuestions = async () => {
      setLoading(true);
      setError(null);
      try {
        const initialQuestions = await fetchInitialQuestions();
        setQuestions(initialQuestions);  
      } catch (err: any) {
        setError('Failed to load questions.');
        console.error('Error loading questions:', err);
      } finally {
        setLoading(false);
      }
    };
    loadQuestions();
  }, []);  

  return { questions, loading, error };  
};

export default useFetchQuestions;
