import { render, screen, fireEvent, act } from '@testing-library/react';
import PsychologicalForm from '../PsychologicalForm';
import useSendAnswer from '@/hooks/useSendAnswer';
import { fetchInitialQuestions } from '@/api/QuestionsClientService/questionService';

Object.defineProperty(window, 'scrollTo', {
  value: jest.fn(),
  writable: true
});

jest.mock('@/hooks/useSendAnswer');
jest.mock('@/api/QuestionsClientService/questionService');
jest.mock('next/navigation', () => ({
  useRouter: () => ({
    push: jest.fn(),
  }),
}));

jest.mock('@/components/OpenQuestions/OpenQuestions', () => ({
  __esModule: true,
  default: () => <div>Open Question</div>,
}));

jest.mock('@/components/MultipleOpenQuestions/MultipleOpenQuestions', () => ({
  __esModule: true,
  default: () => <div>Multiple Open Question</div>,
}));

jest.mock('@/components/SelectQuestions/SelectQuestions', () => ({
  __esModule: true,
  default: () => <div>Select Question</div>,
}));

jest.mock('@/components/TermsConditionsAcceptance/TermsConditionsAcceptance', () => ({
  __esModule: true,
  default: () => <div>Terms Acceptance</div>,
}));

describe('PsychologicalForm', () => {
  beforeEach(() => {
    jest.clearAllMocks();
    (useSendAnswer as jest.Mock).mockReturnValue({
      submitAnswer: jest.fn(),
      newQuestion: undefined,
      setNewQuestion: jest.fn(),
    });
    (fetchInitialQuestions as jest.Mock).mockResolvedValue({
      idQuestionType: 'OPEN',
      idQuestionnaire: 'Q001',
      idQuestion: 'P001',
      title: 'Test Question',
      optionsAnswer: [],
      positionQuestion: 1,
      isFinish: false,
      linkProfile: '',
    });
  });

  it('should render initial profile selection screen', () => {
    render(<PsychologicalForm />);
    expect(screen.getByText('Mereces bienestar emocional.')).toBeInTheDocument();
    expect(screen.getByText('Para mí')).toBeInTheDocument();
    expect(screen.getByText('En pareja')).toBeInTheDocument();
    expect(screen.getByText('En familia')).toBeInTheDocument();
    expect(screen.getByText('Para mi hijo/a')).toBeInTheDocument();
  });

  it('should fetch questions when selecting a profile', async () => {
    render(<PsychologicalForm />);
    await act(async () => {
      fireEvent.click(screen.getByText('Para mí'));
    });
    expect(fetchInitialQuestions).toHaveBeenCalledWith('PF001');
  });
}); 