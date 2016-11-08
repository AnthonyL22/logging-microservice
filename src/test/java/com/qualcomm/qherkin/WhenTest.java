package com.qualcomm.qherkin;

import org.junit.Test;

public class WhenTest extends LoggerBaseTest {

    @Test
    public void whenBlankTest() {
        QherkinLoggerService.WHEN("");
        verifyLogOutput("When");
    }

    @Test
    public void whenNullTest() {
        QherkinLoggerService.WHEN(null);
        verifyLogOutput("When null");
    }

    @Test
    public void whenNullArgTest() {
        QherkinLoggerService.WHEN("Login Test for user=%s and password=%s", null);
        verifyLogOutput("When Login Test for user=null and password=null");
    }

    @Test
    public void whenMismatchArgTest() {
        QherkinLoggerService.WHEN("Login Test for user=%s and password=%s", "foobar");
        verifyLogOutput("When Login Test for user= and password=foobar");
    }

    @Test
    public void whenTooManyArgTest() {
        QherkinLoggerService.WHEN("Login Test for user=%s and password=%s", "foobar", "password", true);
        verifyLogOutput("When Login Test for user=foobar and password=");
    }

    @Test
    public void whenWithArgsTest() {
        QherkinLoggerService.WHEN("Login Test for user=%s and password=%s", "anthony", "foobar");
        verifyLogOutput("When Login Test for user=anthony and password=foobar");
    }

    @Test
    public void whenTest() {
        QherkinLoggerService.WHEN("Login Test for user");
        verifyLogOutput("When Login Test for user");
    }

}
