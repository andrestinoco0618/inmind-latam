"use client";
import React, { useState } from 'react';
import styles from './openQuestions.module.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faAlignLeft} from '@fortawesome/free-solid-svg-icons';

interface OpenQuestionProps {
  title: string;
  questionNumber: number;
  textLength: number; 
}

const OpenQuestion = ({ title, questionNumber, textLength }: OpenQuestionProps) => {
  const [text, setText] = useState('');
  const maxChars = textLength;

  const handleChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    const inputText = event.target.value;
    if (inputText.length <= maxChars) {
      setText(inputText);
    }
  };

  return (
    <div className='open-question__container'>
      <div className={styles['open-question__title']}>
        <span>{`${questionNumber}. ${title}`}</span>
      </div>
      <div className={styles['open-question__space']}>
        <div className={styles['open-question__icon']}>
        <FontAwesomeIcon icon={faAlignLeft} className={styles['open-question__space--style']}></FontAwesomeIcon>
        </div>
        <textarea
          value={text}
          onChange={handleChange}
          rows={4}
          cols={50}
        >
        </textarea>
        <div className={styles['open-question__space--length']}>
          {text.length}/{maxChars}
        </div>
      </div>
    </div>
  );
};

export default OpenQuestion;
