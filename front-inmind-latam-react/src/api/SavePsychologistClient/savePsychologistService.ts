import apiClient from '../apiClient';

/**
 * @api {patch} /:questionnaireId/select-psychologist Save selected psychologist
 * @apiName savePsychologist
 * @apiGroup Psychologist
 * @apiParam {string} questionnaireId - Questionnaire identifier
 * @apiParam {string} idPsychologist - Psychologist identifier
 * @apiSuccess {Object} data - Response data after saving psychologist
 * @apiError {Error} error - Error object
 */
export const savePsychologist = async (
  questionnaireId: string,
  idPsychologist: string
) => {
  try {
    const response = await apiClient.patch(
      `/${questionnaireId}/select-psychologist`,
      null,
      {
        params: { idPsychologist },
      }
    );

    return response.data;
  } catch (error) {
    console.error('Error saving the psychologist:', error);
    throw error;
  }
};
