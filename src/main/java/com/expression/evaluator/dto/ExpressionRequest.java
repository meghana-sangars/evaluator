package com.expression.evaluator.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExpressionRequest {
    @NotBlank
    private String expression;
}