import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import SimpleCloseQuestion from '../SimpleCloseQuestion';

describe('SimpleCloseQuestion', () => {
  const defaultProps = {
    title: 'Test Question',
    subtitle: 'Test Subtitle',
    questionNumber: 1,
    optionsAnswer: [
      { idAlternative: 'A00001', text: 'Option 1' },
      { idAlternative: 'A00002', text: 'Option 2' },
      { idAlternative: 'A00003', text: 'Option 3' }
    ],
    onAnswerChange: jest.fn()
  };

  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('should render question title, subtitle and number', () => {
    render(<SimpleCloseQuestion {...defaultProps} />);
    expect(screen.getByText('1. Test Question')).toBeInTheDocument();
    expect(screen.getByText('Test Subtitle')).toBeInTheDocument();
  });

  it('should render all options', () => {
    render(<SimpleCloseQuestion {...defaultProps} />);
    expect(screen.getByText('Option 1')).toBeInTheDocument();
    expect(screen.getByText('Option 2')).toBeInTheDocument();
    expect(screen.getByText('Option 3')).toBeInTheDocument();
  });

  it('should select an option and call onAnswerChange', () => {
    render(<SimpleCloseQuestion {...defaultProps} />);
    const option = screen.getByText('Option 2');
    fireEvent.click(option);
    expect(defaultProps.onAnswerChange).toHaveBeenCalledWith(['A00002']);
    const checkbox = screen.getAllByRole('checkbox')[1];
    expect(checkbox).toBeChecked();
  });

  it('should deselect an option when clicked again', () => {
    render(<SimpleCloseQuestion {...defaultProps} />);
    const option = screen.getByText('Option 1');
    fireEvent.click(option);
    expect(defaultProps.onAnswerChange).toHaveBeenCalledWith(['A00001']);
    fireEvent.click(option);
    expect(defaultProps.onAnswerChange).toHaveBeenCalledWith([]);
    const checkbox = screen.getAllByRole('checkbox')[0];
    expect(checkbox).not.toBeChecked();
  });

  it('should only allow one option to be selected at a time', () => {
    render(<SimpleCloseQuestion {...defaultProps} />);
    const option1 = screen.getByText('Option 1');
    const option2 = screen.getByText('Option 2');
    fireEvent.click(option1);
    expect(screen.getAllByRole('checkbox')[0]).toBeChecked();
    fireEvent.click(option2);
    expect(screen.getAllByRole('checkbox')[0]).not.toBeChecked();
    expect(screen.getAllByRole('checkbox')[1]).toBeChecked();
  });

  it('should apply checked style when option is selected', () => {
    render(<SimpleCloseQuestion {...defaultProps} />);
    const option = screen.getByText('Option 3');
    fireEvent.click(option);
    const label = option.closest('label');
    expect(label?.className).toMatch(/checked/);
  });
}); 