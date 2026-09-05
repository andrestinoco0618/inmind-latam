"use client";
import React, { useState, useRef, useEffect } from "react";
import styles from "./multipleOpenQuestions.module.css";

/**
 * @interface MultipleOpenQuestionProps
 * @description Props for the MultipleOpenQuestion component
 * @property {string} title - Question title
 * @property {string} subtitle - Question subtitle
 * @property {number} questionNumber - Question number in sequence
 * @property {Option[]} optionsAnswer - Available answer options
 * @property {Function} onAnswerChange - Callback for answer changes with selected options and text response
 */
interface MultipleOpenQuestionProps {
  title: string;
  subtitle: string;
  questionNumber: number;
  optionsAnswer: Option[];
  onAnswerChange: (selectedOptions: string[], textResponse: string) => void;
}

interface Option {
  idAlternative: string;
  text: string;
}

/**
 * @component MultipleOpenQuestion
 * @description Renders a question with multiple open-ended text inputs
 * @param {MultipleOpenQuestionProps} props - Component props
 * @returns {JSX.Element} Rendered multiple open question component
 */
const MultipleOpenQuestion = ({
  title,
  subtitle,
  questionNumber,
  optionsAnswer,
  onAnswerChange,
}: MultipleOpenQuestionProps) => {
  const [selectedOptions, setSelectedOptions] = useState<string[]>([]);
  const [text, setText] = useState("");
  const [showTextarea, setShowTextarea] = useState(false);

   useEffect(() => {
    setShowTextarea(false);
    setText("");
    setSelectedOptions([]);
   }, [questionNumber]);

  const OpenQuestionsOptions = [
    "A00052", "A00094", "A00101", "A00165", "A00184", "A00336", 
    "A00444", "A00460", "A00483", "A00498", "A00505", "A00592", 
    "A00745", "A00753", "A00765", "A00772", "A00779", "A00884", 
    "A00894", "A00900", "A00903", "A00945", "A00967", "A01006", 
    "A01042", "A01063", "A01103", "A01142", "A00573", "A01162"
  ];
  const textareaRef = useRef<HTMLDivElement>(null);

const handleChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
  const newText = event.target.value;
  setText(newText);
  onAnswerChange(selectedOptions, newText); 
};

  const handleCheckboxChange = (optionId: string) => {
    if (OpenQuestionsOptions.includes(optionId)) {
      if (selectedOptions.includes(optionId)) {
        setSelectedOptions([]);
        setShowTextarea(false);
        setText("");
      } else {
        setSelectedOptions([optionId]);
        setShowTextarea(true);
      }
    }else {
      setSelectedOptions((prevSelected) => {
        const newSelection = prevSelected.includes(optionId)
          ? prevSelected.filter((id) => id !== optionId)
          : [...prevSelected.filter((id) => !OpenQuestionsOptions.includes(id)), optionId];
  
        setShowTextarea(false);
        setText(""); 
        onAnswerChange(newSelection, ""); 
        return newSelection;
      });
    }
  };
  

  useEffect(() => {
    if (showTextarea && textareaRef.current) {
      textareaRef.current.scrollIntoView({ behavior: "smooth", block: "start" });
    }
  }, [showTextarea]);

  return (
    <div className="multipleo-question__container">
      <div className={styles["multipleo-question__title"]}>
        <span>{`${questionNumber}. ${title}`}</span>
      </div>
      <div className={styles["multipleo-question__subtitle"]}>
        <p>{subtitle}</p>
      </div>
      <div className={styles["multipleo-question__choices"]}>
        <div className={styles["multipleo-question__options"]}>
          {optionsAnswer.map((option) => (
            <label
              key={option.idAlternative}
              className={`${styles["multipleo-question__options--style"]} ${selectedOptions.includes(option.idAlternative) ? styles["checked"] : ""
                }`}
            >
              <input
                type="checkbox"
                value={option.idAlternative}
                checked={selectedOptions.includes(option.idAlternative)}
                onChange={() => handleCheckboxChange(option.idAlternative)}
              />
              <span
                className={`${styles["multipleo"]} ${selectedOptions.includes(option.idAlternative) ? styles["span-checked"] : ""
                  }`}
              >
                <p className={styles["multipleo-question__options-text"]}>{option.text}</p>
              </span>
            </label>
          ))}
          {showTextarea && (
            <div
              className={`${styles["multipleo-question__space"]} ${showTextarea ? styles["show"] : styles["hide"]
                }`}
              ref={textareaRef}
            >
              <span>
                <strong>Otro</strong>
              </span>
              <textarea
                value={text}
                rows={4}
                cols={50}
                onChange={handleChange}
                role="textbox"
              ></textarea>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default MultipleOpenQuestion;
