package com.pwc.logging;

import org.junit.Test;

public class WhenTest extends LoggerBaseTest {

    @Test
    public void whenBlankTest() {
        LoggerService.WHEN("");
        verifyLogOutput("When");
    }

    @Test
    public void whenNullTest() {
        LoggerService.WHEN(null);
        verifyLogOutput("When null");
    }

    @Test
    public void whenNullArgTest() {
        LoggerService.WHEN("Login Test for user=%s and password=%s", null);
        verifyLogOutput("When Login Test for user=null and password=null");
    }

    @Test
    public void whenMismatchArgTest() {
        LoggerService.WHEN("Login Test for user=%s and password=%s", "foobar");
        verifyLogOutput("When Login Test for user= and password=foobar");
    }

    @Test
    public void whenTooManyArgTest() {
        LoggerService.WHEN("Login Test for user=%s and password=%s", "foobar", "password", true);
        verifyLogOutput("When Login Test for user=foobar and password=");
    }

    @Test
    public void whenWithArgsTest() {
        LoggerService.WHEN("Login Test for user=%s and password=%s", "anthony", "foobar");
        verifyLogOutput("When Login Test for user=anthony and password=foobar");
    }

    @Test
    public void whenTest() {
        LoggerService.WHEN("Login Test for user");
        verifyLogOutput("When Login Test for user");
    }

}
