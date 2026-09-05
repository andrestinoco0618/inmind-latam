"use client";
import React, { useState } from 'react';
import styles from './termsConditionsAcceptance.module.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCheck } from '@fortawesome/free-solid-svg-icons';

interface TermsAndConditionsProps {
  onAnswerChange: (selectedOption: string[]) => void;
  onTermsClick: () => void; 
}

const TermsAndConditionsAcceptance: React.FC<TermsAndConditionsProps> = ({
  onAnswerChange,
  onTermsClick,
}) => {
  const [checked, setChecked] = useState(false);

  const handleChange = () => {
    const newChecked = !checked;
    setChecked(newChecked);
    onAnswerChange(newChecked ? ['accepted'] : []);
  };

  return (
    <div className="close-question__container">
      <div className={styles['close-question__choices']}>
        <label
          className={`${styles['close-question__options--style']} ${checked ? styles['checked'] : ''}`}
          onClick={handleChange}
        >
          <div className={styles['checkbox-container']}>
            <input
              type="checkbox"
              checked={checked}
              readOnly
              className={styles['checkbox-input']}
            />
            {checked && <FontAwesomeIcon icon={faCheck} className={styles['check-icon']} />}
          </div>

          <span className={`${styles['full-width-text']} ${checked ? styles['span-checked'] : ''}`}>
            <p className={styles['close-question__options-text']}>
              Estoy de acuerdo con los{' '}
              <span
                onClick={(e) => {
                  e.stopPropagation();
                  onTermsClick();
                }}
                style={{ textDecoration: 'underline', cursor: 'pointer', color: '#2f3e90' }}
              >
                Términos y condiciones
              </span>.
            </p>
          </span>
        </label>
      </div>
    </div>
  );
};

export default TermsAndConditionsAcceptance;
