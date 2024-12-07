package com.example.e_project_4_api.ex;

import java.util.List;

public class ValidationException extends RuntimeException {
    private List<String> errors;

    public ValidationException(List<String> errors) {
        super("Validation errors occurred");
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
