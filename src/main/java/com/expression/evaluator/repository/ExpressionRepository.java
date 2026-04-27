package com.expression.evaluator.repository;

import com.expression.evaluator.entity.ExpressionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ExpressionRepository extends JpaRepository<ExpressionRecord, Long> {
    @Query("SELECT e FROM ExpressionRecord e WHERE e.result BETWEEN :min AND :max")
    List<ExpressionRecord> findByResult(BigDecimal min, BigDecimal max);
}
