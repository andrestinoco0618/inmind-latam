"use client";
import React, { useState, useEffect } from 'react';
import styles from './multipleCloseQuestion.module.css';

/**
 * @interface MultipleCloseQuestionProps
 * @description Props for the MultipleCloseQuestion component
 * @property {string} title - Question title
 * @property {string} subtitle - Question subtitle
 * @property {number} questionNumber - Question number in sequence
 * @property {Option[]} optionsAnswer - Available answer options
 * @property {string} questionId - Question identifier
 * @property {Function} onAnswerChange - Callback for answer changes
 */
interface MultipleCloseQuestionProps {
  title: string;
  subtitle: string;
  questionNumber: number;
  optionsAnswer: Option[];
  questionId: string,
  onAnswerChange: (selectedOptions: string[]) => void;
}

interface Option {
  idAlternative: string;
  text: string;
}

/**
 * @component MultipleCloseQuestion
 * @description Renders a multiple-choice question with checkbox options
 * @param {MultipleCloseQuestionProps} props - Component props
 * @returns {JSX.Element} Rendered multiple close question component
 */
const MultipleCloseQuestion = ({ title, subtitle, questionNumber, optionsAnswer, onAnswerChange, questionId }: MultipleCloseQuestionProps) => {
  const [selectedOptions, setSelectedOptions] = useState<string[]>([]); 

  useEffect(() => {
    setSelectedOptions([]); 
  }, [questionNumber]);
  
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
          {optionsAnswer.map((option, index) => {
            const isChecked = selectedOptions.includes(option.idAlternative);
            const isDisabled =
              questionId === "P00039" &&
              selectedOptions.length >= 3 &&
              !isChecked;

            return (
              <label
                key={index}
                className={`
                  ${styles['multiple-question__options--style']} 
                  ${isChecked ? styles['checked'] : ''} 
                  ${isDisabled ? styles['disabled'] : ''}
                `}
              >
                <input
                  type="checkbox"
                  value={option.idAlternative}
                  checked={isChecked}
                  onChange={() => handleCheckboxChange(option.idAlternative)}
                  disabled={isDisabled}
                />
                <span className={`
                  ${styles['multiple']} 
                  ${isChecked ? styles['span-checked'] : ''}
                `}>
                  <p className={styles['multiple-question__options-text']}>{option.text}</p>
                </span>
              </label>
            );
          })}
        </div>
      </div>
    </div>
  );
};
export default MultipleCloseQuestion;
