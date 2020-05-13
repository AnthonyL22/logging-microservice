package com.pwc.logging.service;

import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import javax.media.Buffer;
import javax.media.ConfigureCompleteEvent;
import javax.media.Controller;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.DataSink;
import javax.media.EndOfMediaEvent;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.PrefetchCompleteEvent;
import javax.media.Processor;
import javax.media.RealizeCompleteEvent;
import javax.media.ResourceUnavailableEvent;
import javax.media.Time;
import javax.media.control.TrackControl;
import javax.media.datasink.DataSinkErrorEvent;
import javax.media.datasink.DataSinkEvent;
import javax.media.datasink.DataSinkListener;
import javax.media.datasink.EndOfStreamEvent;
import javax.media.format.VideoFormat;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.DataSource;
import javax.media.protocol.FileTypeDescriptor;
import javax.media.protocol.PullBufferDataSource;
import javax.media.protocol.PullBufferStream;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import static com.pwc.logging.service.LoggerService.LOG;

public class VideoLoggerService implements ControllerListener, DataSinkListener {

    private int width;
    private int height;
    private int frameRate;
    private String sourceFilesDirectoryURL;
    private String outputMovieFileName;
    private String outputDirectoryURL;

    private final int DEFAULT_FRAME_RATE = 2;
    private final String DEFAULT_OUTPUT_FILE_NAME = "out.mov";

    public VideoLoggerService() {
    }

    /**
     * Convert image files to single video file
     *
     * @param width                video width
     * @param height               video height
     * @param frameRate            frame rate/second
     * @param sourceFilesDirectory image source file directory location
     * @param outputMovieFileName  output video file name
     * @param outputDirectory      output directory location
     */
    public VideoLoggerService(final int width, final int height, final int frameRate, final String sourceFilesDirectory, final String outputMovieFileName, final String outputDirectory) {
        this.width = width;
        this.height = height;
        this.frameRate = frameRate;
        this.sourceFilesDirectoryURL = sourceFilesDirectory;
        this.outputMovieFileName = outputMovieFileName;
        this.outputDirectoryURL = outputDirectory;
    }

    /**
     * Main entry point for conversion of images to video.
     */
    public void convert() {

        try {

            width = width > -1 ? width : 1;
            height = height > -1 ? height : 1;
            frameRate = frameRate > -1 ? frameRate : DEFAULT_FRAME_RATE;
            sourceFilesDirectoryURL = sourceFilesDirectoryURL != null ? sourceFilesDirectoryURL : "";
            outputMovieFileName = outputMovieFileName != null ? outputMovieFileName : DEFAULT_OUTPUT_FILE_NAME;
            outputDirectoryURL = outputDirectoryURL != null ? StringUtils.prependIfMissing(outputDirectoryURL, "file:") + File.separator + outputMovieFileName :
                    StringUtils.prependIfMissing(sourceFilesDirectoryURL, "file:") + File.separator + outputMovieFileName;

            HashMap videoSettings = prepareVideoConversion();

            if (videoSettings.get("files") != null) {

                MediaLocator oml;
                if ((oml = createMediaLocator(outputDirectoryURL)) == null) {
                    LOG(true, "Cannot build media locator from url='%s'", outputDirectoryURL);
                }

                VideoLoggerService service = new VideoLoggerService();
                service.performConversion(Integer.parseInt(videoSettings.get("width").toString()),
                        Integer.parseInt(videoSettings.get("height").toString()),
                        Integer.parseInt(videoSettings.get("frameRate").toString()),
                        (Vector) videoSettings.get("files"), oml);

                cleanUpConversion();

            }

        } catch (Exception e) {

            LOG(true, "Failed to cast images to movie due to exception='%s'", e.getCause());

        }
    }

