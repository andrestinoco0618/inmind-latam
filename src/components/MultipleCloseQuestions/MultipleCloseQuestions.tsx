"use client";
import React, { useState } from 'react';
import styles from './multipleCloseQuestion.module.css';

interface MultipleCloseQuestionProps {
  title: string;
  subtitle: string;
  questionNumber: number;
  optionsAnswer: string[];
}

const MultipleCloseQuestion = ({ title, subtitle, questionNumber, optionsAnswer }: MultipleCloseQuestionProps) => {
  const [selectedOptions, setSelectedOptions] = useState<string[]>([]);

  const handleCheckboxChange = (option: string) => {
    setSelectedOptions(prevSelected => 
      prevSelected.includes(option)
        ? prevSelected.filter(item => item !== option) // Desmarca si ya estaba seleccionada
        : [...prevSelected, option] // Marca si no estaba seleccionada
    );
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
              className={`${styles['multiple-question__options--style']} ${selectedOptions.includes(option) ? styles['checked'] : ''}`}
            >
              <input
                type="checkbox"
                value={option}
                onChange={() => handleCheckboxChange(option)}
              />
              <span className={`${styles['multiple']} ${selectedOptions.includes(option) ? styles['span-checked'] : ''}`}>
                <p className={styles['multiple-question__options-text']}>{option}</p>
              </span>
            </label>
          ))}
        </div>
      </div>
    </div>
  );
};

export default MultipleCloseQuestion;
