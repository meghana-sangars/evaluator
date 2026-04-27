package com.expression.evaluator.service;

import com.expression.evaluator.dto.ExpressionResponse;
import com.expression.evaluator.entity.ExpressionRecord;
import com.expression.evaluator.mapper.RequestResponseMapper;
import com.expression.evaluator.repository.ExpressionRepository;
import com.expression.evaluator.utils.ExpressionUtility;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExpressionService {
    private final ExpressionRepository repository;
    private final RequestResponseMapper responseMapper;

    public ExpressionService(ExpressionRepository repository, RequestResponseMapper responseMapper) {
        this.repository = repository;
        this.responseMapper = responseMapper;
    }

    public ExpressionResponse evaluateAndSave(String expression) {
        BigDecimal result = ExpressionUtility.evaluate(expression);

        ExpressionRecord record = new ExpressionRecord();
        record.setExpression(expression);
        record.setResult(result);
        record.setCreatedAt(LocalDateTime.now());
        repository.save(record);

        ExpressionResponse response = responseMapper.toResponse(record);

        return response;
    }

    public List<ExpressionResponse> getHistory() {
        List<ExpressionRecord> records  = repository.findAll();
        return records.stream().map(responseMapper::toResponse).toList();
    }

    public List<ExpressionResponse> findByResult(BigDecimal result) {
        BigDecimal tolerance = new BigDecimal("0.00001");
        List<ExpressionRecord> record =  repository.findByResult(result.subtract(tolerance), result.add(tolerance));
        return record.stream().map(responseMapper::toResponse).toList();
    }
}
