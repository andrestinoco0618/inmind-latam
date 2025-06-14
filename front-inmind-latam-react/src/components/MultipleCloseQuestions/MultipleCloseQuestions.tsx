"use client";
import React, { useState } from 'react';
import styles from './multipleCloseQuestion.module.css';

interface MultipleCloseQuestionProps {
  title: string;
  subtitle: string;
  questionNumber: number;
  optionsAnswer: Option[];
  onAnswerChange: (selectedOptions: string[]) => void;
}

interface Option {
  idAlternative: string;
  text: string;
}

const MultipleCloseQuestion = ({ title, subtitle, questionNumber, optionsAnswer, onAnswerChange }: MultipleCloseQuestionProps) => {
  const [selectedOptions, setSelectedOptions] = useState<string[]>([]); 

  const handleCheckboxChange = (option: string) => {
    const updatedSelectedOptions = selectedOptions.includes(option)
      ? selectedOptions.filter((selected) => selected !== option) 
      : [...selectedOptions, option]; 

    setSelectedOptions(updatedSelectedOptions);
    onAnswerChange(updatedSelectedOptions); 
  };

  return (
    <div className='multiple-question__container'>
      <div className={styles['multiple-question__title']}>
        <span>{`${questionNumber}. ${title}`}</span>
      </div>
      <div className={styles['multiple-question__subtitle']}>
        <p>{subtitle}</p>
      </div>
      <div className={styles['multiple-question__choices']}>
        <div className={styles['multiple-question__options']}>
          {optionsAnswer.map((option, index) => (
            <label
              key={index}
              className={`${styles['multiple-question__options--style']} ${selectedOptions.includes(option.idAlternative) ? styles['checked'] : ''}`}
            >
              <input
                type="checkbox"
                value={option.idAlternative}
                checked={selectedOptions.includes(option.idAlternative)} 
                onChange={() => handleCheckboxChange(option.idAlternative)}
              />
              <span className={`${styles['multiple']} ${selectedOptions.includes(option.idAlternative) ? styles['span-checked'] : ''}`}>
                <p className={styles['multiple-question__options-text']}>{option.text}</p>
              </span>
            </label>
          ))}
        </div>
      </div>
    </div>
  );
};

export default MultipleCloseQuestion;
