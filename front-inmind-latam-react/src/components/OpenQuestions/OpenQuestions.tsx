"use client"; 
import React, { useState, useEffect } from 'react';
import styles from './openQuestions.module.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faAlignLeft } from '@fortawesome/free-solid-svg-icons';

/**
 * @interface OpenQuestionProps
 * @description Props for the OpenQuestion component
 * @property {string} idQuestion - Question identifier
 * @property {string} title - Question title
 * @property {number} questionNumber - Question number in sequence
 * @property {number} textLength - Maximum length of text response
 * @property {Function} onAnswerChange - Callback for answer changes
 */
interface OpenQuestionProps {
  idQuestion: string;
  title: string;
  questionNumber: number;
  textLength: number; 
  onAnswerChange: (textResponse: string) => void;
}

const NumberQuestionsOptions = [
  "P00041", "P00116", "P00139", "P00167","P00187","P00042","P00118"
];

/**
 * @component OpenQuestion
 * @description Renders an open-ended question with a text input field
 * @param {OpenQuestionProps} props - Component props
 * @returns {JSX.Element} Rendered open question component
 */
const OpenQuestion = ({ title, questionNumber, textLength, onAnswerChange, idQuestion }: OpenQuestionProps) => {
  const [text, setText] = useState('');
  const maxChars = textLength;

  const handleChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    let inputText = event.target.value;

    if (NumberQuestionsOptions.includes(idQuestion)) {
      inputText = inputText.replace(/[^0-9]/g, '');
    }

    if (inputText.length <= maxChars) {
      setText(inputText);
      onAnswerChange(inputText);
    }
  };

  useEffect(() => {
    setText('');
  }, [questionNumber]);

  return (
    <div className='open-question__container'>
      <div className={styles['open-question__title']}>
        <span>{`${questionNumber}. ${title}`}</span>
      </div>
      <div className={styles['open-question__space']}>
        <div className={styles['open-question__icon']}>
          <FontAwesomeIcon icon={faAlignLeft} className={styles['open-question__space--style']} />
        </div>
        <textarea
          value={text}
          onChange={handleChange}
          rows={4}
          cols={50}
        />
        <div className={styles['open-question__space--length']}>
          {text.length}/{maxChars}
        </div>
      </div>
    </div>
  );
};

export default OpenQuestion;
