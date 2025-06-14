package com.inmind.latam.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a transition type in the system.
 * <p>
 * This class maps to the 'ttipotransicion' table in the database and contains
 * information about transition types including:
 * - Unique identifier
 * - Transition name
 * - Associated profile identifier
 * - Associated alternative identifier
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "t_transition_type")
@Data
@NoArgsConstructor
public class TransitionType {
	
	@Id
	@Column(name = "id_transition_type")
	private String idTransitionType;		
	
	@Column(name = "transition_type")
	private String transitionName;
	
	@Column(name = "id_profile")
	private String idProfile;
	
	@Column(name = "id_alternative")
	private String idAlternative;
}
