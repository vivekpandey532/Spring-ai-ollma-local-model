package com.ollma.model.DTOs;

import java.util.List;

public record ModelAnswer(
        String definition,
        List<String> useCases,
        List<String> commonMistakes
) {}
