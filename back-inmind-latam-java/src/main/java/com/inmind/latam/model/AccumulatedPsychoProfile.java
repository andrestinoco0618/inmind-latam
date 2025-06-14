package com.inmind.latam.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Entity class representing accumulated psycho profile information.
 * <p>
 * This class maps to the 't_acumulado_psico_perfil' table in the database and contains
 * information about accumulated psycho profiles including:
 * - Profile identifier
 * - Psychologist identifier
 * - Questionnaire response information
 * - Review and selection status
 * - Timestamps
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "t_accumulated_psycho_profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccumulatedPsychoProfile {
	
	@Id
	@Column(name = "id_accumulated_psycho_profile")
	private int idAccumulatedPsychoProfile;
	
	@Column(name = "id_profile")
	private String idProfile;
	
	@Column(name = "id_psychologist")
	private String idPsychologist;
	
	@Column(name = "id_questionnaire_answered")
	private String idQuestionnaireAnswered;
	
	@Column(name = "final_score")
	private int finalPoint;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
	@Column(name = "is_review")
	private boolean isReview;
	
	@Column(name = "questionnaire_answered")
	private String questionnaireAnswered;
	
	@Column(name = "is_select_psychology")
	private boolean isSelectPsychology;
	
	@Column(name = "is_finish")
	private boolean isFinish;
	
	/**
	 * Constructs a new AccumulatedPsychoProfile with initial values.
	 * 
	 * @param idProfile The profile identifier
	 * @param idQuestionnaireAnswered The questionnaire response identifier
	 */
	public AccumulatedPsychoProfile(String idProfile, String idQuestionnaireAnswered) {
		this.idProfile = idProfile;
		this.idQuestionnaireAnswered = idQuestionnaireAnswered;
		this.createdAt = LocalDateTime.now();
		this.isReview = false;
		this.isSelectPsychology = false;
		this.isFinish = false;
	}
}
