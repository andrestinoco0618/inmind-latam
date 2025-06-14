"use client";
import React, { useState, useRef, useEffect } from "react";
import styles from "./multipleOpenQuestions.module.css";

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

  const OpenQuestionsOptions = [
    "A00052", "A00094", "A00101", "A00165", "A00184", "A00336", 
    "A00444", "A00460", "A00483", "A00498", "A00505", "A00592", 
    "A00745", "A00753", "A00765", "A00772", "A00779", "A00884", 
    "A00894", "A00900", "A00903", "A00945", "A00967", "A01006", 
    "A01042", "A01063", "A01103", "A01142"
  ];
  const textareaRef = useRef<HTMLDivElement>(null);

  const handleChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    setText(event.target.value);
    onAnswerChange(selectedOptions, text);
  };

  const handleCheckboxChange = (optionId: string) => {
    console.log(process.env.NEXT_PUBLIC_OPTIONS_OPEN);
  
    if (OpenQuestionsOptions.includes(optionId)) {
      if (selectedOptions.includes(optionId)) {
        setSelectedOptions([]);
        setShowTextarea(false);
        setText("");
      } else {
        setSelectedOptions([optionId]);
        setShowTextarea(true);
      }
    } else {
      const updatedOptions = selectedOptions.includes(optionId)
        ? selectedOptions.filter((id) => id !== optionId) 
        : [
            ...selectedOptions.filter((id) => !OpenQuestionsOptions.includes(id)),
            optionId,
          ];
      setText("");
      setSelectedOptions(updatedOptions);
      setShowTextarea(false);
    }
  
    onAnswerChange(selectedOptions, text);
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
              ></textarea>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default MultipleOpenQuestion;
