package com.inmind.latam.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing a country in the system.
 * <p>
 * This class maps to the 't_pais' table in the database and contains
 * information about countries including:
 * - Unique identifier
 * - Country name
 * - Phone code
 * - Creation and update timestamps
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see jakarta.persistence.Entity
 * @see jakarta.persistence.Table
 */
@Setter
@Getter
@Entity
@Table(name = "t_country")
@Data
@NoArgsConstructor
public class Country {

    @Id
	@Column(name = "id")
	private int id;

    @Column(name = "name")
	private String name;

    @Column(name = "phone_code")
	private String phoneCode;

    @Column(name = "created_at")
	private LocalDateTime createdAt;

    @Column(name = "updated_at")
	private LocalDateTime updatedAt;

}