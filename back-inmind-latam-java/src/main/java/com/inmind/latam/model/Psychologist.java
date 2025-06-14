package com.inmind.latam.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a psychologist in the system.
 * <p>
 * This class maps to the 't_psicologa' table in the database and contains
 * information about psychologists including:
 * - Unique identifier
 * - Personal information (name, lastname, phone, address)
 * - Gender
 * - Active status
 * - Profile link and image
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "t_psychologist")
@Data
@NoArgsConstructor
public class Psychologist {
	
	@Id
	@Column(name = "id_psychologist")
	private String idPsychologist;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "last_name")
	private String lastname;
	
	@Column(name = "phone_number")
	private String numberPhone;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "sex")
	private String sex;
	
	@Column(name = "is_active")
	private Boolean isActive;
	
	@Column(name = "link_profile")
	private String linkProfile;
	
	@Column(name = "image")
	private String image;
}
