import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faWhatsapp } from '@fortawesome/free-brands-svg-icons';
import styles from './buttonWhatsApp.module.css';

const WhatsAppButton: React.FC = () => {
  const phoneNumber = '51981465928'; 
  const msg = '¡Hola! Quisiera empezar a conectar con un especialista';
  const encodedMsg = encodeURIComponent(msg);

  const whatsappURL = `https://api.whatsapp.com/send/?phone=${phoneNumber}&text=${encodedMsg}&type=phone_number&app_absent=0`;

  return (
    <a
      href={whatsappURL}
      className={styles['whatsapp-button']}
      target="_blank"
      rel="noopener noreferrer"
      aria-label="Chat en WhatsApp"
    >
      <FontAwesomeIcon icon={faWhatsapp} color="white" size="2x" />
    </a>
  );
};

export default WhatsAppButton;
