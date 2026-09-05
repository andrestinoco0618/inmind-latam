import React from 'react';
import styles from './termsAndConditionsModal.module.css';

/**
 * @interface ModalProps
 * @description Props for the TermsAndConditionsModal component
 * @property {boolean} isOpen - Controls modal visibility
 * @property {Function} onClose - Callback for closing the modal
 * @property {Function} onAccept - Callback for accepting terms
 */
interface ModalProps {
  isOpen: boolean;
  onClose: () => void;
  onAccept: () => void;
}

/**
 * @component TermsAndConditionsModal
 * @description Modal component that displays terms and conditions with accept/cancel options
 * @param {ModalProps} props - Component props
 * @returns {JSX.Element | null} Rendered modal component or null if not open
 */
const TermsAndConditionsModal: React.FC<ModalProps> = ({ isOpen, onClose, onAccept }) => {
  if (!isOpen) return null;

  return (
    <div className={styles.backdrop}>
      <div className={styles.modal}>
        <h2 className={styles.title}>Términos y Condiciones</h2>
        <div className={styles.scroll}>
          <p className={styles.alignText}><strong>Términos de Uso</strong></p>
          <p>
           IMPORTANTE: SI USTED ESTÁ PENSANDO EN EL SUICIDIO O SI ESTÁ CONSIDERANDO TOMAR ACCIONES QUE PUEDEN CAUSAR DAÑO A USTED O A OTROS Y/O SIENTE QUE USTED O CUALQUIER OTRA PERSONA PUEDE ESTAR EN ALGÚN PELIGRO, Y/O SI TIENE ALGUNA EMERGENCIA MÉDICA, DEBE LLAMAR INMEDIATAMENTE AL NÚMERO DE SERVICIO DE EMERGENCIA LOCAL DE SU PAÍS DE RESIDENCIA Y NOTIFICAR A LAS AUTORIDADES PERTINENTES. Los presentes Términos y Condiciones (en adelante, Términos y Condiciones) regulan el uso de la Plataforma. La Referencia a INMIND RUC 10749862632, en adelante, la Plataforma, INMIND). Lea estos Términos de uso detenidamente. Estos Términos rigen y se aplican a su acceso y uso de la plataforma. Al acceder o utilizar el Servicio, usted acepta cumplir y estar obligado por todos los términos y condiciones descritos. Si no está de acuerdo con todos estos términos y condiciones, no está autorizado a utilizar el Servicio. Por la presente, certifica que es (i) mayor de dieciocho (18) años, que tiene la capacidad legal de dar su consentimiento a la Terapia/Acompañamiento Psicológico o que su Padre/Tutor consienta la Terapia/Acompañamiento Psicológico, y (ii) físicamente ubicado o es residente del País que ha elegido como su residencia actual. Usted acepta proporcionar "Información de contacto" (su contacto personal y/o un pariente/familiar cercano).  Usted reconoce que su capacidad para acceder y utilizar el Servicio está condicionada a la veracidad de la información que proporciona con respecto a su edad, residencia e Información de contacto y que los Psicoterapeutas/Psicólogos a los que accede ("Psicoterapeuta(s)" y "Psicólogo(s)") confían en esta información para poder para interactuar con usted y brindarle los Servicios. Toda información brindada será privada y utilizada únicamente por el Psicólogo o Psicoterapeuta con el que desee llevar sus sesiones. En cuanto a la derivación, o cambio de terapeuta, puede darse por solicitud directa del cliente o por requerimiento del Psicólogo o Psicoterapeuta a cargo con previa confirmación del consultante donde su información será proporcionada al nuevo Psicólogo o Psicoterapeuta. 
          </p>
          <p className={styles.alignText}><strong>Contenido del sitio</strong></p>
          <p>
          Aparte de la orientación y el asesoramiento que recibe directamente de su Psicoterapeuta/Psicólogo autorizado, las otras fuentes educativas, gráficas, de investigación y otra información incidental en el Sitio, el Contenido, no deben considerarse consejos médicos. Siempre debe hablar con un profesional de la salud debidamente calificado para el diagnóstico y el tratamiento, incluida la información sobre qué medicamentos o tratamiento pueden ser apropiados para usted. Ninguno de los contenidos representa o garantiza que ningún medicamento o tratamiento en particular sea seguro, apropiado o efectivo para usted. INMIND no respalda ninguna prueba, medicamento, producto o procedimiento específico.
          </p>
          <p className={styles.alignText}><strong>Uso aceptable</strong></p>
          <p>
          Usted acepta no acceder o utilizar el Servicio de manera ilegal o para un propósito ilegal o ilegítimo o de cualquier manera, que contravenga estos términos. No debe publicar, usar, almacenar o transmitir (i) un mensaje o información con un nombre falso; (ii) información que es ilegal, calumniosa, difamatoria, obscena, fraudulenta, depredadora de menores, que acosa, amenaza u odia a cualquier persona; o (iii) información que infringe o viola cualquiera de los derechos de propiedad intelectual de otros o los derechos de privacidad o publicidad de otros. No debe intentar interrumpir el funcionamiento del Servicio por ningún método, incluido el uso de virus, troyanos, gusanos, bombas de tiempo, ataques de denegación de servicio, inundaciones o correo no deseado. No deberá utilizar el Servicio de ninguna manera que pueda dañar, deshabilitar o perjudicar el Servicio. No debe intentar obtener acceso no autorizado a cuentas de usuario o sistemas informáticos o redes, mediante piratería, extracción de contraseñas o cualquier otro medio. No deberá utilizar ningún robot u otro medio para acceder al Servicio para ningún propósito. Usted es el único responsable de la información o material que publique durante las citas. Usted reconoce y acepta que: Estos Términos son entre usted y INMIND, y que INMIND es el único responsable de proporcionar servicio de mantenimiento y soporte con respecto a las reclamaciones, pérdidas, responsabilidades, daños, costos o gastos de la aplicación atribuibles a cualquier incumplimiento de cualquier garantía, será responsabilidad exclusiva de INMIND. El Servicio puede cambiar de vez en cuando y/o INMIND puede dejar de proporcionar (de forma permanente o temporal) el Servicio (o las funciones dentro del Servicio), posiblemente sin previo aviso.
          </p>
          <p className={styles.alignText}><strong>Pago</strong></p>
          <p>
          Cualquier pago que realice por el uso de los Servicios se utiliza para compensar a INMIND por su desarrollo de software, gastos generales, servicios administrativos y otros costos/tarifas corporativas. Independientemente de cualquier pago realizado, INMIND no se considera su Psicoterapeuta/Psicólogo directo de servicios de Psicoterapia/Acompañamiento Psicológico, ya que esa es la función de su Psicoterapeuta/Psicólogo acreditado.
          </p>
          <p className={styles.alignText}><strong>Psicólogos y Psicoterapeutas</strong></p>
          <p>
          INMIND no emplea directamente a los Psicólogos y Psicoterapeutas vinculados a través del Servicio. INMIND creó una red de salud digital moderna de Psicólogos y Psicoterapeutas acreditados. INMIND solo trabaja con Psicólogos y Psicoterapeutas profesionales independientes, y previamente examinados mediante un proceso riguroso de evaluación.Para recibir la aprobación para ejercer como parte de la red de INMIND, se debe determinar que el Psicoterapeuta solicitante cumple con nuestras políticas y procedimientos. Este proceso de verificación incluye: • Verificación de educación y titulación adecuada. • Experiencia clínica relevante y comprobada en terapias relacionadas a: ansiedad, estrés, depresión, riesgo suicida, trastorno límite de la personalidad, parejas, autoestima, desarrollo personal, sexualidad y género, laboral/académico, terapia infantil, social/familiar, entre otros.
          </p>
          <p className={styles.alignText}><strong>Confidencialidad</strong></p>
          <p>
          Su relación con el Psicoterapeuta/Psicólogo es estrictamente con el Psicoterapeuta/Psicólogo. INMIND no está involucrado en la relación Psicoterapeuta/Psicólogo-paciente y no interfiere, valida o controla el tratamiento del Psicoterapeuta/Psicólogo debido a la privacidad del proceso psicológico. La confidencialidad del caso se mantiene con el psicólogo a excepción de que el/la consultante atente contra su vida o la vida de un tercero; asimismo, esto también aplica cuando existe una derivación donde el psicólogo informa al nuevo profesional sobre el caso en cuestión. 
          </p>
          <p className={styles.alignText}><strong>Usted</strong></p>
          <p>
          Usted acepta que INMIND puede utilizar el correo electrónico o su contacto de celular que proporcione para enviarle ofertas de marketing desde INMIND.
          </p>
          <p className={styles.alignText}><strong>Disputas</strong></p>
          <p>
          Cualquier disputa o reclamo relacionado de alguna manera con su uso del Servicio INMINDse resolverá mediante un arbitraje confidencial vinculante, en lugar de un tribunal. No hay juez ni jurado en el arbitraje, y la revisión judicial de un laudo arbitral es limitada. Sin embargo, un árbitro puede otorgar de manera individual los mismos daños y reparación que un tribunal (incluyendo medidas cautelares y declaratorias o daños legales), y debe interpretar estos Términos como lo haría un tribunal. Se acuerda que cualquier procedimiento de resolución de disputas se llevará a cabo de manera individual y no en una acción de clase, consolidada o representativa. Si por algún motivo un reclamo se lleva a cabo en un tribunal en lugar de un arbitraje, INMIND y usted renuncian a cualquier derecho a un juicio. Se acuerda además que no puede presentar una demanda para prohibir la infracción u otro uso indebido de los derechos de propiedad intelectual.
          </p>
        </div>

        <div className={styles.buttons}>
          <button className={styles.okayButton} onClick={onAccept}>Okay</button>
          <button className={styles.cancelButton} onClick={onClose}>Cancelar</button>
        </div>
      </div>
    </div>
  );
};

export default TermsAndConditionsModal;
