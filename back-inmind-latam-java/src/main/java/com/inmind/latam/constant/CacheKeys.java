package com.inmind.latam.constant;

/**
 * Constants class that defines cache keys used throughout the application.
 * 
 * This class contains all the cache key constants used for:
 * - Questionnaire caching
 * - Position number caching
 * - Response caching
 * - Profile type caching
 * - Alternative transition caching
 * - Transition questionnaire caching
 * - Diagnosis questionnaire caching
 * - Question duplication caching
 * - Question level caching
 * - Disorder caching
 * - Question exclusion caching
 * - Country selection caching
 * - Redirect validation caching
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 */
public class CacheKeys {
    /** Private constructor to prevent instantiation */
    private CacheKeys() {}

    /** Cache key for questionnaire data */
    public static final String QUESTIONNAIRE = "questionnaireCache";

    /** Cache key for profile type data */
    public static final String PROFILE_TYPE = "profileType";
    
    /** Cache key for alternative transition data */
    public static final String ALTERNATIVE_TRANSITION = "alternativeTransition";
    
    /** Cache key for transition questionnaire data */
    public static final String TRANSITION = "transitionQuestionnaire";
    
    /** Cache key for diagnosis questionnaire data */
    public static final String DIAGNOSIS = "diagnosisQuestionnaire";
    
    /** Cache key for duplicate question data */
    public static final String DUPLICATE = "duplicateQuestion";
    
    /** Cache key for question level data */
    public static final String LEVEL = "levelQuestion";
    
    /** Cache key for disorders data */
    public static final String DISORDERS = "disordersOne";
    
    /** Cache key for question exclusion data */
    public static final String EXCLUDE = "excludeQuestion";
    
    /** Cache key for selected country data */
    public static final String COUNTRY = "selectedCountry";
    
    /** Cache key for redirect validation data */
    public static final String REDIRECT = "redirectValid";
    
    /** Prefix for cache keys */
    public static final String PREFIX = "keys:";
}
