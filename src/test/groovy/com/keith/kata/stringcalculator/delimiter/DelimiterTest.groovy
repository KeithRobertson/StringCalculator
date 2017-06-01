package com.keith.kata.stringcalculator.delimiter

import org.junit.Test

class DelimiterTest {

    @Test
    void "getDelimitersRegex with no delimiters returns an empty character set"() {
        Delimiter delimiter = new Delimiter(Collections.emptyList())
        assert "[]" == delimiter.getDelimitersRegex()
    }

    @Test
    void "getDelimiterRegex returns the delimiter in a one-length list as a character set"() {
        Delimiter delimiter = new Delimiter(Arrays.asList(";"))
        assert "[;]" == delimiter.getDelimitersRegex()
    }

    @Test
    void "getDelimiterRegex returns all delimiters in the list within the character set"() {
        Delimiter delimiter = new Delimiter(Arrays.asList("!", "*", "x"))
        assert "[!,*,x]" == delimiter.getDelimitersRegex()
    }
}
