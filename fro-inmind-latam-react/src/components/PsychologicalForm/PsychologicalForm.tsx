"use client";
import React, { useState } from 'react';
import styles from './psychologicalForm.module.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCloudUpload, faArrowUp, faArrowDown } from '@fortawesome/free-solid-svg-icons';
import { HELLO_INFORMATION, ALERT_INFORMATION, GREETING, BUTTON_NEXT } from '@/utils/Constants';
import InformativeQuestion from '@/components/InformativeQuestions/InformativeQuestion';
import OpenQuestion from '@/components/OpenQuestions/OpenQuestions';
import SimpleCloseQuestion from '@/components/SimpleCloseQuestion/SimpleCloseQuestion';
import MultipleCloseQuestion from '@/components/MultipleCloseQuestions/MultipleCloseQuestions';
import MultipleOpenQuestion from '@/components/MultipleOpenQuestions/MultipleOpenQuestions';
import SelectQuestion from '@/components/SelectQuestions/SelectQuestions'; 

const PsychologicalForm: React.FC = () => {
  const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
  const [transitionDirection, setTransitionDirection] = useState<'up' | 'down'>('down');

  const questions = [
    
    <OpenQuestion key="open" questionNumber={1} textLength={500} title='¿Qué lo ha motivado a buscar asistencia psicológica para el o la menor?*' />,
    <SimpleCloseQuestion key="simpleClose" title='¿Deseas el servicio para ti o para otra persona?' subtitle={ALERT_INFORMATION} questionNumber={2} optionsAnswer={['Servicio personal', 'Servicio para otra persona']} />,
    <MultipleCloseQuestion key="multipleClose" title='¿Te gustaría que tu especialista tenga apertura en alguno de estos temas?' subtitle={ALERT_INFORMATION} questionNumber={3} optionsAnswer={['LGTBQ', 'Perspectiva Feminista', 'No tengo preferencias']} />,
    <MultipleOpenQuestion key="multipleOpen" title='¿Tienes algún tipo de diagnóstico?' subtitle={ALERT_INFORMATION} questionNumber={4} optionsAnswer={['Depresión','Trastorno de Ansiedad','Trastorno Bipolar','Trastorno Límite de la personalidad','TOC','Ninguno de los anteriores']}/>,
    <OpenQuestion key="open" questionNumber={5} textLength={500} title='¿Qué lo ha motivado a buscar asistencia psicológica para el o la menor?*' />,
    <SimpleCloseQuestion key="simpleClose" title='¿Deseas el servicio para ti o para otra persona?' subtitle={ALERT_INFORMATION} questionNumber={6} optionsAnswer={['Servicio personal', 'Servicio para otra persona']} />,
    <MultipleCloseQuestion key="multipleClose" title='¿Te gustaría que tu especialista tenga apertura en alguno de estos temas?' subtitle={ALERT_INFORMATION} questionNumber={7} optionsAnswer={['LGTBQ', 'Perspectiva Feminista', 'No tengo preferencias']}/>,
    <SelectQuestion key="multipleClose" title='¿Te gustaría que tu especialista tenga apertura en alguno de estos temas?' subtitle={ALERT_INFORMATION} questionNumber={8} optionsAnswer={['LGTBQ', 'Perspectiva Feminista', 'No tengo preferencias']}/>
  ];

  const handleArrowClick = (direction: 'up' | 'down') => {
    setTransitionDirection(direction);
    setCurrentQuestionIndex(prevIndex => {
      const newIndex = direction === 'up'
        ? Math.max(prevIndex - 1, 0)
        : Math.min(prevIndex + 1, questions.length);
      return newIndex;
    });
  };

  const handleNextClick = () => {
    setTransitionDirection('down');
    setCurrentQuestionIndex(prevIndex => Math.min(prevIndex + 1, questions.length));
  };
 
  return (
    <>
      <div className={styles.container__form}>
        <div className={`${styles['container__form-info']} ${styles[transitionDirection]}`}>
          <div className={styles.container__icon}>
            <span className={styles['container__icon--color']}>
              <a>
                <FontAwesomeIcon className={styles['container__icon--size']} icon={faCloudUpload}></FontAwesomeIcon>
              </a>
            </span>
          </div>
          <div className={styles.container__text}>
            {currentQuestionIndex === 0 && (
              <InformativeQuestion key="informative" boldInformation={ALERT_INFORMATION} greeting={GREETING} normalInformation={HELLO_INFORMATION} />
            )}
            {currentQuestionIndex > 0 && questions[currentQuestionIndex - 1]}
          </div>
          <div className={styles['container__button-next']}>
            <span><i>Presione <strong>ENTER</strong></i></span>
            <button className={styles.button__next} onClick={handleNextClick}><strong>{BUTTON_NEXT}</strong></button>
          </div>
       
      
      <div className={styles.container__load}>
        <div className={styles['container__arrow']}>
          <div className={styles['container__arrow-controls']}>
            <div className={styles['container__arrow-controls--up']} onClick={() => handleArrowClick('up')}>
              <FontAwesomeIcon icon={faArrowUp} />
            </div>
            <div className={styles['container__arrow-controls--down']} onClick={() => handleArrowClick('down')}>
              <FontAwesomeIcon icon={faArrowDown} />
            </div>
          </div>
        </div>
        </div>
      </div>
      </div>
    </>
  );
};

export default PsychologicalForm;
