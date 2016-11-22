package com.pwc.logging.service;

import org.apache.commons.lang.StringUtils;
import org.testng.Reporter;

import static com.pwc.logging.helper.LoggerHelper.*;

public class LoggerService {

    public static final String DATETIME_LOGGER_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String WEB_CARRIAGE_RETURN = "</br>";
    public static final String CARRIAGE_RETURN = "\n";

    private static String outMessage;

    public static void LOG(String message, Exception e) {
        reportMessage(true, formatMessage("%s - Exception='%s'", new Object[]{message, e.getMessage()}));
    }

    public static void LOG(final String message) {
        reportMessage(true, message);
    }

    public static void LOG(final boolean logToStandardOut, final String message) {
        outMessage = message;
        Reporter.log(message, logToStandardOut);
    }

    public static void LOG(final boolean logToStandardOut, final String message, final Object... args) {
        reportMessage(logToStandardOut, formatMessage(message, args));
    }

    public static void FEATURE(final String message, final Object... args) {
        LOG(formatMessage("Feature: " + message, args));
    }

    public static void SCENARIO(String message, final Object... args) {
        LOG(formatMessage("Scenario: " + message, args));
    }

    public static void GIVEN(String message, final Object... args) {
        LOG(formatMessage("Given " + message, args));
    }

    public static void WHEN(String message, final Object... args) {
        LOG(formatMessage("When " + message, args));
    }

    public static void THEN(String message, final Object... args) {
        LOG(formatMessage("Then " + message, args));
    }

    public static void AND(String message, final Object... args) {
        LOG(formatMessage("And " + message, args));
    }

    public static void BUT(String message, final Object... args) {
        LOG(formatMessage("But " + message, args));
    }

    private static void reportMessage(boolean logToStandardOut, String message) {
        message = formatGherkinMessage(message);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getDateTime(DATETIME_LOGGER_PATTERN));
        stringBuilder.append("  ");
        String className = getClassName(Reporter.getCurrentTestResult());
        stringBuilder.append(StringUtils.rightPad(StringUtils.substring(className, 0, 40), 42));
        stringBuilder.append(message == null ? "" : message);
        if (logToStandardOut) {
            System.out.print(stringBuilder.toString() + CARRIAGE_RETURN);
        }
        stringBuilder.append(WEB_CARRIAGE_RETURN);
        outMessage = stringBuilder.toString();
        Reporter.log(stringBuilder.toString());
    }

    public static String getOutMessage() {
        return outMessage;
    }

}