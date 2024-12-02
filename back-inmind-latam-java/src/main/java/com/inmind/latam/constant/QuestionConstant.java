package com.inmind.latam.constant;

import java.util.Collections;
import java.util.Map;

public class QuestionConstant {
	
	public static final String QUESTION_ONE = "P00001";
	public static final String QUESTIONNAIRE_CACHE = "questionnaireCache";
	public static final String POSITION_NUMBER_CACHE = "positionNumberCache";
	public static final String RESPONSE_CACHE = "responseCache";
	public static final String PROFILE_TYPE = "profileType";
	public static final String ALTERNATIVE_TRANSITION = "alternativeTransition";
	public static final String TRANSITION_QUESTIONNAIRE = "transitionQuestionnaire";

	
	public static final Map<String, String> PROFILE_START_QUESTIONNAIRE = Collections.unmodifiableMap(
        Map.of(
            "PF001", "P00001",
            "PF002", "P00106",
            "PF003", "P00001",
            "PF004", "P00001"
        )
    );
	
	public static final Map<String, String> PROFILE_TRANSITION_QUESTION = Collections.unmodifiableMap(
        Map.of(
            "PF001", "P00004",
            "PF002", "P00108",
            "PF003", "P00129",
            "PF004", "P00004"
        )
    );
	
	public static final Map<String, String> PROFILE_TRANSITION_DEFAULT = Collections.unmodifiableMap(
        Map.of(
            "PF001", "TT0001",
            "PF002", "TT0005",
            "PF003", "TT0007",
            "PF004", "TT0010"
        )
    );
}