    /**
     * Prepare all properties and directories for conversion of images to video.
     *
     * @return Map of settings
     */
    protected HashMap prepareVideoConversion() {

        HashMap settings = new HashMap();

        try {

            Vector inputFiles = new Vector();
            BufferedImage img;
            File sourceFiles = new File(sourceFilesDirectoryURL);
            String[] files = sourceFiles.list();
            if (!sourceFiles.exists() || files.length == 0) {
                LOG(true, "No image files present in directory='%s'", sourceFilesDirectoryURL);
            } else if (files.length > 0) {
                Arrays.sort(files);
                for (String file : files) {
                    if (StringUtils.containsIgnoreCase(file, ".jpg") || StringUtils.containsIgnoreCase(file, ".jpeg") || StringUtils.containsIgnoreCase(file, ".png"))
                        inputFiles.addElement(sourceFiles.getParent() + System.getProperty("file.separator") + sourceFiles.getName() + System.getProperty("file.separator") + file);
                }
            }


            img = ImageIO.read(new File((String) inputFiles.elementAt(0)));
            if (img != null && width < 1 && height < 1) {
                width = img.getWidth();
                height = img.getHeight();
            }

            if (!StringUtils.endsWithIgnoreCase(outputDirectoryURL, ".mov")) {
                LOG(true, "The output file extension should end with a .mov extension");
                LOG(true, "Appending .mov extension...");
                outputDirectoryURL = StringUtils.substringBeforeLast(outputDirectoryURL, ".") + ".mov";
            }

            File movieFile = new File(sourceFilesDirectoryURL + File.separator + outputMovieFileName);
            File sourceFileDir = new File(sourceFilesDirectoryURL);
            if (movieFile.exists()) {
                FilenameFilter filter = new MovieFileFilter();
                for (File file : sourceFileDir.listFiles(filter)) {
                    file.delete();
                }
            }

            settings.put("sourceFilesDirectory", sourceFilesDirectoryURL);
            settings.put("files", inputFiles);
            settings.put("width", width);
            settings.put("height", height);
            settings.put("frameRate", frameRate);
            settings.put("outputDirectory", outputDirectoryURL);

            LOG(true, "Source File Directory = '%s'", settings.get("sourceFilesDirectory"));
            LOG(true, "Number of Files Converted = %s", inputFiles.size());
            LOG(true, "Output File Directory = '%s'", settings.get("outputDirectory"));

        } catch (Exception e) {
            LOG(true, "Failed to prepare files for conversion due to exception='%s'", e.getMessage());
        }

        return settings;

    }

    /**
     * If movie was successfully created, then delete all image files used to the create movie post production
     */
    protected void cleanUpConversion() {
        File movieFile = new File(sourceFilesDirectoryURL + File.separator + outputMovieFileName);
        if (movieFile.exists()) {
            FilenameFilter filter = new ImageFileFilter();
            File sourceFileDir = new File(sourceFilesDirectoryURL);
            for (File file : sourceFileDir.listFiles(filter)) {
                file.delete();
            }
        }
    }

    /**
     * File filter for .jpg files
     */
    public static class ImageFileFilter implements FilenameFilter {
        @Override
        public boolean accept(File directory, String fileName) {
            return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg");
        }
    }

    /**
     * File filter for .mov files
     */
    public static class MovieFileFilter implements FilenameFilter {
        @Override
        public boolean accept(File directory, String fileName) {
            return fileName.endsWith(".mov");
        }
    }

