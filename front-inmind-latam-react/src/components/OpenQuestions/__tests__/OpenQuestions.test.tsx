import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import OpenQuestion from '../OpenQuestions';

describe('OpenQuestion', () => {
  const defaultProps = {
    idQuestion: 'P00001',
    title: 'Test Question',
    questionNumber: 1,
    textLength: 10,
    onAnswerChange: jest.fn(),
  };

  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('should render question title and number', () => {
    render(<OpenQuestion {...defaultProps} />);
    expect(screen.getByText('1. Test Question')).toBeInTheDocument();
  });

  it('should render textarea and character counter', () => {
    render(<OpenQuestion {...defaultProps} />);
    expect(screen.getByRole('textbox')).toBeInTheDocument();
    expect(screen.getByText('0/10')).toBeInTheDocument();
  });

  it('should call onAnswerChange when typing', () => {
    render(<OpenQuestion {...defaultProps} />);
    const textarea = screen.getByRole('textbox');
    fireEvent.change(textarea, { target: { value: 'Hello' } });
    expect(defaultProps.onAnswerChange).toHaveBeenCalledWith('Hello');
    expect(textarea).toHaveValue('Hello');
  });

  it('should not allow more than max characters', () => {
    render(<OpenQuestion {...defaultProps} textLength={5} />);
    const textarea = screen.getByRole('textbox');
    fireEvent.change(textarea, { target: { value: '123456' } });
    expect(textarea).toHaveValue('');
  });

  it('should reset textarea when question number changes', () => {
    const { rerender } = render(<OpenQuestion {...defaultProps} />);
    const textarea = screen.getByRole('textbox');
    fireEvent.change(textarea, { target: { value: 'Test' } });
    rerender(<OpenQuestion {...defaultProps} questionNumber={2} />);
    expect(textarea).toHaveValue('');
  });

  it('should only allow numbers for numeric questions', () => {
    render(<OpenQuestion {...defaultProps} idQuestion="P00041" />);
    const textarea = screen.getByRole('textbox');
    fireEvent.change(textarea, { target: { value: 'abc123!@#' } });
    expect(textarea).toHaveValue('123');
  });
}); 