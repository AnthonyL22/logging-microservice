package com.pwc.logging.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static com.pwc.logging.service.LoggerService.LOG;

public class VideoLoggerStandalone {

    private static final int DEFAULT_WIDTH = 1;
    private static final int DEFAULT_HEIGHT = 1;
    private static final int DEFAULT_FRAME_RATE = 2;
    private static final String DEFAULT_OUTPUT_FILE_NAME = "out.mov";

    private static int width;
    private static int height;
    private static int frameRate;
    private static String outputMovieFileName;
    private static String sourceFilesDirectoryURL;
    private static String outputURL;

    /**
     * Stand-alone Image to Video conversion tool.  Only arg[0] is Mandatory
     * arg[0] = Source Image Files Directory (Default - user's current directory)
     * arg[1] = video width (Default - width of first image)
     * arg[2] = video Height (Default - height of first image)
     * arg[3] = Frames/sec (Default - 2 frames/sec)
     * arg[4] = Output .mov file name (Default - 'out.mov')"
     * arg[5] = Output .mov file location (Default - Source file directory)
     *
     * @param args program settings
     */
    public static void main(String args[]) {

        if (args.length == 0) {
            displayHelp();
        }

        try {

            configureSettingsFromUser(args);

            VideoLoggerService videoLoggerService = new VideoLoggerService();
            videoLoggerService.setWidth(getWidth());
            videoLoggerService.setHeight(getHeight());
            videoLoggerService.setFrameRate(getFrameRate());
            videoLoggerService.setOutputMovieFileName(getOutputMovieFileName());
            videoLoggerService.setOutputDirectoryURL(getOutputURL());
            videoLoggerService.setSourceFilesDirectoryURL(getSourceFilesDirectoryURL());
            videoLoggerService.convert();

        } catch (Exception e) {

            LOG(true, "Failed to cast images to movie due to exception='%s'", e.getCause());

        }
    }

    /**
     * Interrogate the arguments sent from the calling user
     *
     * @param args program arguments
     */
    public static void configureSettingsFromUser(String[] args) {

        ArrayList<String> settings = new ArrayList(Arrays.asList(args));

        setSourceFilesDirectoryURL(settings.get(0) != null ? settings.get(0) : "");
        if (settings.size() > 1) {
            setWidth(settings.get(1) != null ? new Integer(settings.get(1)) : DEFAULT_WIDTH);
        } else {
            setWidth(DEFAULT_WIDTH);
        }
        if (settings.size() > 2) {
            setHeight(settings.get(2) != null ? new Integer(settings.get(2)) : DEFAULT_HEIGHT);
        } else {
            setHeight(DEFAULT_HEIGHT);
        }
        if (settings.size() > 3) {
            setFrameRate(frameRate = settings.get(3) != null ? new Integer(settings.get(3)) : DEFAULT_FRAME_RATE);
        } else {
            setFrameRate(DEFAULT_FRAME_RATE);
        }
        if (settings.size() > 4) {
            setOutputMovieFileName(settings.get(4) != null ? settings.get(4) : DEFAULT_OUTPUT_FILE_NAME);
        } else {
            setOutputMovieFileName(DEFAULT_OUTPUT_FILE_NAME);
        }
        if (settings.size() > 5) {
            setOutputURL(settings.get(5) != null ? ("file:" + settings.get(5)) : "file:" + sourceFilesDirectoryURL + File.separator + getOutputMovieFileName());
        } else {
            setOutputURL("file:" + sourceFilesDirectoryURL + File.separator + getOutputMovieFileName());
        }
    }

    /**
     * Display application Help and Examples
     */
    public static void displayHelp() {
        System.out.println("\n*************** CONFIGURATION HELP MENU ***************");
        System.out.println("\n args[0] = Source Image Files Directory (Default - user's current directory");
        System.out.println("\n args[1] = video Width (Default - width of first image)");
        System.out.println("\n args[2] = video Height (Default - height of first image)");
        System.out.println("\n args[3] = Frames/sec (Default - 2 frames/sec)");
        System.out.println("\n args[4] = Output .mov file name (Default - 'out.mov')");
        System.out.println("\n args[5] = Output .mov file location (Default - Source file directory from arg[0])");
        System.out.println("\n\nExample For Unix Users: java -cp logging-microservice-1.0.2.jar com.pwc.logging.service.VideoLoggerStandalone /User/username/Desktop/images 500 600 10 out.mov /User/username/Desktop/");
        System.out.println("\n\nExample For Windows Users: java -cp logging-microservice-1.0.2.jar com.pwc.logging.service.VideoLoggerStandalone C:\\images 500 600 10 out.mov C:\\temp\\");

    }

    public static int getWidth() {
        return width;
    }

    public static void setWidth(int width) {
        VideoLoggerStandalone.width = width;
    }

    public static int getHeight() {
        return height;
    }

    public static void setHeight(int height) {
        VideoLoggerStandalone.height = height;
    }

    public static int getFrameRate() {
        return frameRate;
    }

    public static void setFrameRate(int frameRate) {
        VideoLoggerStandalone.frameRate = frameRate;
    }

    public static String getOutputMovieFileName() {
        return outputMovieFileName;
    }

    public static void setOutputMovieFileName(String outputMovieFileName) {
        VideoLoggerStandalone.outputMovieFileName = outputMovieFileName;
    }

    public static String getSourceFilesDirectoryURL() {
        return sourceFilesDirectoryURL;
    }

    public static void setSourceFilesDirectoryURL(String sourceFilesDirectoryURL) {
        VideoLoggerStandalone.sourceFilesDirectoryURL = sourceFilesDirectoryURL;
    }

    public static String getOutputURL() {
        return outputURL;
    }

    public static void setOutputURL(String outputURL) {
        VideoLoggerStandalone.outputURL = outputURL;
    }

}