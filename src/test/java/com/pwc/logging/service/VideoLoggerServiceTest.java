package com.pwc.logging.service;

import com.pwc.logging.LoggerBaseTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testng.Assert;

import java.io.File;
import java.net.URL;
import java.util.HashMap;

public class VideoLoggerServiceTest extends LoggerBaseTest {

    private VideoLoggerService mockVideoLoggerService;
    private URL sourceFilesDirectory;

    @Before
    public void beforeTest() {

        mockVideoLoggerService = new VideoLoggerService();
        sourceFilesDirectory = VideoLoggerService.class.getClassLoader().getResource("files");

        mockVideoLoggerService.setWidth(400);
        mockVideoLoggerService.setHeight(200);
        mockVideoLoggerService.setFrameRate(4);
        mockVideoLoggerService.setSourceFilesDirectoryURL(sourceFilesDirectory.getPath());
        mockVideoLoggerService.setOutputMovieFileName("unit_test.mov");
        mockVideoLoggerService.setOutputDirectoryURL(sourceFilesDirectory.getPath() + File.separator + "unit_test.mov");

    }

    @After
    public void afterTest() {
        File movieFile = new File(sourceFilesDirectory.getPath() + File.separator + "unit_test.mov");
        if (movieFile.exists()) {
            movieFile.delete();
        }
    }

    @Test
    public void cleanUpConversionTest() {
        mockVideoLoggerService.setOutputMovieFileName("unknown.mov");

        mockVideoLoggerService.cleanUpConversion();
        File movieFile = new File(sourceFilesDirectory.getPath() + File.separator + "sample.mov");
        Assert.assertTrue(movieFile.exists());
    }

    @Test
    public void convertNullFilesTest() {

        mockVideoLoggerService.setSourceFilesDirectoryURL("foobar-directory");

        mockVideoLoggerService.convert();

        Assert.assertEquals(mockVideoLoggerService.getWidth(), 400);
        Assert.assertEquals(mockVideoLoggerService.getHeight(), 200);
        Assert.assertEquals(mockVideoLoggerService.getFrameRate(), 4);
        Assert.assertNotEquals(mockVideoLoggerService.getSourceFilesDirectoryURL(), sourceFilesDirectory.getPath());
    }

    @Test
    public void prepareVideoConversionTest() {
        HashMap settings = mockVideoLoggerService.prepareVideoConversion();

        Assert.assertEquals(settings.get("width"), 400);
        Assert.assertEquals(settings.get("height"), 200);
        Assert.assertEquals(settings.get("frameRate"), 4);
        Assert.assertEquals(settings.get("sourceFilesDirectory"), sourceFilesDirectory.getPath());
        Assert.assertEquals(mockVideoLoggerService.getOutputMovieFileName(), "unit_test.mov");
    }

    @Test
    public void prepareVideoConversionNoSourceFilesTest() {
        mockVideoLoggerService.setSourceFilesDirectoryURL("foobar");

        mockVideoLoggerService.prepareVideoConversion();
        Assert.assertEquals(mockVideoLoggerService.getSourceFilesDirectoryURL(), "foobar");
    }

    @Test
    public void prepareVideoConversionNoWidthNoHeightTest() {
        mockVideoLoggerService.setWidth(0);
        mockVideoLoggerService.setHeight(0);

        HashMap settings = mockVideoLoggerService.prepareVideoConversion();
        Assert.assertEquals(settings.get("width"), 1600);
        Assert.assertEquals(settings.get("height"), 804);
    }

    @Test
    public void prepareVideoConversionDefinedWidthNoHeightTest() {
        HashMap settings = mockVideoLoggerService.prepareVideoConversion();
        Assert.assertEquals(settings.get("width"), 400);
        Assert.assertEquals(settings.get("height"), 200);
    }

    @Test
    public void outputDirectoryURLInvalidExtensionTest() {
        mockVideoLoggerService.setOutputDirectoryURL(sourceFilesDirectory.getPath() + File.separator + "temp" + File.separator + "badfile.wav");

        HashMap settings = mockVideoLoggerService.prepareVideoConversion();
        Assert.assertTrue(settings.get("outputDirectory").toString().endsWith("badfile.mov"));

    }


}
