package com.pwc.logging;


import com.pwc.logging.service.LoggerService;
import org.junit.Test;

public class NotTest extends LoggerBaseTest {

    @Test
    public void notBlankTest() {
        LoggerService.NOT("");
        verifyLogOutput("Not");
    }

    @Test
    public void notNullTest() {
        LoggerService.NOT(null);
        verifyLogOutput("Not null");
    }

    @Test
    public void notNullArgTest() {
        LoggerService.NOT("Login Test not user=%s not password=%s", null);
        verifyLogOutput("Not Login Test not user=null not password=null");
    }

    @Test
    public void notMismatchArgTest() {
        LoggerService.NOT("Login Test for user=%s and password=%s", "foobar");
        verifyLogOutput("Not Login Test for user= and password=foobar");
    }

    @Test
    public void notTooManyArgTest() {
        LoggerService.NOT("Login Test not user=%s not password=%s", "foobar", "password", true);
        verifyLogOutput("Not Login Test not user=foobar not password=");
    }

    @Test
    public void notWithArgsTest() {
        LoggerService.NOT("Login Test not user=%s not password=%s", "anthony", "foobar");
        verifyLogOutput("Not Login Test not user=anthony not password=foobar");
    }

    @Test
    public void notTest() {
        LoggerService.NOT("Login Test not user");
        verifyLogOutput("Not Login Test not user");
    }

}
