package com.keith.kata.stringcalculator.delimiter;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Delimiter {

    private static final List<String> DEFAULT_DELIMITERS = Arrays.asList(",", "\\n");

    private List<String> delimiters;

    public Delimiter() {
        this(new ArrayList<>(DEFAULT_DELIMITERS));
    }

    public Delimiter(List<String> delimiters) {
        this.delimiters = delimiters;
    }

    public void addDelimiter(String delimiter) {
        this.delimiters.add(delimiter);
    }

    public String getDelimitersRegex() {
        return String.format("[%s]", StringUtils.join(this.delimiters, ","));
    }

    public List<String> getDelimiters() {
        return delimiters;
    }
}
