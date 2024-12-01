package com.inmind.latam.service;

import com.inmind.latam.dto.PsychoProfileDto;
import com.inmind.latam.model.AccumulatedPsychoProfile;

public interface IAccumulatedPsychoProfileService {
	
	public void saveAccumulatedPsychoProfile(AccumulatedPsychoProfile accumulatedPsychoProfile);
	public AccumulatedPsychoProfile getAccumulatedPsychoProfileByIdQuestionnaire(String idQuestionnaire);
	public void updateAccumulatedPsychoProfile(PsychoProfileDto psychoProfile, String idQuestionnaire);

}
