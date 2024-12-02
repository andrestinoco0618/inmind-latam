package com.inmind.latam.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inmind.latam.dto.TransactionQuestionnaireDto;
import com.inmind.latam.exception.QuestionNotReadyException;
import com.inmind.latam.exception.ResourceNotFoundException;
import com.inmind.latam.service.ITransactionQuestionnaireService;

import static com.inmind.latam.constant.QuestionConstant.*;

@RestController
@RequestMapping("/api/v1/transaction/questionnaire")
public class TransactionQuestionnaireController {

	private final ITransactionQuestionnaireService transactionQuestionnaireService;

    public TransactionQuestionnaireController(ITransactionQuestionnaireService transactionQuestionnaireService) {
        this.transactionQuestionnaireService = transactionQuestionnaireService;
    }
    
    @GetMapping(value = "/start")
	public ResponseEntity<?> getQuestionnaireName(@RequestParam(PROFILE_TYPE)  String profileType) throws ResourceNotFoundException{
		return new ResponseEntity<>(transactionQuestionnaireService.startQuestionnaire(profileType), HttpStatus.OK);
	}
    
    @PostMapping(value = "/response")
	public ResponseEntity<?> transactionQuestionnaire(@RequestBody TransactionQuestionnaireDto transactionQuestionnaireDto) throws ResourceNotFoundException, QuestionNotReadyException{
		return new ResponseEntity<>(transactionQuestionnaireService.transactionQuestionnaire(transactionQuestionnaireDto), HttpStatus.OK);
	}
        
}
