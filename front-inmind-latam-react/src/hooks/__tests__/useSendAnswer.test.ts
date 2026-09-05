import { renderHook, act } from '@testing-library/react';
import useSendAnswer from '../useSendAnswer';
import { sendAnswer, AnswerData } from '@/api/QuestionsClientService/questionService';

jest.mock('@/api/QuestionsClientService/questionService', () => ({
  sendAnswer: jest.fn(),
}));

describe('useSendAnswer', () => {
  const mockAnswerData = {
    idQuestionnaire: 'Q001',
    idQuestion: 'P001',
    responseAnswer: ['answer1'],
    openQuestion: 'test answer',
  };

  const mockResponseData: AnswerData = {
    positionQuestion: 2,
    idQuestionnaire: 'Q001',
    idQuestion: 'P002',
    idQuestionType: 'OPEN',
    title: 'Next Question',
    subtitle: '',
    optionsAnswer: [],
    isFinish: false,
    linkProfile: '',
    status: 'success',
    redirect: '',
    listPsychologist: [],
  };

  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('should initialize with default values', () => {
    const { result } = renderHook(() => useSendAnswer());

    expect(result.current.loading).toBe(false);
    expect(result.current.error).toBeNull();
    expect(result.current.newQuestion).toBeUndefined();
  });

  it('should handle successful answer submission', async () => {
    (sendAnswer as jest.Mock).mockResolvedValueOnce(mockResponseData);

    const { result } = renderHook(() => useSendAnswer());

    await act(async () => {
      await result.current.submitAnswer(mockAnswerData);
    });

    expect(sendAnswer).toHaveBeenCalledWith(mockAnswerData);
    expect(result.current.loading).toBe(false);
    expect(result.current.error).toBeNull();
    expect(result.current.newQuestion).toEqual(mockResponseData);
  });

  it('should handle error during answer submission', async () => {
    const mockError = new Error('API Error');
    (sendAnswer as jest.Mock).mockRejectedValueOnce(mockError);

    const { result } = renderHook(() => useSendAnswer());

    await act(async () => {
      await result.current.submitAnswer(mockAnswerData);
    });

    expect(sendAnswer).toHaveBeenCalledWith(mockAnswerData);
    expect(result.current.loading).toBe(false);
    expect(result.current.error).toBe('Error sending answer');
    expect(result.current.newQuestion).toBeUndefined();
  });

  it('should update newQuestion when setNewQuestion is called', () => {
    const { result } = renderHook(() => useSendAnswer());

    act(() => {
      result.current.setNewQuestion(mockResponseData);
    });

    expect(result.current.newQuestion).toEqual(mockResponseData);
  });
}); 