package com.inmind.latam.dto;

/**
 * Sealed interface that defines possible questionnaire response types.
 * <p>
 * This interface permits three types of responses:
 * - QuestionResponseDto: Individual question responses
 * - PsychologistProfileDto: Psychologist profiles
 * - RedirectDto: Redirects to other sections
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.dto.QuestionResponseDto
 * @see com.inmind.latam.dto.PsychologistProfileDto
 * @see com.inmind.latam.dto.RedirectDto
 */
public sealed interface QuestionnaireResponse permits QuestionResponseDto, PsychologistProfileDto, RedirectDto {}