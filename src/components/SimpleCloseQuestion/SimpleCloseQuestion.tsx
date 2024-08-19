"use client";
import React, { useState } from 'react';
import styles from './simpleCloseQuestion.module.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCheck } from '@fortawesome/free-solid-svg-icons';

interface SimpleCloseQuestionProps {
  title: string;
  subtitle: string;
  questionNumber: number;
  optionsAnswer: string[];
}

const SimpleCloseQuestion = ({ title, subtitle, questionNumber, optionsAnswer }: SimpleCloseQuestionProps) => {
  const [selectedOption, setSelectedOption] = useState<string | null>(null);

  const handleCheckboxChange = (option: string) => {
    setSelectedOption(prevSelected =>
      prevSelected === option ? null : option
    );
  };

  return (
    <div className='close-question__container'>
      <div className={styles['close-question__title']}>
        <span>{`${questionNumber}. ${title}`}</span>
      </div>
      <div className={styles['close-question__subtitle']}>
        <p>{subtitle}</p>
      </div>
      <div className={styles['close-question__choices']}>
        <div className={styles['close-question__options']}>
          {optionsAnswer.map((option, index) => (
            <label
              key={index}
              className={`${styles['close-question__options--style']} ${selectedOption === option ? styles['checked'] : ''}`}
            >
              <div className={styles['checkbox-container']}>
                <input
                  type="checkbox"
                  value={option}
                  checked={selectedOption === option}
                  onChange={() => handleCheckboxChange(option)}
                  className={styles['checkbox-input']}
                />
                {selectedOption === option && (
                  <FontAwesomeIcon icon={faCheck} className={styles['check-icon']} />
                )}
              </div>
              <span className={`${styles['close']} ${selectedOption === option ? styles['span-checked'] : ''}`}>
                <p className={styles['close-question__options-text']}>{option}</p>
              </span>
            </label>
          ))}
        </div>
      </div>
    </div>
  );
};

export default SimpleCloseQuestion;
