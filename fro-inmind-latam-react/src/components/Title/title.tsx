import styles from './title.module.css';
import { TITLE_HOME } from '@/utils/Constants';
const Title = () => {
  return (
    <h1 className={styles.first__title}>
        {TITLE_HOME}
    </h1>
        
  );
};

export default Title;