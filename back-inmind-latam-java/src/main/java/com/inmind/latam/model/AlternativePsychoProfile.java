package com.inmind.latam.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing an alternative psycho profile in the system.
 * <p>
 * This class maps to the 't_alternativa_psico_perfil' table in the database and contains
 * information about alternative psycho profiles including:
 * - Unique identifier
 * - Associated psychologist identifier
 * - Associated alternative identifier
 * - Associated profile identifier
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "t_alternative_psycho_profile")
@Data
@NoArgsConstructor
public class AlternativePsychoProfile {

	@Id
	@Column(name = "id_alternative_psycho_profile")
	private String idAlternativePsychoProfile;
	
	@Column(name = "id_psychologist")
	private String idPsychologist;
	
	@Column(name = "id_alternative")
	private String idAlternative;
	
	@Column(name = "id_profile")
	private String idProfile;
}
