package com.pwc.logging;

import com.pwc.logging.service.LoggerService;
import org.junit.Test;

public class ImageTest extends LoggerBaseTest {

    @Test
    public void imageBlankTest() {
        LoggerService.IMAGE("");
        verifyLogOutput("Image");
    }

    @Test
    public void imageNullTest() {
        LoggerService.IMAGE(null);
        verifyLogOutput("Image null");
    }

    @Test
    public void imageNullArgTest() {
        LoggerService.IMAGE("Login Test for user=%s and password=%s", null);
        verifyLogOutput("Image Login Test for user=null and password=null");
    }

    @Test
    public void imageMismatchArgTest() {
        LoggerService.IMAGE("Login Test for user=%s and password=%s", "foobar");
        verifyLogOutput("Image Login Test for user= and password=foobar");
    }

    @Test
    public void imageTooManyArgTest() {
        LoggerService.IMAGE("Login Test for user=%s and password=%s", "foobar", "password", true);
        verifyLogOutput("Image Login Test for user=foobar and password=");
    }

    @Test
    public void imageWithArgsTest() {
        LoggerService.IMAGE("Login Test for user=%s and password=%s", "anthony", "foobar");
        verifyLogOutput("Image Login Test for user=anthony and password=foobar");
    }

    @Test
    public void imageTest() {
        LoggerService.IMAGE("Login Test for user");
        verifyLogOutput("Image Login Test for user");
    }

}
