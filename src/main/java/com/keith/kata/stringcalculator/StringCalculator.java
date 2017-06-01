package com.keith.kata.stringcalculator;

import com.keith.kata.stringcalculator.delimiter.Delimiter;
import com.keith.kata.stringcalculator.exception.InvalidDelimiterPrefixException;
import com.keith.kata.stringcalculator.exception.InvalidStringCalculatorArgumentException;
import com.keith.kata.stringcalculator.exception.NegativeNumberException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculator {


    private Delimiter delimiter;

    public int add(String numbers) throws InvalidStringCalculatorArgumentException {
        if (numbers.isEmpty()) {
            return 0;
        }

        this.delimiter = getDelimiter(numbers);
        String expression = getExpression(numbers);
        return processExpression(expression);
    }

    private int processExpression(String expression) {
        return addCommaSeparatedValues(expression);
    }

    private String getExpression(String numbers) throws NegativeNumberException {
        String expression = numbers;
        if (numbers.startsWith("//")) {
            int delimiterEndIndex = numbers.indexOf('\n');
            expression = numbers.substring(++delimiterEndIndex);
        }
        expression = stripNumbersOverOneThousand(expression);
        validateExpression(expression);
        return expression;
    }

    private String stripNumbersOverOneThousand(String expression) {
        return Arrays.stream(expression.split(this.delimiter.getDelimitersRegex()))
                .mapToInt(Integer::valueOf)
                .filter(i -> i <= 1000)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(this.delimiter.getDelimiters().get(0)));
    }

    private void validateExpression(String expression) throws NegativeNumberException {
        String[] numbersString = expression.split(this.delimiter.getDelimitersRegex());
        List<Integer> list = Arrays.stream(numbersString).map(Integer::valueOf).collect(Collectors.toList());
        List<Integer> exceptionList = new ArrayList<>();

        for (int i : list) {
            if (i < 0) {
                exceptionList.add(i);
            }
        }

        if (!exceptionList.isEmpty()) {
            throw new NegativeNumberException("Negative numbers not allowed: " + exceptionList);
        }
    }

    private Delimiter getDelimiter(String expression) throws InvalidDelimiterPrefixException {
        Delimiter delimiter = new Delimiter();
        if (expression.startsWith("//")) {
            String delimiterCaptureGroup = "//(.*)\\n";
            Pattern pattern = Pattern.compile(delimiterCaptureGroup);
            Matcher matcher = pattern.matcher(expression);
            if (matcher.find()) {
                delimiter.addDelimiter(matcher.group(1));
            } else {
                String message = String.format("Expected to match on the pattern %s but failed with expression %s", delimiterCaptureGroup, expression);
                throw new InvalidDelimiterPrefixException(message);
            }
        }
        return delimiter;
    }

    private int addCommaSeparatedValues(String numbers) {
        String[] individualNumbers = numbers.split(this.delimiter.getDelimitersRegex());
        return Arrays.stream(individualNumbers).mapToInt(Integer::valueOf).sum();
    }
}
