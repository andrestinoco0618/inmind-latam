import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom';
import TermsAndConditionsAcceptance from '../TermsConditionsAcceptance';

jest.mock('@fortawesome/react-fontawesome', () => ({
  FontAwesomeIcon: () => <span data-testid="mock-icon">✓</span>
}));

describe('TermsAndConditionsAcceptance', () => {
  const defaultProps = {
    onAnswerChange: jest.fn(),
    onTermsClick: jest.fn(),
  };

  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('should render checkbox and terms text', () => {
    render(<TermsAndConditionsAcceptance {...defaultProps} />);
    
    expect(screen.getByText(/Estoy de acuerdo con los/)).toBeInTheDocument();
    expect(screen.getByText('Términos y condiciones')).toBeInTheDocument();
    expect(screen.getByRole('checkbox')).toBeInTheDocument();
  });

  it('should toggle checkbox state on click', () => {
    render(<TermsAndConditionsAcceptance {...defaultProps} />);
    
    const checkbox = screen.getByRole('checkbox');
    expect(checkbox).not.toBeChecked();
    
    fireEvent.click(checkbox);
    expect(checkbox).toBeChecked();
    expect(defaultProps.onAnswerChange).toHaveBeenCalledWith(['accepted']);
    
    fireEvent.click(checkbox);
    expect(checkbox).not.toBeChecked();
    expect(defaultProps.onAnswerChange).toHaveBeenCalledWith([]);
  });

  it('should call onTermsClick when clicking terms link', () => {
    render(<TermsAndConditionsAcceptance {...defaultProps} />);
    
    const termsLink = screen.getByText('Términos y condiciones');
    fireEvent.click(termsLink);
    
    expect(defaultProps.onTermsClick).toHaveBeenCalledTimes(1);
  });

  it('should show check icon when checkbox is checked', () => {
    render(<TermsAndConditionsAcceptance {...defaultProps} />);
    
    const checkbox = screen.getByRole('checkbox');
    fireEvent.click(checkbox);
    
    expect(screen.getByTestId('mock-icon')).toBeInTheDocument();
  });

  it('should apply correct styles when checkbox is checked', () => {
    render(<TermsAndConditionsAcceptance {...defaultProps} />);
    
    const label = screen.getByText(/Estoy de acuerdo con los/).closest('label');
    expect(label).not.toHaveClass('checked');
    
    const checkbox = screen.getByRole('checkbox');
    fireEvent.click(checkbox);
    
    expect(label).toHaveClass('checked');
  });
}); 