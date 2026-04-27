package com.expression.evaluator.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "expressions")
public class ExpressionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String expression;

    @Column(nullable = false, precision = 20, scale = 10)
    private BigDecimal result;

    private LocalDateTime createdAt;

}
