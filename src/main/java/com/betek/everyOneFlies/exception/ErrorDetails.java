package com.betek.everyOneFlies.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ErrorDetails {

    private final String message;
    private final String details;
    private final List<String> fliedErrors;

    public ErrorDetails(String message, String details) {
        this.message = message;
        this.details = details;
        this.fliedErrors = new ArrayList<>();
        this.fliedErrors.add("No field validation errors.");
    }

    public ErrorDetails(String message, String details, List<String> fliedErrors) {
        this.message = message;
        this.details = details;
        this.fliedErrors = (fliedErrors != null && !fliedErrors.isEmpty()) ? fliedErrors : new ArrayList<>(List.of("No field validation errors."));
    }

}
