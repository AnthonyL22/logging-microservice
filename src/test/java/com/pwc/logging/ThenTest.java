package com.pwc.logging;

import com.pwc.logging.service.LoggerService;
import org.junit.Test;

public class ThenTest extends LoggerBaseTest {

    @Test
    public void thenBlankTest() {
        LoggerService.THEN("");
        verifyLogOutput("Then");
    }

    @Test
    public void thenNullTest() {
        LoggerService.THEN(null);
        verifyLogOutput("Then null");
    }

    @Test
    public void thenNullArgTest() {
        LoggerService.THEN("Login Test for user=%s and password=%s", null);
        verifyLogOutput("Then Login Test for user=null and password=null");
    }

    @Test
    public void thenMismatchArgTest() {
        LoggerService.THEN("Login Test for user=%s and password=%s", "foobar");
        verifyLogOutput("Then Login Test for user= and password=foobar");
    }

    @Test
    public void thenTooManyArgTest() {
        LoggerService.THEN("Login Test for user=%s and password=%s", "foobar", "password", true);
        verifyLogOutput("Then Login Test for user=foobar and password=");
    }

    @Test
    public void thenWithArgsTest() {
        LoggerService.THEN("Login Test for user=%s and password=%s", "anthony", "foobar");
        verifyLogOutput("Then Login Test for user=anthony and password=foobar");
    }

    @Test
    public void thenTest() {
        LoggerService.THEN("Login Test for user");
        verifyLogOutput("Then Login Test for user");
    }

}
