package com.pwc.logging.service;

import com.pwc.logging.LoggerBaseTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testng.Assert;

import java.io.File;

public class VideoLoggerTest extends LoggerBaseTest {

    private static final String USER_HOME = System.getProperty("user.home");
    private String[] settings;

    @Before
    public void beforeTest() {
        settings = new String[] {USER_HOME + File.separator + "files", "777", "1234", "4", "my_video.mov", USER_HOME + File.separator + "output"};
    }

    @After
    public void afterTest() {
    }

    @Test
    public void mainNoArgsTest() {
        VideoLogger.main(new String[] {});
    }

    @Test
    public void mainTest() {
        VideoLogger.main(settings);
    }

    @Test
    public void configureSettingsFromUserOnlyRequiredTest() {

        settings = new String[] {USER_HOME + File.separator + "files"};

        VideoLogger.configureSettingsFromUser(settings);

        Assert.assertEquals(VideoLogger.getSourceFilesDirectoryURL(), USER_HOME + File.separator + "files");
        Assert.assertEquals(VideoLogger.getWidth(), 1);
        Assert.assertEquals(VideoLogger.getHeight(), 1);
        Assert.assertEquals(VideoLogger.getFrameRate(), 2);
        Assert.assertEquals(VideoLogger.getOutputMovieFileName(), "out.mov");
        Assert.assertEquals(VideoLogger.getOutputURL(), "file:" + USER_HOME + File.separator + "files" + File.separator + "out.mov");

    }

    @Test
    public void configureSettingsFromUserOptionalTest() {

        VideoLogger.configureSettingsFromUser(settings);

        Assert.assertEquals(VideoLogger.getSourceFilesDirectoryURL(), USER_HOME + File.separator + "files");
        Assert.assertEquals(VideoLogger.getWidth(), 777);
        Assert.assertEquals(VideoLogger.getHeight(), 1234);
        Assert.assertEquals(VideoLogger.getFrameRate(), 4);
        Assert.assertEquals(VideoLogger.getOutputMovieFileName(), "my_video.mov");
        Assert.assertEquals(VideoLogger.getOutputURL(), "file:" + USER_HOME + File.separator + "output");

    }

    @Test
    public void displayHelpTest() {
        VideoLogger.displayHelp();
    }

}
