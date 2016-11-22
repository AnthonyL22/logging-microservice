package com.pwc.logging;

import com.pwc.logging.service.LoggerService;
import org.junit.Test;

public class ScenarioTest extends LoggerBaseTest {

    @Test
    public void scenarioBlankTest() {
        LoggerService.SCENARIO("");
        verifyLogOutput("Scenario:");
    }

    @Test
    public void scenarioNullTest() {
        LoggerService.SCENARIO(null);
        verifyLogOutput("Scenario: null");
    }

    @Test
    public void scenarioNullArgTest() {
        LoggerService.SCENARIO("Login Test for user=%s and password=%s", null);
        verifyLogOutput("Scenario: Login Test for user=null and password=null");
    }

    @Test
    public void scenarioMismatchArgTest() {
        LoggerService.SCENARIO("Login Test for user=%s and password=%s", "foobar");
        verifyLogOutput("Scenario: Login Test for user= and password=foobar");
    }

    @Test
    public void scenarioTooManyArgTest() {
        LoggerService.SCENARIO("Login Test for user=%s and password=%s", "foobar", "password", true);
        verifyLogOutput("Scenario: Login Test for user=foobar and password=");
    }

    @Test
    public void scenarioWithArgsTest() {
        LoggerService.SCENARIO("Login Test for user=%s and password=%s", "anthony", "foobar");
        verifyLogOutput("Scenario: Login Test for user=anthony and password=foobar");
    }

    @Test
    public void scenarioTest() {
        LoggerService.SCENARIO("Login Test for user");
        verifyLogOutput("Scenario: Login Test for user");
    }

}
