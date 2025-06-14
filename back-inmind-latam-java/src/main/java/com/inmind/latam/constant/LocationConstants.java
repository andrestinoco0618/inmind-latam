package com.inmind.latam.constant;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static com.inmind.latam.constant.QuestionIdentifiers.*;

/**
 * Constants class that defines location-related constants used throughout the application.
 * <p>
 * This class contains constants for:
 * - Country identifiers
 * - Question groups for different location types
 * - Mapping between questions and location types
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 */
public class LocationConstants {
    /** Private constructor to prevent instantiation */
    private LocationConstants() {}

    /** Identifier for Peru in the system */
    public static final String PERU_ID = "173";

    /** Set of questions related to country selection */
    public static final Set<String> COUNTRY_QUESTIONS = Set.of(
            COUNTRY_QUESTION_ONE,
            QuestionIdentifiers.COUNTRY_QUESTION_TWO
    );

    /** Set of questions related to district selection */
    public static final Set<String> DISTRICT_QUESTIONS = Set.of(
            QuestionIdentifiers.DISTRICT_QUESTION_TWO,
            QuestionIdentifiers.DISTRICT_QUESTION_THREE
    );

    /** Map that associates questions with their corresponding location type */
    public static final Map<String, QuestionGroupType> QUESTION_GROUP_LOCATION = Collections.unmodifiableMap(
            Map.ofEntries(
                    Map.entry(COUNTRY_QUESTION_ONE, QuestionGroupType.COUNTRY),
                    Map.entry(COUNTRY_QUESTION_TWO, QuestionGroupType.COUNTRY),
                    Map.entry(STATE_QUESTION_ONE, QuestionGroupType.STATE),
                    Map.entry(STATE_QUESTION_TWO, QuestionGroupType.STATE),
                    Map.entry(CITY_QUESTION_ONE, QuestionGroupType.CITY),
                    Map.entry(CITY_QUESTION_TWO, QuestionGroupType.CITY),
                    Map.entry(DISTRICT_QUESTION_ONE, QuestionGroupType.DISTRICT)
            )
    );
}
