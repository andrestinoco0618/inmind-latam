"use client";
import React, { useState, useEffect } from 'react';
import styles from './psychologicalForm.module.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCloudUpload, faArrowUp, faArrowDown } from '@fortawesome/free-solid-svg-icons';
import { BUTTON_FINAL, BUTTON_NEXT } from '@/utils/Constants';
import { AnswerData } from '@/api/questionService';
import InformativeQuestion from '@/components/InformativeQuestions/InformativeQuestion';
import OpenQuestion from '@/components/OpenQuestions/OpenQuestions';
import SimpleCloseQuestion from '@/components/SimpleCloseQuestion/SimpleCloseQuestion';
import MultipleCloseQuestion from '@/components/MultipleCloseQuestions/MultipleCloseQuestions';
import MultipleOpenQuestion from '@/components/MultipleOpenQuestions/MultipleOpenQuestions';
import SelectQuestion from '@/components/SelectQuestions/SelectQuestions'; 
import useFetchQuestions from '@/hooks/useFetchQuestion';
import useSendAnswer from '@/hooks/useSendAnswer';
import MandatoryAlerts from '@/components/MandatoryAlert/MandatoryAlert';
import { text } from 'stream/consumers';

const PsychologicalForm: React.FC = () => {
  const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
  const [transitionDirection, setTransitionDirection] = useState<'up' | 'down'>('down');
  const [answerData, setAnswerData] = useState<AnswerData>();
  const [firstQuestion, setFirstQuestion] = useState(true);
  const [showMandatoryAlert, setShowMandatoryAlert] = useState(false); 
  const [showNextButton, setShowNextButton] = useState(false); 
  const { questions, loading } = useFetchQuestions(); 
  const { submitAnswer, newQuestion } = useSendAnswer();
  const [selectedAnswers, setSelectedAnswers] = useState<string[]>([]);  
  const [textAnswer, setTextAnswer] = useState("");  
  const [finalButton, setFinalButton] = useState(false);  

useEffect(()=>{
if(newQuestion?.idQuestion === process.env.NEXT_PUBLIC_FINAL_QUESTION_ONE || newQuestion?.idQuestion === process.env.NEXT_PUBLIC_FINAL_QUESTION_TWO){
  setFinalButton(true);
}else{
  setFinalButton(false);
}
},[newQuestion])
  const handleAnswerChange = (selectedOptions: string[], textOptions:string) => {
    setSelectedAnswers(selectedOptions); 
    setTextAnswer(textOptions);
    if (selectedOptions.length > 0) {
      setShowMandatoryAlert(false);
    }
  };

  const renderQuestion = (question: any) => {
      const [finalTitle, finalSubtitle] = separateTitleAndSub(question.title);
      switch (question.idQuestionType) {
        case process.env.NEXT_PUBLIC_INFORMATIVE_CODE:
          return <InformativeQuestion key="informative" boldInformation={finalSubtitle} greeting={""} normalInformation={finalTitle} />;
          case process.env.NEXT_PUBLIC_ACCEPTANCE_CODE:
            return <InformativeQuestion key="informative" boldInformation={finalSubtitle} greeting={""} normalInformation={finalTitle} />;
          case process.env.NEXT_PUBLIC_OPEN_CODE:
          return <OpenQuestion key="open" questionNumber={question.positionQuestion} textLength={Number(process.env.NEXT_PUBLIC_TEXT_LENGTH)} title={finalTitle} onAnswerChange={(textResponse) => setTextAnswer(textResponse)}/>;
        case process.env.NEXT_PUBLIC_MULTIPLE_OPEN_CODE:
          return <MultipleOpenQuestion key="multipleOpen" title={finalTitle} subtitle={finalSubtitle} questionNumber={question.positionQuestion} optionsAnswer={question.optionsAnswer} onAnswerChange={(selectedOption) => handleAnswerChange(selectedOption,textAnswer)}/>;
        case process.env.NEXT_PUBLIC_SIMPLE_CLOSE_CODE:
          return <SimpleCloseQuestion key="simpleClose" title={finalTitle} subtitle={finalSubtitle} questionNumber={question.positionQuestion} optionsAnswer={question.optionsAnswer}  onAnswerChange={(selectedOption) => handleAnswerChange(selectedOption,textAnswer)}/>;
        case process.env.NEXT_PUBLIC_MULTIPLE_CLOSE_CODE:
          return <MultipleCloseQuestion key="multipleClose" title={finalTitle} subtitle={finalSubtitle} questionNumber={question.positionQuestion} optionsAnswer={question.optionsAnswer} onAnswerChange={(selectedOption) => handleAnswerChange(selectedOption,textAnswer)}/>;
        case process.env.NEXT_PUBLIC_SELECT_CODE:
          return <SelectQuestion key="select" title={finalTitle} subtitle={finalSubtitle} questionNumber={question.positionQuestion} optionsAnswer={question.optionsAnswer} onAnswerChange={(selectedOption) => handleAnswerChange(selectedOption,textAnswer)}/>;
        case process.env.NEXT_PUBLIC_SIMPLE_OPEN_CODE:
          return <SimpleCloseQuestion key="simpleClose" title={finalTitle} subtitle={finalSubtitle} questionNumber={question.positionQuestion} optionsAnswer={question.optionsAnswer} onAnswerChange={(selectedOption) => handleAnswerChange(selectedOption,textAnswer)}/>;
        default:
          return null;
      }
  };

  const separateTitleAndSub=(totalText:string)=>{
    const [mainTitle, subtitleText] = totalText.split('*');
    return [mainTitle?.trim() || '', subtitleText?.trim() || ''];
  }
  
  const handleNextClick = (idQuestionType:string, idQuestionnaire: string, idQuestion: string, responseAnswer: string[], openQuestion: string) => {
    setFirstQuestion(false);
    if(idQuestionType !== "" && idQuestionType !== process.env.NEXT_PUBLIC_ACCEPTANCE_CODE && idQuestionType !== process.env.NEXT_PUBLIC_INFORMATIVE_CODE){
      if(idQuestionType === process.env.NEXT_PUBLIC_OPEN_CODE){
        if(textAnswer === ""){
          setShowMandatoryAlert(true); 
        }else{
          setShowMandatoryAlert(false);
          submitAnswer({
            idQuestionnaire: idQuestionnaire,
            idQuestion: idQuestion,
            responseAnswer: responseAnswer,
            openQuestion: openQuestion,
          });
        }
      }else if(idQuestionType === process.env.NEXT_PUBLIC_SELECT_CODE){
        setShowMandatoryAlert(false);
        submitAnswer({
          idQuestionnaire: idQuestionnaire,
          idQuestion: idQuestion,
          responseAnswer: responseAnswer,
          openQuestion: openQuestion,
        });
      }else{
        if(selectedAnswers.length === 0){
          setShowMandatoryAlert(true);   
        }else{
          setShowMandatoryAlert(false);
            submitAnswer({
              idQuestionnaire: idQuestionnaire,
              idQuestion: idQuestion,
              responseAnswer: responseAnswer,
              openQuestion: openQuestion,
            });
        }
      }
      }
      else{
      submitAnswer({
        idQuestionnaire: idQuestionnaire,
        idQuestion: idQuestion,
        responseAnswer: responseAnswer,
        openQuestion: openQuestion,
      });
    }
    setSelectedAnswers([]);
    setTextAnswer("");
  };

  return (
    <div className={styles.container__form}>
      <div className={`${styles['container__form-info']} ${styles[transitionDirection]}`}>
        <div className={styles.container__icon}>
          <span className={styles['container__icon--color']}>
            <a>
              <FontAwesomeIcon className={styles['container__icon--size']} icon={faCloudUpload} />
            </a>
          </span>
        </div>
        <div className={styles.container__text}>
          {firstQuestion?questions?renderQuestion(questions):null:
          newQuestion?renderQuestion(newQuestion):null}
        </div>
        {showMandatoryAlert && <MandatoryAlerts />}
        {showNextButton && 
        <div className={styles['container__button-next']}>
          <span><i>Presione <strong>ENTER</strong></i></span>
          <button
            className={styles.button__next}
            onClick={() => handleNextClick(
              (firstQuestion ? questions?.idQuestionType : newQuestion?.idQuestionType) ?? "",
              (firstQuestion ? questions?.idQuestionnaire : newQuestion?.idQuestionnaire) ?? "",
              (firstQuestion ? questions?.idQuestion : newQuestion?.idQuestion) ?? "",
              selectedAnswers,
              textAnswer 
            )}
          >
            <strong>{BUTTON_NEXT}</strong>
          </button>
        </div>
      }
      {finalButton &&
      <div className={styles['container__button-final']}>
          <button
            className={styles.button__final}
            onClick={() => handleNextClick(
              (firstQuestion ? questions?.idQuestionType : newQuestion?.idQuestionType) ?? "",
              (firstQuestion ? questions?.idQuestionnaire : newQuestion?.idQuestionnaire) ?? "",
              (firstQuestion ? questions?.idQuestion : newQuestion?.idQuestion) ?? "",
              selectedAnswers,
              textAnswer 
            )}
          >
            <strong>{BUTTON_FINAL}</strong>
          </button>
          <span><i>Presione <strong>ENTER</strong></i></span>
        </div>}
      {!finalButton &&
      <div className={styles.container__load}>
      <div className={styles['container__arrow']}>
        <div className={styles['container__arrow-controls']}>
          <div className={styles['container__arrow-controls--up']} >
            <FontAwesomeIcon icon={faArrowUp} />
          </div>
          <div className={styles['container__arrow-controls--down']} onClick={() => handleNextClick(
            (firstQuestion ? questions?.idQuestionType : newQuestion?.idQuestionType) ?? "",
            (firstQuestion ? questions?.idQuestionnaire : newQuestion?.idQuestionnaire) ?? "",
            (firstQuestion ? questions?.idQuestion : newQuestion?.idQuestion) ?? "",
            selectedAnswers,
            textAnswer
        )}>
            <FontAwesomeIcon icon={faArrowDown} />
          </div>
        </div>
      </div>
    </div>
      }
        
      </div>
    </div>
  );
};

export default PsychologicalForm;
