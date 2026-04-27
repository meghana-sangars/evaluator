package com.expression.evaluator.mapper;

import com.expression.evaluator.dto.ExpressionResponse;
import com.expression.evaluator.entity.ExpressionRecord;
import org.springframework.stereotype.Component;

@Component
public class RequestResponseMapper {
    public ExpressionResponse toResponse(ExpressionRecord record) {
        ExpressionResponse response = new ExpressionResponse();
                response.setExpression(record.getExpression());
                response.setResult(record.getResult());
    return response;
    }
}
