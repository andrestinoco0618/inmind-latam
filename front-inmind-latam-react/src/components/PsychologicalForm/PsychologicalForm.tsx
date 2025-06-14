"use client";
import React, { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import styles from './psychologicalForm.module.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCloudUpload, faArrowUp, faArrowDown } from '@fortawesome/free-solid-svg-icons';
import { BUTTON_FINAL, BUTTON_NEXT } from '@/utils/Constants';
import InformativeQuestion from '@/components/InformativeQuestions/InformativeQuestion';
import OpenQuestion from '@/components/OpenQuestions/OpenQuestions';
import SimpleCloseQuestion from '@/components/SimpleCloseQuestion/SimpleCloseQuestion';
import MultipleCloseQuestion from '@/components/MultipleCloseQuestions/MultipleCloseQuestions';
import MultipleOpenQuestion from '@/components/MultipleOpenQuestions/MultipleOpenQuestions';
import SelectQuestion from '@/components/SelectQuestions/SelectQuestions'; 
import useSendAnswer from '@/hooks/useSendAnswer';
import MandatoryAlerts from '@/components/MandatoryAlert/MandatoryAlert';
import Modal from '@/components/Modals/ResultsPsychologists/ResultsPsychologistsModal';
import { fetchInitialQuestions, GetQuestions, listPsychologist } from "@/api/QuestionsClientService/questionService";
import TermsAndConditionsAcceptance from '../TermsConditionsAcceptance/TermsConditionsAcceptance';
import TermsAndConditionsModal from '../Modals/TermsConditions/TermsAndConditionsModal';

/**
 * @component PsychologicalForm
 * @description Main form component for psychological questionnaire
 * @returns {JSX.Element} Rendered psychological form
 */
const PsychologicalForm: React.FC = () => {
  const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
  const [transitionDirection, setTransitionDirection] = useState<'up' | 'down'>('down');
  const [firstQuestion, setFirstQuestion] = useState(true);
  const [showMandatoryAlert, setShowMandatoryAlert] = useState(false); 
  const [showIncorrectFormat, setshowIncorrectFormat] = useState(false); 
  const [showNextButton, setShowNextButton] = useState(false); 
  const { submitAnswer, newQuestion, setNewQuestion} = useSendAnswer();
  const [selectedAnswers, setSelectedAnswers] = useState<string[]>([]);  
  const [textAnswer, setTextAnswer] = useState("");  
  const [finalButton, setFinalButton] = useState(false);  
  const [showForm, setShowForm] = useState(false);
  const [questions, setQuestions] = useState<GetQuestions | null>(null);
  const [beforeQuestion, setBeforeQuestion] = useState(false);
  const [questionHistory, setQuestionHistory] = useState<any[]>([]);
  const [showInitialModal, setShowInitialModal] = useState(false);
  const [showTermsModal, setshowTermsModal] = useState(false);
  const [termsAccepted, setTermsAccepted] = useState(false);
  const [psychologistList, setPsychologistList] = useState<listPsychologist[]>([]);
  const [idQuestionnairePsychologist, setIdQuestionnairePsychologist] = useState("");
  const [isGoingBack, setIsGoingBack] = useState(false);
  const router = useRouter();

  const phoneNumber = '51981465928'; 
  const msg = '¡Hola! Quisiera empezar a conectar con un especialista';
  const encodedMsg = encodeURIComponent(msg);
  const whatsappURL = `https://api.whatsapp.com/send/?phone=${phoneNumber}&text=${encodedMsg}&type=phone_number&app_absent=0`;

  const EmailQuestionsOptions = [
    "P00040","P00117"
  ];

  useEffect(() => {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }, [newQuestion, questions]);

  useEffect(() => {
    if (!newQuestion || isGoingBack) {
      setIsGoingBack(false); // reset
      return;
    }
  
    if (newQuestion.status === "matching") {
      setShowInitialModal(true);
      setPsychologistList(newQuestion.listPsychologist);
      setIdQuestionnairePsychologist(newQuestion.idQuestionnaire);

    } else {
      setShowInitialModal(false);
    }

    if(newQuestion.status === "redirecting"){
       window.location.href = newQuestion.redirect;
    }
  
    setQuestionHistory(prev => [...prev, newQuestion]);
    setCurrentQuestionIndex(prev => prev + 1);
  }, [newQuestion]);


  const handleAnswerChange = (selectedOptions: string[], textOptions: string) => {
    setSelectedAnswers(selectedOptions); 
    setTextAnswer(textOptions);
    if (selectedOptions.length > 0) {
      setShowMandatoryAlert(false);
    }
  };

  const renderQuestion = (question: any) => {
    if(question.status != "matching" && question.status != "redirecting"){
      const [finalTitle, finalSubtitle] = separateTitleAndSub(question.title);
      switch (question.idQuestionType) {
        case process.env.NEXT_PUBLIC_INFORMATIVE_CODE:
          return <InformativeQuestion key="informative" boldInformation={finalSubtitle} greeting={""} normalInformation={finalTitle} questionCode={question.idQuestion}/>;
          case process.env.NEXT_PUBLIC_ACCEPTANCE_CODE:
            return (
              <TermsAndConditionsAcceptance 
                onAnswerChange={(selectedOption) => {
                  handleAnswerChange(selectedOption, textAnswer);
                  setTermsAccepted(selectedOption.includes("accepted"));
                }} 
                onTermsClick={() => setshowTermsModal(true)} 
              />
            );case process.env.NEXT_PUBLIC_OPEN_CODE:
          return <OpenQuestion key="open" idQuestion={question.idQuestion} questionNumber={question.positionQuestion} textLength={Number(process.env.NEXT_PUBLIC_TEXT_LENGTH)} title={finalTitle} onAnswerChange={(textResponse) => setTextAnswer(textResponse)} />;
        case process.env.NEXT_PUBLIC_MULTIPLE_OPEN_CODE:
          return <MultipleOpenQuestion key="multipleOpen" title={finalTitle} subtitle={finalSubtitle} questionNumber={question.positionQuestion} optionsAnswer={question.optionsAnswer} onAnswerChange={(selectedOption, text) => handleAnswerChange(selectedOption, text)} />;
        case process.env.NEXT_PUBLIC_SIMPLE_CLOSE_CODE:
          return <SimpleCloseQuestion key="simpleClose" title={finalTitle} subtitle={finalSubtitle} questionNumber={question.positionQuestion} optionsAnswer={question.optionsAnswer} onAnswerChange={(selectedOption) => handleAnswerChange(selectedOption, textAnswer)} />;
        case process.env.NEXT_PUBLIC_MULTIPLE_CLOSE_CODE:
          return <MultipleCloseQuestion key="multipleClose" title={finalTitle} subtitle={finalSubtitle} questionNumber={question.positionQuestion} optionsAnswer={question.optionsAnswer} onAnswerChange={(selectedOption) => handleAnswerChange(selectedOption, textAnswer)} questionId={question.idQuestion}/>;
        case process.env.NEXT_PUBLIC_SELECT_CODE:
          return <SelectQuestion key={`select-${question.idQuestion}`} title={finalTitle} subtitle={finalSubtitle} questionNumber={question.positionQuestion} optionsAnswer={question.optionsAnswer} onAnswerChange={(selectedOption) => handleAnswerChange(selectedOption, textAnswer)} />;
        case process.env.NEXT_PUBLIC_SIMPLE_OPEN_CODE:
          return <SimpleCloseQuestion key="simpleClose" title={finalTitle} subtitle={finalSubtitle} questionNumber={question.positionQuestion} optionsAnswer={question.optionsAnswer} onAnswerChange={(selectedOption) => handleAnswerChange(selectedOption, textAnswer)} />;
        default:
          return null;
      }
    }
  };

  const separateTitleAndSub = (text: string): [string, string] => {
    const i = text.indexOf('*');
    return i === -1 ? [text, ''] : [text.slice(0, i), text.slice(i)];
  };

  const handleButtonClickFirst = async (profileType: string) => {
    try {
      const response = await fetchInitialQuestions(profileType);
      setQuestions(response);
      setShowForm(true);
    } catch (error) {
      console.error("Error al obtener preguntas iniciales:", error);
    }
  };

const isEmailValid = (email: string) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);

const shouldValidateEmail = (idQuestion: string) => EmailQuestionsOptions.includes(idQuestion);

const handleNextClick = (
  idQuestionType: string,
  idQuestionnaire: string,
  idQuestion: string,
  responseAnswer: string[],
  openQuestion: string
) => {
  setFirstQuestion(false);
  const openCode = process.env.NEXT_PUBLIC_OPEN_CODE;
  const acceptanceCode = process.env.NEXT_PUBLIC_ACCEPTANCE_CODE;
  const informativeCode = process.env.NEXT_PUBLIC_INFORMATIVE_CODE;
  const redirectQuestion = ["A00875", "A00854"].some(restrictionAnswer => responseAnswer.includes(restrictionAnswer));

  if(redirectQuestion){
    window.location.href = whatsappURL;
    return;
  }

  switch (idQuestionType) {
    case openCode: {
      const isOptional = idQuestion === "P00126" || idQuestion === "P00054";
      const isEmpty = textAnswer.trim() === "";

      if (!isOptional && isEmpty) {
        setShowMandatoryAlert(true);
        return;
      }

      if (shouldValidateEmail(idQuestion) && !isEmailValid(textAnswer)) {
        setshowIncorrectFormat(true);
        return;
      }

      setShowMandatoryAlert(false);
      setshowIncorrectFormat(false);
      break;
    }

    case acceptanceCode: {
      if (!termsAccepted) {
        setShowMandatoryAlert(true);
        return;
      }
      setShowMandatoryAlert(false);
      break;
    }

    case informativeCode: {
      // No requiere validaciones especiales
      break;
    }

    default: {
      if (selectedAnswers.length === 0) {
        setShowMandatoryAlert(true);
        return;
      }
      setShowMandatoryAlert(false);
    }
  }

  submitAnswer({
    idQuestionnaire,
    idQuestion,
    responseAnswer,
    openQuestion,
  });

  // Reset de estados
  setSelectedAnswers([]);
  setTextAnswer("");
  setTermsAccepted(false);
  setshowIncorrectFormat(false);
  
};

const handleBackClick = () => {
  if (currentQuestionIndex > 1) {
    const previousIndex = currentQuestionIndex - 2;
    setIsGoingBack(true);
    setCurrentQuestionIndex(currentQuestionIndex - 1);
    setNewQuestion(questionHistory[previousIndex]);
    setQuestionHistory(prev => prev.slice(0, previousIndex + 1));
    setShowMandatoryAlert(false);
  } else if (currentQuestionIndex === 1) {
    setFirstQuestion(true);
    setCurrentQuestionIndex(0);
    setNewQuestion(undefined);
    setShowMandatoryAlert(false);
  }
};

  return (
    <div>
      <div>
      {
        showInitialModal && (
          <Modal isOpen={showInitialModal} psychologists={psychologistList} idQuestionnaire={idQuestionnairePsychologist}
          ></Modal>
        )
      }
      {
        showTermsModal && (
          <TermsAndConditionsModal isOpen={showTermsModal} onClose={()=>setshowTermsModal(false)} onAccept={()=>setshowTermsModal(false)}
          ></TermsAndConditionsModal>
        )
      }
      </div>
      
      {!showForm ? (
        <section className={styles.section__buttons}>
          <h1>
            Mereces bienestar emocional.
          </h1>
          <h2>
            En menos de 05 minutos, encuentra un Psicoterapeuta especializado en lo que estás pasando y recibe tus sesiones presenciales o virtuales.
          </h2>
          <div className={styles['section__buttons--next']}>
            <button onClick={() => handleButtonClickFirst("PF001")}>Para mí</button>
            <button onClick={() => handleButtonClickFirst("PF002")}>En pareja</button>
            <button onClick={() => handleButtonClickFirst("PF003")}>En familia</button>
            <button onClick={() => handleButtonClickFirst("PF004")}>Para mi hijo/a</button>
          </div>
        </section>
      ) : (
        <div className={styles.container__form}>
          <div className={`${styles['container__form-info']} ${styles[transitionDirection]}`}>
            <div className={styles.container__text}>
              {firstQuestion ? questions ? renderQuestion(questions) : null :
                newQuestion ? renderQuestion(newQuestion) : null}
            </div>
            {showMandatoryAlert && <MandatoryAlerts text='Campo obligatorio'/>}
            {showIncorrectFormat && <MandatoryAlerts text='Formato incorrecto'/>}
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
            {!finalButton &&
              <div className={styles.container__load}>
                <div className={styles['container__arrow']}>
                  <div className={styles['container__arrow-controls']}>
                    <div className={styles['container__arrow-controls--up']}onClick={handleBackClick}>
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
      )}
    </div>
  );
};

export default PsychologicalForm;
