package com.inmind.latam.dto;

import java.io.Serializable;

public record PsychoProfileDto (String idPsychologist, Long countAlternatives) implements Serializable {}