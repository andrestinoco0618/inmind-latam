import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom';
import MultipleOpenQuestion from '../MultipleOpenQuestions';

// Mock scrollIntoView
Element.prototype.scrollIntoView = jest.fn();

describe('MultipleOpenQuestion', () => {
  const defaultProps = {
    title: 'Test Question',
    subtitle: 'Test Subtitle',
    questionNumber: 1,
    optionsAnswer: [
      { idAlternative: 'A00001', text: 'Option 1' },
      { idAlternative: 'A00052', text: 'Other Option' },
      { idAlternative: 'A00002', text: 'Option 2' }
    ],
    onAnswerChange: jest.fn()
  };

  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('should render question title and subtitle', () => {
    render(<MultipleOpenQuestion {...defaultProps} />);
    
    expect(screen.getByText('1. Test Question')).toBeInTheDocument();
    expect(screen.getByText('Test Subtitle')).toBeInTheDocument();
  });

  it('should render all options', () => {
    render(<MultipleOpenQuestion {...defaultProps} />);
    
    expect(screen.getByText('Option 1')).toBeInTheDocument();
    expect(screen.getByText('Other Option')).toBeInTheDocument();
    expect(screen.getByText('Option 2')).toBeInTheDocument();
  });

  it('should handle normal option selection', () => {
    render(<MultipleOpenQuestion {...defaultProps} />);
    
    const checkbox = screen.getByRole('checkbox', { name: 'Option 1' });
    fireEvent.click(checkbox);
    
    expect(checkbox).toBeChecked();
    expect(defaultProps.onAnswerChange).toHaveBeenCalledWith(['A00001'], '');
  });

  it('should show textarea when selecting other option', () => {
    render(<MultipleOpenQuestion {...defaultProps} />);
    
    const otherCheckbox = screen.getByRole('checkbox', { name: 'Other Option' });
    fireEvent.click(otherCheckbox);
    
    const textarea = screen.getByRole('textbox');
    expect(textarea).toBeInTheDocument();
    expect(screen.getByText('Otro')).toBeInTheDocument();
  });

  it('should handle textarea input', () => {
    render(<MultipleOpenQuestion {...defaultProps} />);
    
    const otherCheckbox = screen.getByRole('checkbox', { name: 'Other Option' });
    fireEvent.click(otherCheckbox);
    
    const textarea = screen.getByRole('textbox');
    fireEvent.change(textarea, { target: { value: 'Test response' } });
    
    expect(defaultProps.onAnswerChange).toHaveBeenCalledWith(['A00052'], 'Test response');
  });


  it('should reset state when question number changes', () => {
    const { rerender } = render(<MultipleOpenQuestion {...defaultProps} />);
    
    const checkbox = screen.getByRole('checkbox', { name: 'Option 1' });
    fireEvent.click(checkbox);
    
    rerender(<MultipleOpenQuestion {...defaultProps} questionNumber={2} />);
    
    expect(checkbox).not.toBeChecked();
    expect(screen.queryByRole('textbox')).not.toBeInTheDocument();
  });

  it('should apply correct styles when option is selected', () => {
    render(<MultipleOpenQuestion {...defaultProps} />);
    
    const checkbox = screen.getByRole('checkbox', { name: 'Option 1' });
    fireEvent.click(checkbox);
    
    const label = checkbox.closest('label');
    expect(label).toHaveClass('checked');
  });

  it('should deselect other options when selecting an open question option', () => {
    render(<MultipleOpenQuestion {...defaultProps} />);
    
    const normalCheckbox = screen.getByRole('checkbox', { name: 'Option 1' });
    const otherCheckbox = screen.getByRole('checkbox', { name: 'Other Option' });
    
    fireEvent.click(normalCheckbox);
    fireEvent.click(otherCheckbox);
    
    expect(normalCheckbox).not.toBeChecked();
    expect(otherCheckbox).toBeChecked();
    expect(screen.getByRole('textbox')).toBeInTheDocument();
  });
}); 