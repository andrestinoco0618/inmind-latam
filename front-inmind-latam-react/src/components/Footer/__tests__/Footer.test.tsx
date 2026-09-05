import { render } from '@testing-library/react'
import Footer from '../footer'

describe('Footer', () => {
  it('renders the footer component with two containers', () => {
    const { container } = render(<Footer />)
    const firstContainer = container.querySelector('.container__footer--first')
    const secondContainer = container.querySelector('.container__footer--second')
    
    expect(firstContainer).toBeInTheDocument()
    expect(secondContainer).toBeInTheDocument()
  })

  it('renders the footer with correct CSS classes', () => {
    const { container } = render(<Footer />)
    const firstContainer = container.querySelector('.container__footer--first')
    const secondContainer = container.querySelector('.container__footer--second')
    
    expect(firstContainer).toHaveClass('container__footer--first')
    expect(secondContainer).toHaveClass('container__footer--second')
  })
}) 