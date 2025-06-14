import { render, screen, fireEvent } from '@testing-library/react'
import Header from '../header'

describe('Header', () => {
  it('renders the header with logo', () => {
    render(<Header />)
    const logo = screen.getByAltText('Logo de inmindlatam')
    expect(logo).toBeInTheDocument()
  })

  it('renders navigation menu items', () => {
    render(<Header />)
    expect(screen.getByText('Inicio')).toBeInTheDocument()
    expect(screen.getByText('Nosotros')).toBeInTheDocument()
    expect(screen.getByText('Servicios')).toBeInTheDocument()
    expect(screen.getByText('FAQ')).toBeInTheDocument()
  })

  it('renders social media links', () => {
    render(<Header />)
    const links = screen.getAllByRole('link')
    const facebookLink = links.find(link => link.getAttribute('href') === 'https://www.facebook.com/espacioparaconectar')
    const instagramLink = links.find(link => link.getAttribute('href') === 'https://www.instagram.com/espacioparaconectar/')
    const linkedinLink = links.find(link => link.getAttribute('href') === 'https://www.linkedin.com/company/inmind-latam/people/')
    
    expect(facebookLink).toBeInTheDocument()
    expect(instagramLink).toBeInTheDocument()
    expect(linkedinLink).toBeInTheDocument()
  })

  it('toggles menu when clicking menu icon', () => {
    const { container } = render(<Header />)
    const menuIcon = container.querySelector('.nav-menu__icon')
    fireEvent.click(menuIcon!)
    const menu = container.querySelector('.nav__menu')
    expect(menu).toHaveClass('nav__menu-open')
  })
}) 