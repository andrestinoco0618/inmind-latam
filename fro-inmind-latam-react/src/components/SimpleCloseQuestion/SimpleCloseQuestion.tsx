"use client";
import React, { useState } from 'react';
import styles from './simpleCloseQuestion.module.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCheck } from '@fortawesome/free-solid-svg-icons';

interface SimpleCloseQuestionProps {
  title: string;
  subtitle: string;
  questionNumber: number;
  optionsAnswer: Option[];
  onAnswerChange: (selectedOption: string[]) => void;  
}

interface Option {
  idAlternative: string;
  text: string;
}
const SimpleCloseQuestion = ({ title, subtitle, questionNumber, optionsAnswer, onAnswerChange }: SimpleCloseQuestionProps) => {
  const [selectedOption, setSelectedOption] = useState<string | null>(null); 

  const handleCheckboxChange = (option: string) => {
    const updatedSelectedOption = selectedOption === option ? null : option; 
    setSelectedOption(updatedSelectedOption);
    onAnswerChange(updatedSelectedOption ? [updatedSelectedOption] : []); 
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
              className={`${styles['close-question__options--style']} ${selectedOption === option.idAlternative ? styles['checked'] : ''}`}
            >
              <div className={styles['checkbox-container']} onClick={() => handleCheckboxChange(option.idAlternative)}>
                <input
                  type="checkbox"
                  value={option.idAlternative}
                  checked={selectedOption === option.idAlternative}
                  readOnly 
                  className={styles['checkbox-input']}
                />
                {selectedOption === option.idAlternative && (
                  <FontAwesomeIcon icon={faCheck} className={styles['check-icon']} />
                )}
              </div>
              <span className={`${styles['close']} ${selectedOption === option.idAlternative ? styles['span-checked'] : ''}`}>
                <p className={styles['close-question__options-text']}>{option.text}</p>
              </span>
            </label>
          ))}
        </div>
      </div>
    </div>
  );
};

export default SimpleCloseQuestion;
