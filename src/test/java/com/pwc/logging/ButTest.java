package com.pwc.logging;

import org.junit.Test;

public class ButTest extends LoggerBaseTest {

    @Test
    public void butBlankTest() {
        LoggerService.BUT("");
        verifyLogOutput("But");
    }

    @Test
    public void butNullTest() {
        LoggerService.BUT(null);
        verifyLogOutput("But null");
    }

    @Test
    public void butNullArgTest() {
        LoggerService.BUT("Login Test for user=%s but password=%s", null);
        verifyLogOutput("But Login Test for user=null but password=null");
    }

    @Test
    public void butMismatchArgTest() {
        LoggerService.BUT("Login Test for user=%s but password=%s", "foobar");
        verifyLogOutput("But Login Test for user= but password=foobar");
    }

    @Test
    public void butTooManyArgTest() {
        LoggerService.BUT("Login Test for user=%s but password=%s", "foobar", "password", true);
        verifyLogOutput("But Login Test for user=foobar but password=");
    }

    @Test
    public void butWithArgsTest() {
        LoggerService.BUT("Login Test for user=%s but password=%s", "anthony", "foobar");
        verifyLogOutput("But Login Test for user=anthony but password=foobar");
    }

    @Test
    public void butTest() {
        LoggerService.BUT("Login Test for user");
        verifyLogOutput("But Login Test for user");
    }

}
