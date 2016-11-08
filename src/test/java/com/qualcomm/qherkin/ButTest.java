package com.qualcomm.qherkin;

import org.junit.Test;

public class ButTest extends LoggerBaseTest {

    @Test
    public void butBlankTest() {
        QherkinLoggerService.BUT("");
        verifyLogOutput("But");
    }

    @Test
    public void butNullTest() {
        QherkinLoggerService.BUT(null);
        verifyLogOutput("But null");
    }

    @Test
    public void butNullArgTest() {
        QherkinLoggerService.BUT("Login Test for user=%s but password=%s", null);
        verifyLogOutput("But Login Test for user=null but password=null");
    }

    @Test
    public void butMismatchArgTest() {
        QherkinLoggerService.BUT("Login Test for user=%s but password=%s", "foobar");
        verifyLogOutput("But Login Test for user= but password=foobar");
    }

    @Test
    public void butTooManyArgTest() {
        QherkinLoggerService.BUT("Login Test for user=%s but password=%s", "foobar", "password", true);
        verifyLogOutput("But Login Test for user=foobar but password=");
    }

    @Test
    public void butWithArgsTest() {
        QherkinLoggerService.BUT("Login Test for user=%s but password=%s", "anthony", "foobar");
        verifyLogOutput("But Login Test for user=anthony but password=foobar");
    }

    @Test
    public void butTest() {
        QherkinLoggerService.BUT("Login Test for user");
        verifyLogOutput("But Login Test for user");
    }

}
