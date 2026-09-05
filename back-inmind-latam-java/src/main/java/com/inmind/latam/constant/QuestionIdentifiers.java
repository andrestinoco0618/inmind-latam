package com.inmind.latam.constant;

import java.util.Set;

/**
 * Constants class that defines question identifiers used throughout the application.
 * <p>
 * This class contains identifiers for:
 * - General questions
 * - Country-related questions
 * - State-related questions
 * - City-related questions
 * - District-related questions
 * - Text constants
 * - Transition constants
 * - Alternative constants
 * - Alternative groups
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 */
public final class QuestionIdentifiers {
    /** Private constructor to prevent instantiation */
    private QuestionIdentifiers() {}

    /** Identifier for question 1 */
    public static final String QUESTION_ONE = "P00001";
    
    /** Identifier for question 5 */
    public static final String QUESTION_FIVE = "P00005";
    
    /** Identifier for question 8 */
    public static final String QUESTION_EIGHT = "P00008";
    
    /** Identifier for question 9 */
    public static final String QUESTION_NINE = "P00009";
    
    /** Identifier for question 12 */
    public static final String QUESTION_TWELVE = "P00012";
    
    /** Identifier for question 39 */
    public static final String QUESTION_THIRTY_NINE = "P00039";
    
    /** Identifier for question 40 */
    public static final String QUESTION_FORTY = "P00040";
    
    /** Identifier for question 57 */
    public static final String QUESTION_FIFTY_SEVEN = "P00057";
    
    /** Identifier for question 217 */
    public static final String QUESTION_TWO_HUNDRED_SEVENTEEN = "P00217";

    /** Identifier for country question 1 */
    public static final String COUNTRY_QUESTION_ONE = "P00044";
    
    /** Identifier for country question 2 */
    public static final String COUNTRY_QUESTION_TWO = "P00119";

    /** Identifier for state question 1 */
    public static final String STATE_QUESTION_ONE = "P00045";
    
    /** Identifier for state question 2 */
    public static final String STATE_QUESTION_TWO = "P00120";

    /** Identifier for city question 1 */
    public static final String CITY_QUESTION_ONE = "P00046";
    
    /** Identifier for city question 2 */
    public static final String CITY_QUESTION_TWO = "P00121";

    /** Identifier for district question 1 */
    public static final String DISTRICT_QUESTION_ONE = "P00217";
    
    /** Identifier for district question 2 */
    public static final String DISTRICT_QUESTION_TWO = "P00046";
    
    /** Identifier for district question 3 */
    public static final String DISTRICT_QUESTION_THREE = "P00121";

    /** Text constant for "Other" option */
    public static final String OTHER = "Otro";
    
    /** Empty string constant */
    public static final String EMPTY_STRING = "";

    /** Identifier for transition type 2 */
    public static final String TYPE_TRANSITION_TWO = "TT0002";

    /** Identifier for alternative 929 */
    public static final String ALTERNATIVE_NINE_HUNDRED_TWENTY_NINE = "A00929";
    
    /** Identifier for alternative 6 */
    public static final String ALTERNATIVE_SIX = "A00006";

    /** Set of alternative identifiers for group disorders one */
    public static final Set<String> GROUP_DISORDERS_ONE = Set.of(
            "A00007", "A00008", "A00009", "A00010", "A00011",
            "A00012", "A00013", "A00014", "A00015", "A00016"
    );
}