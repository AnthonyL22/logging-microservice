package com.pwc.logging;

import com.pwc.logging.service.LoggerService;
import org.junit.Test;

public class IfTest extends LoggerBaseTest {

    @Test
    public void ifBlankTest() {
        LoggerService.IF("");
        verifyLogOutput("If");
    }

    @Test
    public void ifNullTest() {
        LoggerService.IF(null);
        verifyLogOutput("If null");
    }

    @Test
    public void ifNullArgTest() {
        LoggerService.IF("Login Test if user=%s if password=%s", null);
        verifyLogOutput("If Login Test if user=null if password=null");
    }

    @Test
    public void ifMismatchArgTest() {
        LoggerService.IF("Login Test if user=%s if password=%s", "foobar");
        verifyLogOutput("If Login Test if user= if password=foobar");
    }

    @Test
    public void ifTooManyArgTest() {
        LoggerService.IF("Login Test if user=%s if password=%s", "foobar", "password", true);
        verifyLogOutput("If Login Test if user=foobar if password=");
    }

    @Test
    public void ifWithArgsTest() {
        LoggerService.IF("Login Test if user=%s if password=%s", "anthony", "foobar");
        verifyLogOutput("If Login Test if user=anthony if password=foobar");
    }

    @Test
    public void ifTest() {
        LoggerService.IF("Login Test if user");
        verifyLogOutput("If Login Test if user");
    }

}
