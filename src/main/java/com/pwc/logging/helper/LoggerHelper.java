package com.pwc.logging.helper;

import org.apache.commons.lang.StringUtils;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class LoggerHelper {

    private static final int GHERKIN_HEADING_SPACE_COUNT = 2;
    private static final int GHERKIN_CORE_SPACE_COUNT = 4;
    private static final int GENERAL_LOG_SPACE_COUNT = 6;

    private static final String[] GHERKIN_HEADING_TOKENS = new String[]{"FEATURE", "SCENARIO"};
    private static final String[] GHERKIN_CORE_TOKENS = new String[]{"GIVEN", "WHEN", "THEN", "AND", "BUT"};
    private static final String[] INFORMATIONAL_TOKENS = new String[]{"--"};

    /**
     * Wrapper method to avoid MissingFormatArgumentException that users could
     * accidentally do by using the variable arguments logic incorrectly
     *
     * @param message     Assertion message to manipulate
     * @param messageArgs Variable args array
     * @return well-formatted message
     */
    public static String formatMessage(String message, Object... messageArgs) {
        try {
            return String.format(message, messageArgs);
        } catch (Exception e) {
            int argumentNotationCount = StringUtils.countMatches(message, "%s");
            for (int i = 0; i < (argumentNotationCount - messageArgs.length); i++) {
                message = StringUtils.replaceOnce(message, "%s", "").trim();
            }
        }
        return String.format(message, messageArgs);
    }

    public static String formatGherkinMessage(String message) {
        if (startsWithAnyIgnoreCase(message, GHERKIN_HEADING_TOKENS)) {
            return StringUtils.repeat(" ", GHERKIN_HEADING_SPACE_COUNT) + message;
        } else if (startsWithAnyIgnoreCase(message, GHERKIN_CORE_TOKENS)) {
            return StringUtils.repeat(" ", GHERKIN_CORE_SPACE_COUNT) + message;
        } else if (containsAnyIgnoreCase(message, INFORMATIONAL_TOKENS)) {
            return message;
        } else {
            return StringUtils.repeat(" ", GENERAL_LOG_SPACE_COUNT) + message;
        }
    }

    private static boolean startsWithAnyIgnoreCase(String actual, String[] tokens) {
        for (String token : tokens) {
            if (org.apache.commons.lang.StringUtils.startsWithIgnoreCase(actual, token)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsAnyIgnoreCase(final String actual, String[] tokens) {
        for (String token : tokens) {
            if (org.apache.commons.lang.StringUtils.containsIgnoreCase(actual, token)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get class name from ITestResult in context
     *
     * @param testResult currently executing method
     * @return well formed test class name
     */
    public static String getClassName(ITestResult testResult) {
        try {
            if (testResult != null) {
                return getClassNameFromClasspath(testResult.getMethod());
            }
        } catch (Exception e) {
            return "";
        }
        return "";
    }

    /**
     * Get class name from ITestNGMethod in context
     *
     * @param currentMethod currently executing method
     * @return well formed test class name
     */
    public static String getClassNameFromClasspath(final ITestNGMethod currentMethod) {
        return StringUtils.substringBeforeLast(StringUtils.substringAfterLast(currentMethod.getTestClass().toString(), "."), "]");
    }

    /**
     * Converts the Stack Trace to String
     *
     * @param error Assertion Error that was captured when Assert Method failed
     * @return Stack trace as String
     */
    public static String convertStackTrace(Throwable error) {
        StringWriter stackTraceWriter = new StringWriter();
        error.printStackTrace(new PrintWriter(stackTraceWriter));
        return stackTraceWriter.toString();
    }

    /**
     * Utility method which returns a Date and time <code>String</code> for a specified DATE offset
     *
     * @param pattern date/time pattern
     * @return offset date and time
     */
    public static String getDateTime(final String pattern) {
        return getDateTime(pattern, System.getProperty("user.timezone"));
    }

    /**
     * Utility method which returns a Date and time <code>String</code> for a specified DATE offset in a
     * given date formatted pattern for a particular TimeZone <code>String</code>
     *
     * @param pattern  date/time pattern
     * @param timeZone timezone of timestamp
     * @return offset date and time
     */
    public static String getDateTime(final String pattern, final String timeZone) {
        return getDateTime(pattern, timeZone, 0);
    }

    /**
     * Utility method which returns a Date and time <code>String</code> for a specified DATE offset in a
     * given date formatted pattern for a particular TimeZone <code>String</code>
     *
     * @param pattern    date/time pattern
     * @param timeZone   timezone of timestamp
     * @param dateOffset date offset
     * @return offset date and time
     */
    public static String getDateTime(final String pattern, final String timeZone, final int dateOffset) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
        cal.add(Calendar.DATE, dateOffset);
        return getDateTime(pattern, cal, timeZone);
    }

    /**
     * Utility method which returns a Date and time <code>String</code> for a specified Calendar instance.
     *
     * @param pattern  date/time pattern
     * @param calendar calendar instance
     * @param timeZone timezone of timestamp
     * @return formatted calendar time
     */
    public static String getDateTime(final String pattern, final Calendar calendar, final String timeZone) {
        DateFormat formatter = new SimpleDateFormat(pattern);
        formatter.setCalendar(calendar);
        formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
        return formatter.format(calendar.getTime());
    }

    /**
     * Utility method which returns a Date for a specified DATE offset for a
     * particular TimeZone <code>String</code>
     *
     * @param timeZone   timezone of timestamp
     * @param dateOffset date offset
     * @return offset date and time
     */
    public static Date getDate(final String timeZone, final int dateOffset) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
        cal.add(Calendar.DATE, dateOffset);
        return cal.getTime();
    }

    /**
     * Utility method which returns a Date for a specified DATE offset
     *
     * @param dateOffset date offset
     * @return offset date offset
     */
    public static Date getDate(final int dateOffset) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, dateOffset);
        return cal.getTime();
    }


}
