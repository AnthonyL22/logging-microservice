package com.qualcomm.qherkin;

import org.junit.Test;

public class AndTest extends LoggerBaseTest {

    @Test
    public void andBlankTest() {
        QherkinLoggerService.AND("");
        verifyLogOutput("And");
    }

    @Test
    public void andNullTest() {
        QherkinLoggerService.AND(null);
        verifyLogOutput("And null");
    }

    @Test
    public void andNullArgTest() {
        QherkinLoggerService.AND("Login Test for user=%s and password=%s", null);
        verifyLogOutput("And Login Test for user=null and password=null");
    }

    @Test
    public void andMismatchArgTest() {
        QherkinLoggerService.AND("Login Test for user=%s and password=%s", "foobar");
        verifyLogOutput("And Login Test for user= and password=foobar");
    }

    @Test
    public void andTooManyArgTest() {
        QherkinLoggerService.AND("Login Test for user=%s and password=%s", "foobar", "password", true);
        verifyLogOutput("And Login Test for user=foobar and password=");
    }

    @Test
    public void andWithArgsTest() {
        QherkinLoggerService.AND("Login Test for user=%s and password=%s", "anthony", "foobar");
        verifyLogOutput("And Login Test for user=anthony and password=foobar");
    }

    @Test
    public void andTest() {
        QherkinLoggerService.AND("Login Test for user");
        verifyLogOutput("And Login Test for user");
    }

}
