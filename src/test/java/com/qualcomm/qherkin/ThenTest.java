package com.qualcomm.qherkin;

import org.junit.Test;

public class ThenTest extends LoggerBaseTest {

    @Test
    public void thenBlankTest() {
        QherkinLoggerService.THEN("");
        verifyLogOutput("Then");
    }

    @Test
    public void thenNullTest() {
        QherkinLoggerService.THEN(null);
        verifyLogOutput("Then null");
    }

    @Test
    public void thenNullArgTest() {
        QherkinLoggerService.THEN("Login Test for user=%s and password=%s", null);
        verifyLogOutput("Then Login Test for user=null and password=null");
    }

    @Test
    public void thenMismatchArgTest() {
        QherkinLoggerService.THEN("Login Test for user=%s and password=%s", "foobar");
        verifyLogOutput("Then Login Test for user= and password=foobar");
    }

    @Test
    public void thenTooManyArgTest() {
        QherkinLoggerService.THEN("Login Test for user=%s and password=%s", "foobar", "password", true);
        verifyLogOutput("Then Login Test for user=foobar and password=");
    }

    @Test
    public void thenWithArgsTest() {
        QherkinLoggerService.THEN("Login Test for user=%s and password=%s", "anthony", "foobar");
        verifyLogOutput("Then Login Test for user=anthony and password=foobar");
    }

    @Test
    public void thenTest() {
        QherkinLoggerService.THEN("Login Test for user");
        verifyLogOutput("Then Login Test for user");
    }

}
