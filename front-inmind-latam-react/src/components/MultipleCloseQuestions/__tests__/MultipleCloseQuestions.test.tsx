import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom';
import MultipleCloseQuestion from '../MultipleCloseQuestions';

describe('MultipleCloseQuestion', () => {
  const defaultProps = {
    title: 'Test Question',
    subtitle: 'Test Subtitle',
    questionNumber: 1,
    questionId: 'P00001',
    optionsAnswer: [
      { idAlternative: '1', text: 'Option 1' },
      { idAlternative: '2', text: 'Option 2' },
      { idAlternative: '3', text: 'Option 3' },
      { idAlternative: '4', text: 'Option 4' }
    ],
    onAnswerChange: jest.fn()
  };

  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('should render question title and subtitle', () => {
    render(<MultipleCloseQuestion {...defaultProps} />);
    
    expect(screen.getByText('1. Test Question')).toBeInTheDocument();
    expect(screen.getByText('Test Subtitle')).toBeInTheDocument();
  });

  it('should render all options', () => {
    render(<MultipleCloseQuestion {...defaultProps} />);
    
    defaultProps.optionsAnswer.forEach(option => {
      expect(screen.getByText(option.text)).toBeInTheDocument();
    });
  });

  it('should handle multiple selections', () => {
    render(<MultipleCloseQuestion {...defaultProps} />);
    
    const checkboxes = screen.getAllByRole('checkbox');
    
    fireEvent.click(checkboxes[0]);
    expect(defaultProps.onAnswerChange).toHaveBeenCalledWith(['1']);
    
    fireEvent.click(checkboxes[1]);
    expect(defaultProps.onAnswerChange).toHaveBeenCalledWith(['1', '2']);
  });

  it('should allow deselection of options', () => {
    render(<MultipleCloseQuestion {...defaultProps} />);
    
    const checkboxes = screen.getAllByRole('checkbox');
    
    fireEvent.click(checkboxes[0]);
    fireEvent.click(checkboxes[1]);
    fireEvent.click(checkboxes[0]);
    
    expect(defaultProps.onAnswerChange).toHaveBeenLastCalledWith(['2']);
  });

  it('should limit selections to 3 for question P00039', () => {
    render(<MultipleCloseQuestion {...defaultProps} questionId="P00039" />);
    
    const checkboxes = screen.getAllByRole('checkbox');
    
    fireEvent.click(checkboxes[0]);
    fireEvent.click(checkboxes[1]);
    fireEvent.click(checkboxes[2]);
    
    expect(checkboxes[3]).toBeDisabled();
  });

  it('should reset selections when question number changes', () => {
    const { rerender } = render(<MultipleCloseQuestion {...defaultProps} />);
    
    const checkboxes = screen.getAllByRole('checkbox');
    fireEvent.click(checkboxes[0]);
    fireEvent.click(checkboxes[1]);
    
    rerender(<MultipleCloseQuestion {...defaultProps} questionNumber={2} />);
    
    checkboxes.forEach(checkbox => {
      expect(checkbox).not.toBeChecked();
    });
  });

  it('should apply correct styles when option is selected', () => {
    render(<MultipleCloseQuestion {...defaultProps} />);
    
    const option = screen.getByText('Option 1').closest('label');
    const checkbox = screen.getByRole('checkbox', { name: 'Option 1' });
    
    fireEvent.click(checkbox);
    
    expect(option).toHaveClass('checked');
    expect(option?.querySelector('span')).toHaveClass('span-checked');
  });

  it('should apply disabled styles when limit is reached for P00039', () => {
    render(<MultipleCloseQuestion {...defaultProps} questionId="P00039" />);
    
    const checkboxes = screen.getAllByRole('checkbox');
    
    fireEvent.click(checkboxes[0]);
    fireEvent.click(checkboxes[1]);
    fireEvent.click(checkboxes[2]);
    
    const disabledOption = screen.getByText('Option 4').closest('label');
    expect(disabledOption).toHaveClass('disabled');
  });
}); 