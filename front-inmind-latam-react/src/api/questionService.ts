import apiClient from './apiClient';

export interface GetQuestions {
  positionQuestion: number;
  idQuestionnaire: string;
  idQuestion: string;
  idQuestionType: string;
  title: string;
  subtitle: string;
  optionsAnswer: [];
}

export interface AnswerData {
  positionQuestion: number;
  idQuestionnaire: string;
  idQuestion: string;
  idQuestionType: string;
  title: string;
  subtitle: string;
  optionsAnswer: [];
}

export interface DataSendAnswer {
  idQuestionnaire: string;
  idQuestion: string;
  responseAnswer: string[];
  openQuestion: string;
}

export const fetchInitialQuestions = async (): Promise<GetQuestions> => {
  try {
    const response = await apiClient.get<GetQuestions>(`/start`, {
      params: {
        profileType: "PF001",
      },
    });
    return response.data;
  } catch (error) {
    console.error('Error fetching initial questions:', error);
    throw error;
  }
};

export const sendAnswer = async (data: DataSendAnswer): Promise<AnswerData> => {
  try {
    const response = await apiClient.post<AnswerData>(`/response`, data);
    return response.data;
  } catch (error) {
    console.error('Error sending answer:', error);
    throw error;
  }
};