    /**
     * Perform image to movie converstion
     *
     * @param width     movie width
     * @param height    movie height
     * @param frameRate frame rate per second
     * @param inFiles   image file list to construct video
     * @param outML     locator
     * @return success flag
     */
    protected boolean performConversion(int width, int height, int frameRate, Vector inFiles, MediaLocator outML) {

        ImageDataSource ids = new ImageDataSource(width, height, frameRate, inFiles);
        Processor p;

        try {
            System.out.println("- create processor for the image datasource ...");
            p = Manager.createProcessor(ids);
        } catch (Exception e) {
            System.err.println("Yikes!  Cannot create a processor from the data source.");
            return false;
        }

        p.addControllerListener(this);

        p.configure();
        if (!waitForState(p, Processor.Configured)) {
            System.err.println("Failed to configure the processor.");
            return false;
        }

        p.setContentDescriptor(new ContentDescriptor(FileTypeDescriptor.QUICKTIME));

        TrackControl tcs[] = p.getTrackControls();
        System.out.println("# track controls = " + tcs.length);
        for (TrackControl tc : tcs) {
            Format ff[] = tc.getSupportedFormats();

            for (Format aFf : ff) {
                System.out.println("  track control " + aFf);
            }
        }

        Format f[] = tcs[0].getSupportedFormats();
        if (f == null || f.length <= 0) {
            System.err.println("The mux does not support the input format: " + tcs[0].getFormat());
            return false;
        }

        String[] formatNames = ImageIO.getReaderFormatNames();
        for (String formatName : formatNames) {
            System.out.println("input format: " + formatName);
        }

        for (Format aF : f) {
            System.out.println("input format: " + aF);
        }

        tcs[0].setFormat(f[0]);

        System.out.println("Setting the track format to: " + f[0]);

        p.realize();
        if (!waitForState(p, Controller.Realized)) {
            System.err.println("Failed to realize the processor.");
            return false;
        }

        DataSink dsink;
        if ((dsink = createDataSink(p, outML)) == null) {
            System.err.println("Failed to create a DataSink for the given output MediaLocator: " + outML);
            return false;
        }

        dsink.addDataSinkListener(this);
        fileDone = false;

        System.out.println("start processing...");

        try {
            p.start();
            dsink.start();
        } catch (IOException e) {
            System.err.println("IO error during processing");
            return false;
        }

        waitForFileDone();

        try {
            dsink.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        p.removeControllerListener(this);

        System.out.println("...done processing.");

        return true;
    }

    /**
     * Create the DataSink.
     */
    DataSink createDataSink(Processor p, MediaLocator outML) {

        DataSource ds;

        if ((ds = p.getDataOutput()) == null) {
            System.err.println("Something is really wrong: the processor does not have an output DataSource");
            return null;
        }

        DataSink dsink;

        try {
            System.out.println("- create DataSink for: " + outML);
            dsink = Manager.createDataSink(ds, outML);
            dsink.open();
        } catch (Exception e) {
            System.err.println("Cannot create the DataSink: " + e);
            return null;
        }

        return dsink;
    }

    final Object waitSync = new Object();
    boolean stateTransitionOK = true;

    /**
     * Block until the processor has transitioned to the given state.
     * Return false if the transition failed.
     *
     * @param p     Processor
     * @param state state
     */
    boolean waitForState(Processor p, int state) {
        synchronized (waitSync) {
            try {
                while (p.getState() < state && stateTransitionOK)
                    waitSync.wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return stateTransitionOK;
    }

    /**
     * Controller Listener.
     */
    public void controllerUpdate(ControllerEvent evt) {

        if (evt instanceof ConfigureCompleteEvent ||
                evt instanceof RealizeCompleteEvent ||
                evt instanceof PrefetchCompleteEvent) {
            synchronized (waitSync) {
                stateTransitionOK = true;
                waitSync.notifyAll();
            }
        } else if (evt instanceof ResourceUnavailableEvent) {
            synchronized (waitSync) {
                stateTransitionOK = false;
                waitSync.notifyAll();
            }
        } else if (evt instanceof EndOfMediaEvent) {
            evt.getSourceController().stop();
            evt.getSourceController().close();
        }
    }

    Object waitFileSync = new Object();
    boolean fileDone = false;
    boolean fileSuccess = true;

    /**
     * Block until file writing is done.
     */
    boolean waitForFileDone() {
        synchronized (waitFileSync) {
            try {
                while (!fileDone)
                    waitFileSync.wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fileSuccess;
    }

    /**
     * Event handler for the file writer.
     */
    public void dataSinkUpdate(DataSinkEvent evt) {

        if (evt instanceof EndOfStreamEvent) {
            synchronized (waitFileSync) {
                fileDone = true;
                waitFileSync.notifyAll();
            }
        } else if (evt instanceof DataSinkErrorEvent) {
            synchronized (waitFileSync) {
                fileDone = true;
                fileSuccess = false;
                waitFileSync.notifyAll();
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(int frameRate) {
        this.frameRate = frameRate;
    }

    public String getSourceFilesDirectoryURL() {
        return sourceFilesDirectoryURL;
    }

    public void setSourceFilesDirectoryURL(String sourceFilesDirectoryURL) {
        this.sourceFilesDirectoryURL = sourceFilesDirectoryURL;
    }

    public String getOutputMovieFileName() {
        return outputMovieFileName;
    }

    public void setOutputMovieFileName(String outputMovieFileName) {
        this.outputMovieFileName = outputMovieFileName;
    }

    public String getOutputDirectoryURL() {
        return outputDirectoryURL;
    }

    public void setOutputDirectoryURL(String outputDirectoryURL) {
        this.outputDirectoryURL = outputDirectoryURL;
    }

    /**
     * Create a media locator from the given string.
     */
    static MediaLocator createMediaLocator(String url) {

        MediaLocator ml;

        if (url.indexOf(":") > 0 && (ml = new MediaLocator(url)) != null)
            return ml;

        if (url.startsWith(File.separator)) {
            if ((ml = new MediaLocator("file:" + url)) != null)
                return ml;
        } else {
            String file = "file:" + System.getProperty("user.dir") + File.separator + url;
            if ((ml = new MediaLocator(file)) != null)
                return ml;
        }

        return null;
    }

    /**
     * A DataSource to read from a list of JPEG image files and
     * turn that into a stream of JMF buffers.
     * The DataSource is not seekable or positionable.
     */
    class ImageDataSource extends PullBufferDataSource {

        ImageSourceStream streams[];

        ImageDataSource(int width, int height, int frameRate, Vector images) {
            streams = new ImageSourceStream[1];
            streams[0] = new ImageSourceStream(width, height, frameRate, images);
        }

        public void setLocator(MediaLocator source) {
        }

        public MediaLocator getLocator() {
            return null;
        }

        /**
         * Content type is of RAW since we are sending buffers of video
         * frames without a container format.
         */
        public String getContentType() {
            return ContentDescriptor.RAW;
        }

        public void connect() {
        }

        public void disconnect() {
        }

        public void start() {
        }

        public void stop() {
        }

        /**
         * Return the ImageSourceStreams.
         */
        public PullBufferStream[] getStreams() {
            return streams;
        }

        /**
         * We could have derived the duration from the number of
         * frames and frame rate.  But for the purpose of this program,
         * it's not necessary.
         */
        public Time getDuration() {
            return DURATION_UNKNOWN;
        }

        public Object[] getControls() {
            return new Object[0];
        }

        public Object getControl(String type) {
            return null;
        }
    }

    /**
     * The source stream to go along with ImageDataSource.
     */
    class ImageSourceStream implements PullBufferStream {
        Vector images;
        int width, height;
        VideoFormat format;

        int nextImage = 0;    // index of the next image to be read.
        boolean ended = false;

        public ImageSourceStream(int width, int height, int frameRate, Vector images) {
            this.width = width;
            this.height = height;
            this.images = images;

            format = new VideoFormat(VideoFormat.JPEG,
                    new Dimension(width, height),
                    Format.NOT_SPECIFIED,
                    Format.byteArray,
                    (float) frameRate);
        }

        /**
         * We should never need to block assuming data are read from files.
         */
        public boolean willReadBlock() {
            return false;
        }

        /**
         * Convert any PNG source files to JPEG file types for movie creation
         *
         * @param imageFile source file (PNG or JPEG)
         * @return target file name
         */
        private String convertImageToJpeg(String imageFile) {

            try {
                if (imageFile.endsWith(".png")) {
                    LOG(true, "Converting PNG image='%s' to .jpeg and deleting original .png", imageFile);
                    File pngFile = new File(imageFile);  // read the png
                    BufferedImage inputImage = ImageIO.read(pngFile);

                    imageFile = StringUtils.substringBeforeLast(imageFile, ".png");
                    File jpgFile = new File(imageFile + ".jpg");
                    ImageIO.write(inputImage, "jpg", jpgFile);
                    imageFile = imageFile + ".jpg";
                    if (pngFile.exists()) {
                        pngFile.delete();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return imageFile;
        }

        /**
         * This is called from the Processor to read a frame worth
         * of video data.
         */
        public void read(Buffer buf) throws IOException {

            if (nextImage >= images.size()) {
                buf.setEOM(true);
                buf.setOffset(0);
                buf.setLength(0);
                ended = true;
                return;
            }

            String imageFile = (String) images.elementAt(nextImage);
            nextImage++;

            imageFile = convertImageToJpeg(imageFile);

            RandomAccessFile raFile = new RandomAccessFile(imageFile, "r");

            byte data[] = null;

            if (buf.getData() instanceof byte[]) {
                data = (byte[]) buf.getData();
            }

            if (data == null || data.length < raFile.length()) {
                data = new byte[(int) raFile.length()];
                buf.setData(data);
            }

            raFile.readFully(data, 0, (int) raFile.length());

            buf.setOffset(0);
            buf.setLength((int) raFile.length());
            buf.setFormat(format);
            buf.setFlags(buf.getFlags() | Buffer.FLAG_KEY_FRAME);

            raFile.close();
        }

        public Format getFormat() {
            return format;
        }

        public ContentDescriptor getContentDescriptor() {
            return new ContentDescriptor(ContentDescriptor.RAW);
        }

        public long getContentLength() {
            return 0;
        }

        public boolean endOfStream() {
            return ended;
        }

        public Object[] getControls() {
            return new Object[0];
        }

        public Object getControl(String type) {
            return null;
        }
    }

}