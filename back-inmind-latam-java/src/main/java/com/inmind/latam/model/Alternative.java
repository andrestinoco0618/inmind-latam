package com.inmind.latam.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing an alternative in the system.
 * <p>
 * This class maps to the 'talternativa' table in the database and contains
 * information about alternatives including:
 * - Unique identifier
 * - Alternative text
 * - Alternative type
 * - Title
 * - Associated question
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "t_alternative")
@Data
@NoArgsConstructor
public class Alternative {
	
	@Id
	@Column(name = "id_alternative")
	private String idAlternative;
	
	@Column(name = "alternative_text")
	private String textAlternative;
	
	@Column(name = "alternative_type")
	private String alternativeType;
	
	@Column(name = "qualification")
	private String title;
	
	@ManyToOne
    @JoinColumn(name = "id_question", referencedColumnName = "id_question", insertable = false, updatable = false)
    private Question question;
}
