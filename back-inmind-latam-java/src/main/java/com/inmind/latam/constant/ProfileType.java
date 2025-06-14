package com.inmind.latam.constant;

import lombok.Getter;

/**
 * Enumeration representing different types of profiles in the system.
 * <p>
 * This enum defines the profile types with their associated:
 * - Start question identifier
 * - Transition question identifier
 * - Default transition identifier
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 */
@Getter
public enum ProfileType {
    /** Profile type 1 with specific question and transition identifiers */
    PF001("P00001", "P00004", "TT0001"),
    
    /** Profile type 2 with specific question and transition identifiers */
    PF002("P00001", "P00108", "TT0005"),
    
    /** Profile type 3 with specific question and transition identifiers */
    PF003("P00001", "P00129", "TT0007"),
    
    /** Profile type 4 with specific question and transition identifiers */
    PF004("P00001", "P00004", "TT0010");

    /** The identifier of the starting question for this profile type */
    private final String startQuestion;
    
    /** The identifier of the transition question for this profile type */
    private final String transitionQuestion;
    
    /** The identifier of the default transition for this profile type */
    private final String defaultTransition;

    /**
     * Constructs a new ProfileType with the specified identifiers.
     * 
     * @param startQuestion the identifier of the starting question
     * @param transitionQuestion the identifier of the transition question
     * @param defaultTransition the identifier of the default transition
     */
    ProfileType(String startQuestion, String transitionQuestion, String defaultTransition) {
        this.startQuestion = startQuestion;
        this.transitionQuestion = transitionQuestion;
        this.defaultTransition = defaultTransition;
    }
}
