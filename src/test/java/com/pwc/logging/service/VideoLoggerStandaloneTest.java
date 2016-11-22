package com.pwc.logging.service;

import com.pwc.logging.LoggerBaseTest;
import com.pwc.logging.service.VideoLoggerStandalone;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testng.Assert;

import java.io.File;

public class VideoLoggerStandaloneTest extends LoggerBaseTest {

    private static final String USER_HOME = System.getProperty("user.home");
    private String[] settings;

    @Before
    public void beforeTest() {
        settings = new String[]{USER_HOME + File.separator + "files", "777", "1234", "4", "my_video.mov", USER_HOME + File.separator + "output"};
    }

    @After
    public void afterTest() {
    }

    @Test
    public void mainNoArgsTest() {
        VideoLoggerStandalone.main(new String[]{});
    }

    @Test
    public void mainTest() {
        VideoLoggerStandalone.main(settings);
    }

    @Test
    public void configureSettingsFromUserOnlyRequiredTest() {

        settings = new String[]{USER_HOME + File.separator + "files"};

        VideoLoggerStandalone.configureSettingsFromUser(settings);

        Assert.assertEquals(VideoLoggerStandalone.getSourceFilesDirectoryURL(), USER_HOME + File.separator + "files");
        Assert.assertEquals(VideoLoggerStandalone.getWidth(), 1);
        Assert.assertEquals(VideoLoggerStandalone.getHeight(), 1);
        Assert.assertEquals(VideoLoggerStandalone.getFrameRate(), 2);
        Assert.assertEquals(VideoLoggerStandalone.getOutputMovieFileName(), "out.mov");
        Assert.assertEquals(VideoLoggerStandalone.getOutputURL(), "file:" + USER_HOME + File.separator + "files" + File.separator + "out.mov");

    }

    @Test
    public void configureSettingsFromUserOptionalTest() {

        VideoLoggerStandalone.configureSettingsFromUser(settings);

        Assert.assertEquals(VideoLoggerStandalone.getSourceFilesDirectoryURL(), USER_HOME + File.separator + "files");
        Assert.assertEquals(VideoLoggerStandalone.getWidth(), 777);
        Assert.assertEquals(VideoLoggerStandalone.getHeight(), 1234);
        Assert.assertEquals(VideoLoggerStandalone.getFrameRate(), 4);
        Assert.assertEquals(VideoLoggerStandalone.getOutputMovieFileName(), "my_video.mov");
        Assert.assertEquals(VideoLoggerStandalone.getOutputURL(), "file:" + USER_HOME + File.separator + "output");

    }

    @Test
    public void displayHelpTest() {
        VideoLoggerStandalone.displayHelp();
    }

}
