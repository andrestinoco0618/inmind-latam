import { render, screen } from '@testing-library/react'
import MandatoryAlert from '../MandatoryAlert'

describe('MandatoryAlert', () => {
  it('should display default message', () => {
    render(<MandatoryAlert text="This field is mandatory" />)
    expect(screen.getByText('This field is mandatory')).toBeInTheDocument()
  })

  it('should display custom message', () => {
    render(<MandatoryAlert text="Campo obligatorio" />)
    expect(screen.getByText('Campo obligatorio')).toBeInTheDocument()
  })
}) 