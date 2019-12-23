package com.pwc.logging;

import com.pwc.logging.service.LoggerService;
import org.junit.Test;

public class FinallyTest extends LoggerBaseTest {

    @Test
    public void finallyBlankTest() {
        LoggerService.FINALLY("");
        verifyLogOutput("Finally");
    }

    @Test
    public void orNullTest() {
        LoggerService.FINALLY(null);
        verifyLogOutput("Finally null");
    }

    @Test
    public void orNullArgTest() {
        LoggerService.FINALLY("Login Test for user=%s or password=%s", null);
        verifyLogOutput("Finally Login Test for user=null or password=null");
    }

    @Test
    public void orMismatchArgTest() {
        LoggerService.FINALLY("Login Test for user=%s or password=%s", "foobar");
        verifyLogOutput("Finally Login Test for user= or password=foobar");
    }

    @Test
    public void orTooManyArgTest() {
        LoggerService.FINALLY("Login Test for user=%s or password=%s", "foobar", "password", true);
        verifyLogOutput("Finally Login Test for user=foobar or password=");
    }

    @Test
    public void orWithArgsTest() {
        LoggerService.FINALLY("Login Test for user=%s or password=%s", "anthony", "foobar");
        verifyLogOutput("Finally Login Test for user=anthony or password=foobar");
    }

    @Test
    public void orTest() {
        LoggerService.FINALLY("Login Test for user");
        verifyLogOutput("Finally Login Test for user");
    }

}
