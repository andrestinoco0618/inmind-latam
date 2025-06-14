package com.inmind.latam.strategy;

import java.util.Map;
import org.springframework.stereotype.Component;
import com.inmind.latam.constant.QuestionGroupType;

/**
 * Factory class for creating and managing location strategies.
 * 
 * This class provides a centralized way to access different location strategies
 * based on the question group type. It manages strategies for:
 * - Countries
 * - States
 * - Cities
 * - Districts
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.strategy.LocationStrategy
 * @see com.inmind.latam.constant.QuestionGroupType
 */
@Component
public class LocationStrategyFactory {
    
    /** Map of question group types to their corresponding strategies */
    private final Map<QuestionGroupType, LocationStrategy> strategies;
    
    /**
     * Constructs a new LocationStrategyFactory with the provided strategies.
     * 
     * @param countryStrategy strategy for handling country-related operations
     * @param stateStrategy strategy for handling state-related operations
     * @param cityStrategy strategy for handling city-related operations
     * @param districtStrategy strategy for handling district-related operations
     */
    public LocationStrategyFactory(
            CountryStrategy countryStrategy,
            StateStrategy stateStrategy,
            CityStrategy cityStrategy,
            DistrictStrategy districtStrategy) {
        
        this.strategies = Map.of(
            QuestionGroupType.COUNTRY, countryStrategy,
            QuestionGroupType.STATE, stateStrategy,
            QuestionGroupType.CITY, cityStrategy,
            QuestionGroupType.DISTRICT, districtStrategy
        );
    }
    
    /**
     * Gets the appropriate strategy for the given question group type.
     * 
     * @param type the question group type
     * @return the corresponding location strategy, or null if not found
     */
    public LocationStrategy getStrategy(QuestionGroupType type) {
        return strategies.getOrDefault(type, null);
    }
} 