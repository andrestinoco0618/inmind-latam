"use client";
import styles from './header.module.css';
import Image from 'next/image';
import logo from '../../../public/inmind-logo.png';
import { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBars, faXmark } from '@fortawesome/free-solid-svg-icons';
import { faFacebook, faInstagram, faLinkedin } from '@fortawesome/free-brands-svg-icons';
const Header = () => {
  const [isMenuOpen, setMenuOpen] = useState(false);

  const toggleMenu = () => {
    setMenuOpen(!isMenuOpen);
  };


  return (
    <header className={styles.header}>
      <div className={styles.header__container}>
        <div className={styles['header__container-logo']}>
          <a href='https://inmindlatam.com'>
            <Image className={styles['container-logo__img']} src={logo} alt="Logo de inmindlatam" />
          </a>
        </div>
        <div className={styles['header__container-navmenu']}>
          <nav className={styles.nav__bar}>
            <div className={styles['nav-menu__icon']} onClick={toggleMenu}>
              <FontAwesomeIcon icon={isMenuOpen ? faXmark : faBars} className={styles['nav-menu__icon--styles']} />
            </div>
            <ul className={`${styles.nav__menu} ${isMenuOpen ? styles['nav__menu-open'] : styles['nav__menu-close']}`}>
              <li><a href="https://inmindlatam.com/">Inicio</a></li>
              <li><a href="https://inmindlatam.com/nosotros/">Nosotros</a></li>
              <li><a href="https://inmindlatam.com/servicios/">Servicios</a></li>
              <li><a href="https://inmindlatam.com/faq/">FAQ</a></li>
            </ul>
          </nav>
        </div>
        <div className={styles['header__container-media']}>
          <div className={styles['container-media__icons']}>
            <span className={styles['container-media__icons--purple']}>
              <a href='https://www.facebook.com/espacioparaconectar'>
                <FontAwesomeIcon className={styles['container-media__icons--white']} icon={faFacebook}></FontAwesomeIcon>
              </a>
            </span>
            <span className={styles['container-media__icons--pink']}>
              <a href='https://www.instagram.com/espacioparaconectar/'>
                <FontAwesomeIcon className={styles['container-media__icons--white']} icon={faInstagram}></FontAwesomeIcon>
              </a>
            </span>
            <span className={styles['container-media__icons--blue']}>
              <a href='https://www.linkedin.com/company/inmind-latam/people/'>
                <FontAwesomeIcon className={styles['container-media__icons--white']} icon={faLinkedin}></FontAwesomeIcon>
              </a>
            </span>
          </div>

        </div>
      </div>
    </header>
  );
};

export default Header;