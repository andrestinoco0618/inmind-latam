import React from 'react';
import { render, screen } from '@testing-library/react';
import WhatsAppButton from '../buttonWhatsApp';

describe('WhatsAppButton', () => {
  test('should render button with correct link', () => {
    render(<WhatsAppButton />);

    const linkElement = screen.getByRole('link', { name: /chat en whatsapp/i });

    expect(linkElement).toBeInTheDocument();
    expect(linkElement).toHaveAttribute('href');
    expect(linkElement).toHaveAttribute(
      'href',
      expect.stringContaining('https://api.whatsapp.com/send/')
    );
    expect(linkElement).toHaveAttribute('target', '_blank');
    expect(linkElement).toHaveAttribute('rel', 'noopener noreferrer');
  });

  test('should have correctly encoded message in URL', () => {
    render(<WhatsAppButton />);

    const linkElement = screen.getByRole('link', { name: /chat en whatsapp/i });

    const expectedMessage = encodeURIComponent('¡Hola! Quisiera empezar a conectar con un especialista');
    expect(linkElement?.getAttribute('href')).toContain(`text=${expectedMessage}`);
  });
});
