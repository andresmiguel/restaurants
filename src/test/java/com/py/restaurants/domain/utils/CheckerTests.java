package com.py.restaurants.domain.utils;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class CheckerTests {

    @Test
    public void shouldIsNotBlankThrowExceptionWhenStringIsBlank() {
        final String errorMsg = "This is an error! :)";

        assertThatThrownBy(() -> Checker.isNotBlank(null, errorMsg))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(errorMsg);

        assertThatThrownBy(() -> Checker.isNotBlank("", errorMsg))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(errorMsg);

        assertThatThrownBy(() -> Checker.isNotBlank("  ", errorMsg))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(errorMsg);
    }

    @Test
    public void shouldIsNotBlankNotThrowExceptionWhenStringIsValid() {
        Checker.isNotBlank("not blank", "Error");
    }

    @Test
    public void shouldIsValidHourThrowExceptionWhenHourIsNotValid() {
        final String errorMsg = "This is an error! :)";

        assertThatThrownBy(() -> Checker.isValidHour(-1, errorMsg))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(errorMsg);

        assertThatThrownBy(() -> Checker.isValidHour(24, errorMsg))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(errorMsg);
    }

    @Test
    public void shouldIsValidHourNotThrowExceptionWhenHourIsValid() {
        Checker.isValidHour(10, "Error");
    }

    @Test
    public void shouldIsValidMinuteThrowExceptionWhenMinuteIsNotValid() {
        final String errorMsg = "This is an error! :)";

        assertThatThrownBy(() -> Checker.isValidMinute(-1, errorMsg))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(errorMsg);

        assertThatThrownBy(() -> Checker.isValidMinute(60, errorMsg))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(errorMsg);
    }

    @Test
    public void shouldIsValidMinuteNotThrowExceptionWhenMinuteIsValid() {
        Checker.isValidMinute(10, "Error");
    }
}
