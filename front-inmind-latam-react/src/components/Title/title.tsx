import styles from './title.module.css';
import { TITLE_HOME } from '@/utils/Constants';

/**
 * @component Title
 * @description Displays the main title of the application
 * @returns {JSX.Element} Rendered title component
 */
const Title = () => {
  return (
    <h1 className={styles.first__title}>
        {TITLE_HOME}
    </h1>
  );
};

export default Title;