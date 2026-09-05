package com.inmind.latam.constant;

/**
 * Constants class that defines status values used throughout the application.
 * <p>
 * This class contains status values for:
 * - Processing state
 * - Matching state
 * - Redirecting state
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 */
public final class StatusValues {
    /** Private constructor to prevent instantiation */
    private StatusValues() {}

    /** Status indicating that a process is being processed */
    public static final String PROCESSING = "processing";
    
    /** Status indicating that a matching operation is in progress */
    public static final String MATCHING = "matching";
    
    /** Status indicating that a redirect operation is in progress */
    public static final String REDIRECTING = "redirecting";
}