package com.pwc.logging;

import org.junit.Test;

public class FeatureTest extends LoggerBaseTest {

    @Test
    public void featureBlankTest() {
        LoggerService.FEATURE("");
        verifyLogOutput("Feature");
    }

    @Test
    public void featureNullTest() {
        LoggerService.FEATURE(null);
        verifyLogOutput("Feature: null");
    }

    @Test
    public void featureNullArgTest() {
        LoggerService.FEATURE("Login Test for user=%s and password=%s", null);
        verifyLogOutput("Feature: Login Test for user=null and password=null");
    }

    @Test
    public void featureMismatchArgTest() {
        LoggerService.FEATURE("Login Test for user=%s and password=%s", "foobar");
        verifyLogOutput("Feature: Login Test for user= and password=foobar");
    }

    @Test
    public void featureTooManyArgTest() {
        LoggerService.FEATURE("Login Test for user=%s and password=%s", "foobar", "password", true);
        verifyLogOutput("Feature: Login Test for user=foobar and password=");
    }

    @Test
    public void featureWithArgsTest() {
        LoggerService.FEATURE("Login Test for user=%s and password=%s", "anthony", "foobar");
        verifyLogOutput("Feature: Login Test for user=anthony and password=foobar");
    }

    @Test
    public void featureTest() {
        LoggerService.FEATURE("Login Test for user");
        verifyLogOutput("Feature: Login Test for user");
    }

}
