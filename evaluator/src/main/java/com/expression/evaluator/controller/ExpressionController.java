package com.expression.evaluator.controller;

import com.expression.evaluator.dto.ExpressionRequest;
import com.expression.evaluator.dto.ExpressionResponse;
import com.expression.evaluator.service.ExpressionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/expressions/")
public class ExpressionController {

    private final ExpressionService service;

    public ExpressionController(ExpressionService service) {
        this.service = service;
    }

    @PostMapping("/evaluate")
    public ResponseEntity<ExpressionResponse> evaluate(
            @Valid @RequestBody ExpressionRequest request) {

        return ResponseEntity.ok(service.evaluateAndSave(request.getExpression()));
    }

    @GetMapping
    public ResponseEntity<List<ExpressionResponse>> getHistory () {
        return ResponseEntity.ok(service.getHistory());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ExpressionResponse>> getByResult(@RequestParam BigDecimal result) {
        return ResponseEntity.ok(service.findByResult(result));
    }
}
