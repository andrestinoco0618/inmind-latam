package com.inmind.latam.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing a state in the system.
 * <p>
 * This class maps to the 't_estado' table in the database and contains
 * information about states including:
 * - Unique identifier
 * - State name
 * - Associated country
 * - Creation and update timestamps
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 */
@Setter
@Getter
@Entity
@Table(name = "t_state")
@Data
@NoArgsConstructor
public class State {
	
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "name")
	private String name;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Column(name = "country_id")
	private Integer countryId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id", insertable = false, updatable = false)
	private Country country;
}
