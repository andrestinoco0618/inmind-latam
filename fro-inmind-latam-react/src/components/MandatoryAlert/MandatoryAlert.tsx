import styles from './mandatoryAlert.module.css';
const MandatoryAlerts = () => {
  return (
<div className={styles['mandatory-alert__principal']}>
    <span className={styles['mandatory-alert__principal--arrow']}>
    </span>
    <div className={styles['mandatory-alert__container']}>
        <span className={styles['mandatory-alert__container--text']}>
        Campo es obligatorio
</span>
    </div>
</div>
        
  );
};

export default MandatoryAlerts;