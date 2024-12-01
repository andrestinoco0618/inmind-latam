"use client";
import React, { useState, useRef, useEffect } from 'react';
import styles from './multipleOpenQuestions.module.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faAlignLeft } from '@fortawesome/free-solid-svg-icons';

interface MultipleOpenQuestionProps {
  title: string;
  subtitle: string;
  questionNumber: number;
  optionsAnswer: string[];
}

const MultipleOpenQuestion = ({ title, subtitle, questionNumber, optionsAnswer }: MultipleOpenQuestionProps) => {
  const [selectedOptions, setSelectedOptions] = useState<string[]>([]);
  const [text, setText] = useState('');
  const [showTextarea, setShowTextarea] = useState(false); // Estado para mostrar/ocultar el textarea

  const textareaRef = useRef<HTMLDivElement>(null);

  const handleCheckboxChange = (option: string) => {
    const newSelectedOptions = selectedOptions.includes(option)
      ? selectedOptions.filter(item => item !== option)
      : [...selectedOptions, option];

    setSelectedOptions(newSelectedOptions);

    // Si 'Ninguno de los anteriores' está seleccionado, mostrar el textarea
    if (option === 'Ninguno de los anteriores') {
      setShowTextarea(true);
    } else {
      // Ocultar el textarea si 'Ninguno de los anteriores' no está seleccionado
      if (newSelectedOptions.length === 0 || !newSelectedOptions.includes('Ninguno de los anteriores')) {
        setShowTextarea(false);
      }
    }
  };

  const handleChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    setText(event.target.value);
  };

  useEffect(() => {
    if (showTextarea && textareaRef.current) {
      textareaRef.current.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
  }, [showTextarea]);

  return (
    <div className='multipleo-question__container'>
      <div className={styles['multipleo-question__title']}>
        <span>{`${questionNumber}. ${title}`}</span>
      </div>
      <div className={styles['multipleo-question__subtitle']}>
        <p>{subtitle}</p>
      </div>
      <div className={styles['multipleo-question__choices']}>
        <div className={styles['multipleo-question__options']}>
          {optionsAnswer.map((option, index) => (
            <label
              key={index}
              className={`${styles['multipleo-question__options--style']} ${selectedOptions.includes(option) ? styles['checked'] : ''}`}
            >
              <input
                type="checkbox"
                value={option}
                onChange={() => handleCheckboxChange(option)}
              />
              <span className={`${styles['multipleo']} ${selectedOptions.includes(option) ? styles['span-checked'] : ''}`}>
                <p className={styles['multipleo-question__options-text']}>{option}</p>
              </span>
            </label>
          ))}
          <div
            className={`${styles['multipleo-question__space']} ${showTextarea ? styles['show'] : styles['hide']}`}
            ref={textareaRef} 
          >
            <span><strong>Otro</strong></span>
            <textarea
              value={text}
              rows={4}
              cols={50}
              onChange={handleChange}
            >
            </textarea>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MultipleOpenQuestion;
