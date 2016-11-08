package com.qualcomm.qherkin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LogTest extends LoggerBaseTest {

    @Test(expected = AssertionError.class)
    public void failLogExceptionTest() {
        QherkinLoggerService.LOG(true, "Login Test", new AssertionError("Resulted in a failure"));
        verifyLogOutput("Login Test - Exception='Resulted in a failure'");
    }

    @Test(expected = NullPointerException.class)
    public void failLogTest() {
        QherkinLoggerService.LOG(true, null, "Test Passed");
    }

    @Test
    public void failLogNullTest() {
        QherkinLoggerService.LOG(null);
        verifyLogOutput("");
    }

    @Test
    public void logExceptionTest() {
        QherkinLoggerService.LOG("Login Test", new NullPointerException("Resulted in a failure"));
        verifyLogOutput("Login Test - Exception='Resulted in a failure'");
    }

    @Test
    public void logWithArgsTest() {
        QherkinLoggerService.LOG(true, "Login Test result = %s page = %s response = %s", "PASS", 1, true);
        verifyLogOutput("Login Test result = PASS page = 1 response = true");
    }

    @Test
    public void logMismatchArgTest() {
        QherkinLoggerService.LOG(true, "Login Test result=%s page=%s", "PASS");
        verifyLogOutput("Login Test result= page=PASS");
    }

    @Test
    public void logNoStringFormattingTest() {
        QherkinLoggerService.LOG(true, "Login Test result=%s page=%s");
        verifyLogOutputWithoutDate("Login Test result=%s page=%s");
    }

    @Test
    public void logTest() {
        QherkinLoggerService.LOG("Login Test result PASS");
        verifyLogOutput("Login Test result PASS");
    }

    @Test
    public void doNotLogToStandardOutTest() {
        QherkinLoggerService.LOG(false, "Login Test result PASS");
        verifyLogOutputWithoutDate("Login Test result PASS");
    }

    @Test
    public void logToStandardOutTest() {
        QherkinLoggerService.LOG(true, "Login Test result PASS");
        verifyLogOutputWithoutDate("Login Test result PASS");
    }

}
