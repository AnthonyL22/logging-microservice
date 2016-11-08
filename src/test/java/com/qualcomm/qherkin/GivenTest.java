package com.qualcomm.qherkin;

import org.junit.Test;

public class GivenTest extends LoggerBaseTest {

    @Test
    public void givenBlankTest() {
        QherkinLoggerService.GIVEN("");
        verifyLogOutput("Given");
    }

    @Test
    public void givenNullTest() {
        QherkinLoggerService.GIVEN(null);
        verifyLogOutput("Given null");
    }

    @Test
    public void givenNullArgTest() {
        QherkinLoggerService.GIVEN("Login Test for user=%s and password=%s", null);
        verifyLogOutput("Given Login Test for user=null and password=null");
    }

    @Test
    public void givenMismatchArgTest() {
        QherkinLoggerService.GIVEN("Login Test for user=%s and password=%s", "foobar");
        verifyLogOutput("Given Login Test for user= and password=foobar");
    }

    @Test
    public void givenTooManyArgTest() {
        QherkinLoggerService.GIVEN("Login Test for user=%s and password=%s", "foobar", "password", true);
        verifyLogOutput("Given Login Test for user=foobar and password=");
    }

    @Test
    public void givenWithArgsTest() {
        QherkinLoggerService.GIVEN("Login Test for user=%s and password=%s", "anthony", "foobar");
        verifyLogOutput("Given Login Test for user=anthony and password=foobar");
    }

    @Test
    public void givenTest() {
        QherkinLoggerService.GIVEN("Login Test for user");
        verifyLogOutput("Given Login Test for user");
    }

}
