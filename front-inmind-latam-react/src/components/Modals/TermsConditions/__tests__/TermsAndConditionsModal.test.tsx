import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom';
import TermsAndConditionsModal from '../TermsAndConditionsModal';

describe('TermsAndConditionsModal', () => {
  const defaultProps = {
    isOpen: true,
    onClose: jest.fn(),
    onAccept: jest.fn(),
  };

  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('should not render when isOpen is false', () => {
    render(<TermsAndConditionsModal {...defaultProps} isOpen={false} />);
    expect(screen.queryByText('Términos y Condiciones')).not.toBeInTheDocument();
  });

  it('should render title and content when open', () => {
    render(<TermsAndConditionsModal {...defaultProps} />);
    
    expect(screen.getByText('Términos y Condiciones')).toBeInTheDocument();
    expect(screen.getByText('Términos de Uso')).toBeInTheDocument();
    expect(screen.getByText(/IMPORTANTE: SI USTED ESTÁ PENSANDO EN EL SUICIDIO/)).toBeInTheDocument();
  });

  it('should call onAccept when clicking Okay button', () => {
    render(<TermsAndConditionsModal {...defaultProps} />);
    
    const okayButton = screen.getByText('Okay');
    fireEvent.click(okayButton);
    
    expect(defaultProps.onAccept).toHaveBeenCalledTimes(1);
  });

  it('should call onClose when clicking Cancel button', () => {
    render(<TermsAndConditionsModal {...defaultProps} />);
    
    const cancelButton = screen.getByText('Cancelar');
    fireEvent.click(cancelButton);
    
    expect(defaultProps.onClose).toHaveBeenCalledTimes(1);
  });
}); 