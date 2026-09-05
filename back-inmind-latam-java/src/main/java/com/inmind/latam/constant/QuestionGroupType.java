package com.inmind.latam.constant;

/**
 * Enumeration representing different types of question groups in the questionnaire.
 * <p>
 * This enum defines the possible grouping types for questions:
 * - COUNTRY: Questions related to country selection
 * - CITY: Questions related to city selection
 * - STATE: Questions related to state/province selection
 * - DISTRICT: Questions related to district selection
 * - DEFAULT: Default group type for questions
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 */
public enum QuestionGroupType {
    /** Questions related to country selection */
    COUNTRY,
    /** Questions related to city selection */
    CITY,
    /** Questions related to state/province selection */
    STATE,
    /** Questions related to district selection */
    DISTRICT,
    /** Default group type for questions */
    DEFAULT
}