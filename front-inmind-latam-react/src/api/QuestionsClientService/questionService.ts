import apiClient from '../apiClient';

/**
 * @interface GetQuestions
 * @description Interface for the initial questions response
 * @property {number} positionQuestion - Current question position
 * @property {string} idQuestionnaire - Questionnaire identifier
 * @property {string} idQuestion - Question identifier
 * @property {string} idQuestionType - Question type identifier
 * @property {string} title - Question title
 * @property {string} subtitle - Question subtitle
 * @property {Array} optionsAnswer - Available answer options
 * @property {boolean} isFinish - Indicates if questionnaire is finished
 * @property {string} linkProfile - Profile link
 */
export interface GetQuestions {
  positionQuestion: number;
  idQuestionnaire: string;
  idQuestion: string;
  idQuestionType: string;
  title: string;
  subtitle: string;
  optionsAnswer: [];
  isFinish: boolean,
  linkProfile: string;
}

/**
 * @interface AnswerData
 * @description Interface for the answer response data
 * @property {number} positionQuestion - Current question position
 * @property {string} idQuestionnaire - Questionnaire identifier
 * @property {string} idQuestion - Question identifier
 * @property {string} idQuestionType - Question type identifier
 * @property {string} title - Question title
 * @property {string} subtitle - Question subtitle
 * @property {Array} optionsAnswer - Available answer options
 * @property {boolean} isFinish - Indicates if questionnaire is finished
 * @property {string} linkProfile - Profile link
 * @property {string} status - Response status
 * @property {string} redirect - Redirect URL
 * @property {Array} listPsychologist - List of psychologists
 */
export interface AnswerData {
  positionQuestion: number;
  idQuestionnaire: string;
  idQuestion: string;
  idQuestionType: string;
  title: string;
  subtitle: string;
  optionsAnswer: [];
  isFinish: boolean;
  linkProfile: string;
  status: string;
  redirect: string,
  listPsychologist : [];
}

/**
 * @interface listPsychologist
 * @description Interface for psychologist data
 * @property {string} idQuestionnaire - Questionnaire identifier
 * @property {string} idPsychologist - Psychologist identifier
 * @property {string} name - Psychologist name
 * @property {string} linkProfile - Profile link
 * @property {string} image - Psychologist image URL
 */
export interface listPsychologist {
  idQuestionnaire: string;
  idPsychologist: string,
  name: string,
  linkProfile: string
  image: string 
}

/**
 * @interface DataSendAnswer
 * @description Interface for sending answer data
 * @property {string} idQuestionnaire - Questionnaire identifier
 * @property {string} idQuestion - Question identifier
 * @property {string[]} responseAnswer - Array of answer responses
 * @property {string} openQuestion - Open question response
 */
export interface DataSendAnswer {
  idQuestionnaire: string;
  idQuestion: string;
  responseAnswer: string[];
  openQuestion: string;
}

/**
 * @api {get} /start Fetch initial questions
 * @apiName fetchInitialQuestions
 * @apiGroup Questions
 * @apiParam {string} profileType - Type of profile to fetch questions for
 * @apiSuccess {GetQuestions} data - Initial questions data
 * @apiError {Error} error - Error object
 */
export const fetchInitialQuestions = async (profileType: string): Promise<GetQuestions> => {
  try {
    const response = await apiClient.get<GetQuestions>(`/start?profileType=${profileType}`);
    return response.data;
  } catch (error) {
    console.error("Error fetching initial questions:", error);
    throw error;
  }
};

/**
 * @api {post} /response Send answer
 * @apiName sendAnswer
 * @apiGroup Questions
 * @apiParam {DataSendAnswer} data - Answer data to send
 * @apiSuccess {AnswerData} data - Response data after sending answer
 * @apiError {Error} error - Error object
 */
export const sendAnswer = async (data: DataSendAnswer): Promise<AnswerData> => {
  try {
    const response = await apiClient.post<AnswerData>(`/response`, data);
    return response.data;
  } catch (error) {
    console.error('Error sending answer:', error);
    throw error;
  }
};
