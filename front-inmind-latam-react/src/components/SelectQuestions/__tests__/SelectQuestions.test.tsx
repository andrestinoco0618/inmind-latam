import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import SelectQuestion from '../SelectQuestions';

describe('SelectQuestion', () => {
  const defaultProps = {
    title: 'Test Question',
    subtitle: 'Test Subtitle',
    questionNumber: 1,
    optionsAnswer: [
      { idAlternative: 'A00001', text: 'Option 1' },
      { idAlternative: 'A00002', text: 'Option 2' },
      { idAlternative: 'A00003', text: 'Another Option' }
    ],
    onAnswerChange: jest.fn()
  };

  beforeEach(() => {
    jest.clearAllMocks();
    jest.useFakeTimers();
  });

  afterEach(() => {
    jest.useRealTimers();
  });

  it('should render question title, subtitle and number', () => {
    render(<SelectQuestion {...defaultProps} />);
    
    expect(screen.getByText('1. Test Question')).toBeInTheDocument();
    expect(screen.getByText('Test Subtitle')).toBeInTheDocument();
  });

  it('should render input with placeholder', () => {
    render(<SelectQuestion {...defaultProps} />);
    
    const input = screen.getByPlaceholderText('Selecciona una opción');
    expect(input).toBeInTheDocument();
    expect(screen.getByText('▼')).toBeInTheDocument();
  });

  it('should show dropdown when input is focused', () => {
    render(<SelectQuestion {...defaultProps} />);
    
    const input = screen.getByPlaceholderText('Selecciona una opción');
    fireEvent.focus(input);
    
    expect(screen.getByText('Option 1')).toBeInTheDocument();
    expect(screen.getByText('Option 2')).toBeInTheDocument();
    expect(screen.getByText('Another Option')).toBeInTheDocument();
  });

  it('should filter options when typing', () => {
    render(<SelectQuestion {...defaultProps} />);
    
    const input = screen.getByPlaceholderText('Selecciona una opción');
    fireEvent.focus(input);
    fireEvent.change(input, { target: { value: 'Option' } });
    
    const options = screen.getAllByRole('listitem');
    expect(options).toHaveLength(3);
    expect(screen.getByText('Option 1')).toBeInTheDocument();
    expect(screen.getByText('Option 2')).toBeInTheDocument();
    expect(screen.getByText('Another Option')).toBeInTheDocument();
  });

  it('should select option and call onAnswerChange', () => {
    render(<SelectQuestion {...defaultProps} />);
    
    const input = screen.getByPlaceholderText('Selecciona una opción');
    fireEvent.focus(input);
    
    const option = screen.getByText('Option 1');
    fireEvent.click(option);
    
    expect(input).toHaveValue('Option 1');
    expect(defaultProps.onAnswerChange).toHaveBeenCalledWith(['A00001']);
  });

  it('should show all options when input is cleared', () => {
    render(<SelectQuestion {...defaultProps} />);
    
    const input = screen.getByPlaceholderText('Selecciona una opción');
    fireEvent.focus(input);
    fireEvent.change(input, { target: { value: 'Option' } });
    
    let filteredOptions = screen.getAllByRole('listitem');
    expect(filteredOptions).toHaveLength(3);
    
    fireEvent.change(input, { target: { value: '' } });
    
    const allOptions = screen.getAllByRole('listitem');
    expect(allOptions).toHaveLength(3);
  });
}); 