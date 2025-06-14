"use client";
import React, { useState } from 'react';
import styles from './selectQuestions.module.css';

interface SelectQuestionProps {
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

const SelectQuestion = ({ title, subtitle, questionNumber, optionsAnswer,onAnswerChange }: SelectQuestionProps) => {
  const [selectedOptions, setSelectedOptions] = useState<string[]>([]);

  const handleSelectChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    const selectedValue = event.target.value;
    setSelectedOptions([selectedValue]); 
    onAnswerChange([selectedValue]); 
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
          value={"option.idAlternative"}
          onChange={handleSelectChange}
          className={styles['select-question__select']}
        >
          <option value="" disabled>Selecciona una opción</option>
          {optionsAnswer.map((option, index) => (
            <option key={index} value={option.idAlternative}>
              {option.text}
            </option>
          ))}
        </select>
      </div>
    </div>
  );
};

export default SelectQuestion;
