"use client";
import React, { useState, useEffect } from "react";
import styles from "./selectQuestions.module.css";

/**
 * @interface SelectQuestionProps
 * @description Props for the SelectQuestion component
 * @property {string} title - Question title
 * @property {string} subtitle - Question subtitle
 * @property {number} questionNumber - Question number in sequence
 * @property {Option[]} optionsAnswer - Available answer options
 * @property {Function} onAnswerChange - Callback for answer changes
 */
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

/**
 * @component SelectQuestion
 * @description Renders a question with a dropdown select input
 * @param {SelectQuestionProps} props - Component props
 * @returns {JSX.Element} Rendered select question component
 */
const SelectQuestion = ({
  title,
  subtitle,
  questionNumber,
  optionsAnswer,
  onAnswerChange,
}: SelectQuestionProps) => {
  const [inputValue, setInputValue] = useState("");
  const [filteredOptions, setFilteredOptions] = useState<Option[]>(optionsAnswer);
  const [showOptions, setShowOptions] = useState(false);

  useEffect(() => {
    const lowerInput = inputValue.toLowerCase();
    const filtered = optionsAnswer.filter((option) =>
      option.text.toLowerCase().includes(lowerInput)
    );
    setFilteredOptions(filtered);
  }, [inputValue, optionsAnswer]);

  const handleOptionSelect = (option: Option) => {
    setInputValue(option.text);
    setShowOptions(false);
    onAnswerChange([option.idAlternative]);
  };

  return (
    <div className={styles["select-question__container"]}>
      <div className={styles["select-question__title"]}>
        <span>{`${questionNumber}. ${title}`}</span>
      </div>
      <div className={styles["select-question__subtitle"]}>
        <p>{subtitle}</p>
      </div>

      <div className={styles["select-question__choices"]} style={{ position: "relative" }}>
        <input
          type="text"
          value={inputValue}
          onChange={(e) => {
            setInputValue(e.target.value);
            setShowOptions(true);
          }}
          onFocus={() => {
            setShowOptions(true);
            setFilteredOptions(optionsAnswer);
          }}
          onBlur={() => setTimeout(() => setShowOptions(false), 150)}
          placeholder="Selecciona una opción"
          className={styles["select-question__input"]}
        />
        <span className={styles["select-question__arrow"]}>▼</span>

        {showOptions && filteredOptions.length > 0 && (
          <ul className={styles["select-question__dropdown"]}>
            {filteredOptions.map((option) => (
              <li
                key={option.idAlternative}
                onClick={() => handleOptionSelect(option)}
                className={styles["select-question__dropdown-option"]}
              >
                {option.text}
              </li>
            ))}
          </ul>
        )}
      </div>
    </div>
  );
};

export default SelectQuestion;
