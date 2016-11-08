package com.pwc.logging;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LogTest extends LoggerBaseTest {

    @Test(expected = AssertionError.class)
    public void failLogExceptionTest() {
        LoggerService.LOG(true, "Login Test", new AssertionError("Resulted in a failure"));
        verifyLogOutput("Login Test - Exception='Resulted in a failure'");
    }

    @Test(expected = NullPointerException.class)
    public void failLogTest() {
        LoggerService.LOG(true, null, "Test Passed");
    }

    @Test
    public void failLogNullTest() {
        LoggerService.LOG(null);
        verifyLogOutput("");
    }

    @Test
    public void logExceptionTest() {
        LoggerService.LOG("Login Test", new NullPointerException("Resulted in a failure"));
        verifyLogOutput("Login Test - Exception='Resulted in a failure'");
    }

    @Test
    public void logWithArgsTest() {
        LoggerService.LOG(true, "Login Test result = %s page = %s response = %s", "PASS", 1, true);
        verifyLogOutput("Login Test result = PASS page = 1 response = true");
    }

    @Test
    public void logMismatchArgTest() {
        LoggerService.LOG(true, "Login Test result=%s page=%s", "PASS");
        verifyLogOutput("Login Test result= page=PASS");
    }

    @Test
    public void logNoStringFormattingTest() {
        LoggerService.LOG(true, "Login Test result=%s page=%s");
        verifyLogOutputWithoutDate("Login Test result=%s page=%s");
    }

    @Test
    public void logTest() {
        LoggerService.LOG("Login Test result PASS");
        verifyLogOutput("Login Test result PASS");
    }

    @Test
    public void doNotLogToStandardOutTest() {
        LoggerService.LOG(false, "Login Test result PASS");
        verifyLogOutputWithoutDate("Login Test result PASS");
    }

    @Test
    public void logToStandardOutTest() {
        LoggerService.LOG(true, "Login Test result PASS");
        verifyLogOutputWithoutDate("Login Test result PASS");
    }

}
