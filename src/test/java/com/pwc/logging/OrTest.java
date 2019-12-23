package com.pwc.logging;

import com.pwc.logging.service.LoggerService;
import org.junit.Test;

public class OrTest extends LoggerBaseTest {

    @Test
    public void andBlankTest() {
        LoggerService.OR("");
        verifyLogOutput("Or");
    }

    @Test
    public void orNullTest() {
        LoggerService.OR(null);
        verifyLogOutput("Or null");
    }

    @Test
    public void orNullArgTest() {
        LoggerService.OR("Login Test for user=%s or password=%s", null);
        verifyLogOutput("Or Login Test for user=null or password=null");
    }

    @Test
    public void orMismatchArgTest() {
        LoggerService.OR("Login Test for user=%s or password=%s", "foobar");
        verifyLogOutput("Or Login Test for user= or password=foobar");
    }

    @Test
    public void orTooManyArgTest() {
        LoggerService.OR("Login Test for user=%s or password=%s", "foobar", "password", true);
        verifyLogOutput("Or Login Test for user=foobar or password=");
    }

    @Test
    public void orWithArgsTest() {
        LoggerService.OR("Login Test for user=%s or password=%s", "anthony", "foobar");
        verifyLogOutput("Or Login Test for user=anthony or password=foobar");
    }

    @Test
    public void orTest() {
        LoggerService.OR("Login Test for user");
        verifyLogOutput("Or Login Test for user");
    }

}
