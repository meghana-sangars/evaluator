package com.expression.evaluator.utils;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Stack;

public class ExpressionUtility {

    private static final MathContext MC = new MathContext(10, RoundingMode.HALF_UP);

    public static BigDecimal evaluate(String s) {
        if (s == null || s.trim().isEmpty()) {
            throw new IllegalArgumentException("Exception : Expression cannot be empty");
        }

        Stack<BigDecimal> values = new Stack<>();
        Stack<Character> ops = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (ch == ' ') continue;

            if (Character.isDigit(ch) || ch == '.') {
                StringBuilder sb = new StringBuilder();

                while (i < s.length() &&
                        (Character.isDigit(s.charAt(i)) || s.charAt(i) == '.')) {
                    sb.append(s.charAt(i));
                    i++;
                }
                i--;

                try {
                    values.push(new BigDecimal(sb.toString(), MC));
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("Exception : Invalid number: " + sb);
                }
            }

            else if (ch == '(') {
                ops.push(ch);
            }

            else if (ch == ')') {
                while (!ops.isEmpty() && ops.peek() != '(') {
                    process(values, ops);
                }

                if (ops.isEmpty()) {
                    throw new IllegalArgumentException("Exception : Mismatched parentheses");
                }

                ops.pop(); // remove '('
            }

            else if (isOperator(ch)) {

                // Prevent cases like "3 + * 5"
                if (i == 0 || isOperator(previousNonSpace(s, i))) {
                    throw new IllegalArgumentException("Exception : Invalid operator placement at: " + ch);
                }

                while (!ops.isEmpty() && precedence(ops.peek()) >= precedence(ch)) {
                    process(values, ops);
                }

                ops.push(ch);
            }

            else {
                throw new IllegalArgumentException("Exception : Invalid character: " + ch);
            }
        }


        while (!ops.isEmpty()) {
            if (ops.peek() == '(') {
                throw new IllegalArgumentException("Exception : Mismatched parentheses");
            }
            process(values, ops);
        }

        if (values.size() != 1) {
            throw new IllegalArgumentException("Exception : Invalid expression format");
        }

        return values.pop();
    }

    private static void process(Stack<BigDecimal> values, Stack<Character> ops) {
        if (values.size() < 2) {
            throw new IllegalArgumentException("Exception : Invalid expression format");
        }

        BigDecimal b = values.pop();
        BigDecimal a = values.pop();
        char op = ops.pop();

        values.push(applyOp(op, a, b));
    }

    private static BigDecimal applyOp(char op, BigDecimal a, BigDecimal b) {
        switch (op) {
            case '+': return a.add(b, MC);
            case '-': return a.subtract(b, MC);
            case '*': return a.multiply(b, MC);
            case '/':
                if (b.compareTo(BigDecimal.ZERO) == 0) {
                    throw new ArithmeticException("Exception : Division by zero");
                }
                return a.divide(b, MC);
        }
        throw new IllegalArgumentException("Exception : Invalid operator: " + op);
    }

    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    private static int precedence(char op) {
        if (op == '+' || op == '-') return 1;
        if (op == '*' || op == '/') return 2;
        return 0;
    }

    private static char previousNonSpace(String s, int i) {
        i--;
        while (i >= 0 && s.charAt(i) == ' ') i--;
        return i >= 0 ? s.charAt(i) : '#';
    }
}