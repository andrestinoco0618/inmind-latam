package com.inmind.latam.controller;

import com.inmind.latam.dto.QuestionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inmind.latam.dto.TransactionQuestionnaireDto;
import com.inmind.latam.exception.ResourceNotFoundException;
import com.inmind.latam.service.ITransactionQuestionnaireService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import static com.inmind.latam.constant.CacheKeys.PROFILE_TYPE;

/**
 * REST Controller for handling questionnaire transactions.
 * 
 * This controller provides endpoints for:
 * - Starting questionnaires
 * - Processing questionnaire responses
 * - Selecting psychologists
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.service.ITransactionQuestionnaireService
 */
@RestController
@RequestMapping("/api/v1/transaction/questionnaire")
@Tag(name = "Questionnaire Transaction", description = "APIs to manage the flow of the psychological questionnaire")
public class TransactionQuestionnaireController {

	private final ITransactionQuestionnaireService transactionQuestionnaireService;

    /**
     * Constructs a new TransactionQuestionnaireController with the specified service.
     * 
     * @param transactionQuestionnaireService the service for handling questionnaire transactions
     */
    public TransactionQuestionnaireController(ITransactionQuestionnaireService transactionQuestionnaireService) {
        this.transactionQuestionnaireService = transactionQuestionnaireService;
    }
    
    @Operation(
        summary = "Start questionnaire",
        description = "Start a new questionnaire based on the specified profile type. Generate a unique ID and configure the initial question flow."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Questionnaire started successfully",
            content = @Content(schema = @Schema(implementation = QuestionResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "Profile type not found"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping(value = "/start")
	public ResponseEntity<?> getQuestionnaireName(
        @Parameter(description = "Profile type for the questionnaire (ej: 'PF001', 'PF002')", required = true)
        @RequestParam(PROFILE_TYPE) String profileType
    ) throws ResourceNotFoundException {
		return new ResponseEntity<>(transactionQuestionnaireService.startQuestionnaire(profileType), HttpStatus.OK);
	}
    
    @Operation(
        summary = "Process questionnaire response",
        description = "Processes a questionnaire response, updates the status, and determines the next question or outcome."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Response processed successfully",
            content = @Content(schema = @Schema(implementation = QuestionResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Resources not found"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping(value = "/response")
	public ResponseEntity<?> transactionQuestionnaire(
        @Parameter(description = "Questionnaire response data", required = true)
        @RequestBody TransactionQuestionnaireDto transactionQuestionnaireDto
    ) throws ResourceNotFoundException {
		return new ResponseEntity<>(transactionQuestionnaireService.transactionQuestionnaire(transactionQuestionnaireDto), HttpStatus.OK);
	}
    
    @Operation(
        summary = "Select psychologist",
        description = "Updates the questionnaire with the psychologist selected by the user."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Psychologist successfully selected"),
        @ApiResponse(responseCode = "404", description = "Questionnaire or psychologist not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/{idQuestionnaire}/select-psychologist")
    public ResponseEntity<?> selectPsychologist(
        @Parameter(description = "Questionnaire ID", required = true)
        @PathVariable String idQuestionnaire,
        @Parameter(description = "Selected psychologist ID", required = true)
        @RequestParam String idPsychologist
    ) {
    	return new ResponseEntity<>(transactionQuestionnaireService.updateSelectPsycho(idQuestionnaire, idPsychologist), HttpStatus.NO_CONTENT);
    }
}
