package com.keith.kata.stringcalculator

import groovy.test.GroovyAssert
import org.junit.Before
import org.junit.Test

class StringCalculatorTest extends GroovyAssert {

    StringCalculator stringCalculator

    @Before
    void setUp() {
        stringCalculator = new StringCalculator()
    }

    @Test
    void "add('') returns 0"() {
        assert 0 == stringCalculator.add("")
    }

    @Test
    void "add('1') returns 1"() {
        assert 1 == stringCalculator.add("1")
    }

    @Test
    void "add('2') returns 2"() {
        assert 2 == stringCalculator.add("2")
    }

    @Test
    void "add('1,1') returns 2"() {
        assert 2 == stringCalculator.add("1,1")
    }

    @Test
    void "add('3,5') returns 8"() {
        assert 8 == stringCalculator.add("3,5")
    }

    @Test
    void "add('1,2,3') returns 6"() {
        assert 6 == stringCalculator.add("1,2,3")
    }

    @Test
    void "Also allow newline as delimiter"() {
        assert 7 == stringCalculator.add("2\n2,3")
    }

    @Test
    void "Can provide ; delimiter at start"() {
        assert 3 == stringCalculator.add("//;\n1;2")
    }

    @Test
    void "Can provide @ delimiter at start"() {
        assert 16 == stringCalculator.add("//@\n11@5")
    }

    @Test
    void "Negative number throws exception"() {
        Throwable exception = shouldFail {
            stringCalculator.add("-1,2")
        }
        assert "Negative numbers not allowed: [-1]" == exception.getMessage()
    }

    @Test
    void "Multiple negative numbers all get listed within exception"() {
        Throwable exception = shouldFail {
            stringCalculator.add("-2,-3,0")
        }
        assert "Negative numbers not allowed: [-2, -3]" == exception.getMessage()
    }

    @Test
    void "Numbers greater than 1000 are ignored"() {
        assert 1000 == stringCalculator.add("1000,1001")
    }

    @Test
    void "Badly formed delimiters will throw an exception"() {
        Throwable exception = shouldFail {
            stringCalculator.add("//4,5")
        }
        assert "Expected to match on the pattern //(.*)\\n but failed with expression //4,5" == exception.getMessage()
    }
}