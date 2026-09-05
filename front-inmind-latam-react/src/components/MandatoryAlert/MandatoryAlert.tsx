import styles from './mandatoryAlert.module.css';

/**
 * @interface MandatoryAlertsProps
 * @description Props for the MandatoryAlert component
 * @property {string} text - Text to display in the alert
 */
interface MandatoryAlertsProps {
  text: string;
}

/**
 * @component MandatoryAlerts
 * @description Displays a mandatory alert message with an arrow indicator
 * @param {MandatoryAlertsProps} props - Component props
 * @returns {JSX.Element} Rendered mandatory alert component
 */
const MandatoryAlerts = ({ text }: MandatoryAlertsProps) => {
  return (
    <div className={styles['mandatory-alert__principal']}>
      <span className={styles['mandatory-alert__principal--arrow']}>
      </span>
      <div className={styles['mandatory-alert__container']}>
        <span className={styles['mandatory-alert__container--text']}>
          {text}
        </span>
      </div>
    </div>
  );
};

export default MandatoryAlerts;