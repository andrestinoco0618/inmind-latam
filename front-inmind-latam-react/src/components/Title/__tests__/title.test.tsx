import React from 'react';
import { render, screen } from '@testing-library/react';
import Title from '../title';
import { TITLE_HOME } from '@/utils/Constants';

describe('Title', () => {
  it('should render h1 with the correct class', () => {
    render(<Title />);
    const h1 = screen.getByRole('heading', { level: 1 });
    expect(h1).toBeInTheDocument();
    expect(h1.className).toMatch(/first__title/);
  });

  it('should render the TITLE_HOME text', () => {
    render(<Title />);
    expect(screen.getByText(TITLE_HOME)).toBeInTheDocument();
  });
}); 