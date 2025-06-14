import { render, screen } from '@testing-library/react'
import InformativeQuestion from '../InformativeQuestion'

describe('InformativeQuestion', () => {
  const props = {
    greeting: '¡Hola!',
    normalInformation: 'Esto es información normal.',
    boldInformation: 'Esto es importante.',
  }

  it('should render normal information', () => {
    render(<InformativeQuestion {...props} />)
    expect(screen.getByText('Esto es información normal.')).toBeInTheDocument()
  })

  it('should render information in bold and italic by default', () => {
    render(<InformativeQuestion {...props} />)
    const strong = screen.getByText('Esto es importante.').closest('strong')
    const italic = screen.getByText('Esto es importante.').closest('i')
    expect(strong).toBeInTheDocument()
    expect(italic).toBeInTheDocument()
  })

  it('should render information only in italic when questionCode is P00214', () => {
    render(<InformativeQuestion {...props} questionCode="P00214" />)
    const italic = screen.getByText('Esto es importante.').closest('i')
    expect(italic).toBeInTheDocument()
    expect(italic?.tagName).toBe('I')
    expect(italic?.parentElement?.tagName).not.toBe('STRONG')
  })
}) 