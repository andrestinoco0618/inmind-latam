import React from 'react';
import styles from './resultsPsychologists.module.css';
import { listPsychologist } from '@/api/QuestionsClientService/questionService';
import { savePsychologist } from '@/api/SavePsychologistClient/savePsychologistService';
import defaultImage from '../../../../public/img/not-found.jpg';

/**
 * @interface ModalProps
 * @description Props for the ResultsPsychologistsModal component
 * @property {boolean} isOpen - Controls modal visibility
 * @property {listPsychologist[]} psychologists - List of recommended psychologists
 * @property {string} idQuestionnaire - ID of the current questionnaire
 */
interface ModalProps {
  isOpen: boolean;
  psychologists: listPsychologist[];
  idQuestionnaire: string;
}

/**
 * @component ResultsPsychologistsModal
 * @description Modal component that displays a grid of recommended psychologists with their profiles
 * @param {ModalProps} props - Component props
 * @returns {JSX.Element | null} Rendered modal component or null if not open
 */
const Modal: React.FC<ModalProps> = ({ isOpen, psychologists, idQuestionnaire }) => {
  if (!isOpen) return null;

  /**
   * @function handleSelect
   * @description Handles the selection of a psychologist, saves the selection and opens their profile
   * @param {string} questionnaireId - ID of the questionnaire
   * @param {string} idPsychologist - ID of the selected psychologist
   * @param {string} linkProfile - URL to the psychologist's profile
   */
  const handleSelect = async (questionnaireId: string, idPsychologist: string, linkProfile: string) => {
    try {
      await savePsychologist(questionnaireId, idPsychologist);
      console.log('Save psychologist succesfully');
      window.open(linkProfile, '_blank');
    } catch (error) {
      console.error('Error saving the psychologist', error);
    }
  };

  return (
    <div className={styles.backdrop}>
      <div className={styles.modal}>
        <h2 className={styles.title}>Estos son los psicólogos ideales para ti</h2>
        <div className={styles.description}>
          <p>
            Hemos analizado cuidadosamente tus respuestas y con base en tu perfil emocional, te presentamos una selección de psicólogos que se ajustan a lo que realmente necesitas. Elige el que más te inspire confianza y empieza tu camino hacia el bienestar.
          </p>
        </div>

        <div className={styles.cardGrid}>
          {psychologists.map((psychologist) => (
            <div className={styles.card} key={psychologist.idPsychologist}>
              <div className={styles.cardContent}>
                <h3 className={styles.name}>{psychologist.name}</h3>
                <img
                  src={psychologist.image === 'PENDIENTE' ? defaultImage.src : psychologist.image}
                  alt={`Foto de ${psychologist.name}`}
                  className={styles.profileImage}
                />
                <button
                  onClick={() =>
                    handleSelect(idQuestionnaire, psychologist.idPsychologist, psychologist.linkProfile)
                  }
                  className={styles.linkedinButton}
                >
                  Ver Perfil
                </button>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default Modal;
