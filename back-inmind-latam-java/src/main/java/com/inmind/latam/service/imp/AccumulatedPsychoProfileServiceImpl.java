package com.inmind.latam.service.imp;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.inmind.latam.dto.PsychoProfileDto;
import com.inmind.latam.exception.ResourceNotFoundException;
import com.inmind.latam.model.AccumulatedPsychoProfile;
import com.inmind.latam.repository.IAccumulatedPsychoProfileRepository;
import com.inmind.latam.service.IAccumulatedPsychoProfileService;

@Service
public class AccumulatedPsychoProfileServiceImpl implements IAccumulatedPsychoProfileService{
	
	private final IAccumulatedPsychoProfileRepository accumulatedPsychoProfileRepository;
	
	public AccumulatedPsychoProfileServiceImpl(IAccumulatedPsychoProfileRepository accumulatedPsychoProfileRepository) {
		this.accumulatedPsychoProfileRepository = accumulatedPsychoProfileRepository;
	} 

	@Override
	public void saveAccumulatedPsychoProfile(AccumulatedPsychoProfile accumulatedPsychoProfile) {
		accumulatedPsychoProfileRepository.save(accumulatedPsychoProfile);
	}

	@Override
	public AccumulatedPsychoProfile getAccumulatedPsychoProfileByIdQuestionnaire(String idQuestionnaire) {
	    return accumulatedPsychoProfileRepository.findByIdQuestionnaireAnswered(idQuestionnaire)
	            .orElseThrow(() -> new ResourceNotFoundException("Accumulated psycho profile not found with: " + idQuestionnaire));
	}

	@Override
	public void updateAccumulatedPsychoProfile(PsychoProfileDto psychoProfile, String idQuestionnaire) {
	    AccumulatedPsychoProfile accumulatedPsychoProfile = getAccumulatedPsychoProfileByIdQuestionnaire(idQuestionnaire);

	    accumulatedPsychoProfile.setFinalPoint(psychoProfile.countAlternatives().intValue());
	    accumulatedPsychoProfile.setIdPsychologist(psychoProfile.idPsychologist());
	    accumulatedPsychoProfile.setUpdatedAt(LocalDateTime.now());

	    accumulatedPsychoProfileRepository.save(accumulatedPsychoProfile);
	}


}
