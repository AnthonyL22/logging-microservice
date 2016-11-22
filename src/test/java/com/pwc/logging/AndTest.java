package com.pwc.logging;

import com.pwc.logging.service.LoggerService;
import org.junit.Test;

public class AndTest extends LoggerBaseTest {

    @Test
    public void andBlankTest() {
        LoggerService.AND("");
        verifyLogOutput("And");
    }

    @Test
    public void andNullTest() {
        LoggerService.AND(null);
        verifyLogOutput("And null");
    }

    @Test
    public void andNullArgTest() {
        LoggerService.AND("Login Test for user=%s and password=%s", null);
        verifyLogOutput("And Login Test for user=null and password=null");
    }

    @Test
    public void andMismatchArgTest() {
        LoggerService.AND("Login Test for user=%s and password=%s", "foobar");
        verifyLogOutput("And Login Test for user= and password=foobar");
    }

    @Test
    public void andTooManyArgTest() {
        LoggerService.AND("Login Test for user=%s and password=%s", "foobar", "password", true);
        verifyLogOutput("And Login Test for user=foobar and password=");
    }

    @Test
    public void andWithArgsTest() {
        LoggerService.AND("Login Test for user=%s and password=%s", "anthony", "foobar");
        verifyLogOutput("And Login Test for user=anthony and password=foobar");
    }

    @Test
    public void andTest() {
        LoggerService.AND("Login Test for user");
        verifyLogOutput("And Login Test for user");
    }

}
