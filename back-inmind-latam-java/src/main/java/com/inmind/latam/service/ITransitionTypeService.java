package com.inmind.latam.service;

import com.inmind.latam.model.TransitionType;

public interface ITransitionTypeService {
	
	public TransitionType identifyTranstionType(String idProfile, String idAlternative);

}
