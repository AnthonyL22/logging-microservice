package com.qualcomm.qherkin;

import org.junit.Test;

public class FeatureTest extends LoggerBaseTest {

    @Test
    public void featureBlankTest() {
        QherkinLoggerService.FEATURE("");
        verifyLogOutput("Feature");
    }

    @Test
    public void featureNullTest() {
        QherkinLoggerService.FEATURE(null);
        verifyLogOutput("Feature: null");
    }

    @Test
    public void featureNullArgTest() {
        QherkinLoggerService.FEATURE("Login Test for user=%s and password=%s", null);
        verifyLogOutput("Feature: Login Test for user=null and password=null");
    }

    @Test
    public void featureMismatchArgTest() {
        QherkinLoggerService.FEATURE("Login Test for user=%s and password=%s", "foobar");
        verifyLogOutput("Feature: Login Test for user= and password=foobar");
    }

    @Test
    public void featureTooManyArgTest() {
        QherkinLoggerService.FEATURE("Login Test for user=%s and password=%s", "foobar", "password", true);
        verifyLogOutput("Feature: Login Test for user=foobar and password=");
    }

    @Test
    public void featureWithArgsTest() {
        QherkinLoggerService.FEATURE("Login Test for user=%s and password=%s", "anthony", "foobar");
        verifyLogOutput("Feature: Login Test for user=anthony and password=foobar");
    }

    @Test
    public void featureTest() {
        QherkinLoggerService.FEATURE("Login Test for user");
        verifyLogOutput("Feature: Login Test for user");
    }

}
