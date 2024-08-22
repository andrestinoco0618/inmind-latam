"use client";
import React, { useState } from 'react';
import styles from './selectQuestions.module.css';

interface SelectQuestionProps {
  title: string;
  subtitle: string;
  questionNumber: number;
  optionsAnswer: string[];
}

const SelectQuestion = ({ title, subtitle, questionNumber, optionsAnswer }: SelectQuestionProps) => {
  const [selectedOption, setSelectedOption] = useState<string>("");

  const handleSelectChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setSelectedOption(event.target.value);
  };

  return (
    <div className='select-question__container'>
      <div className={styles['select-question__title']}>
        <span>{`${questionNumber}. ${title}`}</span>
      </div>
      <div className={styles['select-question__subtitle']}>
        <p>{subtitle}</p>
      </div>
      <div className={styles['select-question__choices']}>
        <select
          value={selectedOption}
          onChange={handleSelectChange}
          className={styles['select-question__select']}
        >
          <option value="" disabled>Selecciona una opción</option>
          {optionsAnswer.map((option, index) => (
            <option key={index} value={option}>
              {option}
            </option>
          ))}
        </select>
      </div>
    </div>
  );
};

export default SelectQuestion;
