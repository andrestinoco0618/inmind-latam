import styles from './footer.module.css';

/**
 * @component Footer
 * @description Displays the application footer with two sections
 * @returns {JSX.Element} Rendered footer component
 */
const Footer = () => {
  return (
    <>
    <div className={styles['container__footer--first']}>
    </div>  
    <div className={styles['container__footer--second']}>
    </div>  
    </>
    
  );
};

export default Footer;