import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom';
import Modal from '../ResultsPsychologistsModal';
import { savePsychologist } from '@/api/SavePsychologistClient/savePsychologistService';
import { listPsychologist } from '@/api/QuestionsClientService/questionService';

jest.mock('../../../../public/img/not-found.jpg', () => ({
  src: '/img/not-found.jpg'
}));

jest.mock('@/api/SavePsychologistClient/savePsychologistService', () => ({
  savePsychologist: jest.fn(),
}));

const mockOpen = jest.fn();
window.open = mockOpen;

describe('ResultsPsychologistsModal', () => {
  const mockPsychologists: listPsychologist[] = [
    {
      idQuestionnaire: '123',
      idPsychologist: '1',
      name: 'Dr. John Doe',
      image: 'test-image.jpg',
      linkProfile: 'https://linkedin.com/test',
    },
    {
      idQuestionnaire: '123',
      idPsychologist: '2',
      name: 'Dra. Jane Smith',
      image: 'PENDIENTE',
      linkProfile: 'https://linkedin.com/test2',
    },
  ];

  const defaultProps = {
    isOpen: true,
    psychologists: mockPsychologists,
    idQuestionnaire: '123',
  };

  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('should not render when isOpen is false', () => {
    render(<Modal {...defaultProps} isOpen={false} />);
    expect(screen.queryByText('Estos son los psicólogos ideales para ti')).not.toBeInTheDocument();
  });

  it('should render the modal with title and description when isOpen is true', () => {
    render(<Modal {...defaultProps} />);
    
    expect(screen.getByText('Estos son los psicólogos ideales para ti')).toBeInTheDocument();
    expect(screen.getByText(/Hemos analizado cuidadosamente tus respuestas/)).toBeInTheDocument();
  });

  it('should render all psychologists cards', () => {
    render(<Modal {...defaultProps} />);
    
    expect(screen.getByText('Dr. John Doe')).toBeInTheDocument();
    expect(screen.getByText('Dra. Jane Smith')).toBeInTheDocument();
  });

  it('should handle psychologist selection successfully', async () => {
    (savePsychologist as jest.Mock).mockResolvedValueOnce(undefined);
    
    render(<Modal {...defaultProps} />);
    
    const buttons = screen.getAllByText('Ver Perfil');
    fireEvent.click(buttons[0]);

    await waitFor(() => {
      expect(savePsychologist).toHaveBeenCalledWith('123', '1');
      expect(mockOpen).toHaveBeenCalledWith('https://linkedin.com/test', '_blank');
    });
  });

  it('should handle error when saving psychologist fails', async () => {
    const consoleErrorSpy = jest.spyOn(console, 'error').mockImplementation();
    (savePsychologist as jest.Mock).mockRejectedValueOnce(new Error('Save failed'));
    
    render(<Modal {...defaultProps} />);
    
    const buttons = screen.getAllByText('Ver Perfil');
    fireEvent.click(buttons[0]);

    await waitFor(() => {
      expect(consoleErrorSpy).toHaveBeenCalledWith('Error saving the psychologist', expect.any(Error));
    });

    consoleErrorSpy.mockRestore();
  });

  it('should use default image when psychologist image is PENDIENTE', () => {
    render(<Modal {...defaultProps} />);
    
    const images = screen.getAllByRole('img');
    const pendingImage = images.find(img => img.getAttribute('alt') === 'Foto de Dra. Jane Smith');
    
    expect(pendingImage).toHaveAttribute('src', '/img/not-found.jpg');
  });
}); 