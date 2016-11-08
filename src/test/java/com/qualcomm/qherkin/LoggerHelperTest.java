package com.qualcomm.qherkin;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.testng.ITestClass;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UnknownFormatConversionException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoggerHelperTest extends LoggerBaseTest {

    private final int DATE_OFFSET = 0;
    private final String TIMEZONE = "PST";
    private final String DATE_PATTERN = "MM\\dd\\yyyy";

    private final String FORMATTING_MESSAGE = "Scenario: Landing Page %s";
    private final String GHERKIN_HEADING_MESSAGE = "Scenario: Landing Page Components";
    private final String GHERKIN_CORE_MESSAGE = "Then The Landing Page is displayed with expected data";
    private final String INFORMATIONAL_MESSAGE = "testBasic--Description: N/A";
    private final String GENERAL_LOG_MESSAGE = "Verify elementTextExists() exists text=Response from OneIp-WebService for Patents. {Page Title: 'OneIP'} is <true> was <true>";

    private String expectedDateAsString;
    private Calendar expectedCalendar;

    private ITestResult mockTestResult;
    private ITestClass mockTestClass;
    private ITestNGMethod mockITestNGMethod;

    @Before
    public void before() {

        mockTestResult = mock(ITestResult.class);
        mockTestClass = mock(ITestClass.class);
        mockITestNGMethod = mock(ITestNGMethod.class);

        when(mockTestResult.getMethod()).thenReturn(mockITestNGMethod);
        when(mockITestNGMethod.getTestClass()).thenReturn(mockTestClass);
        when(mockTestClass.toString()).thenReturn("com.qualcomm.qherkin.LoggerBaseTest");

        expectedCalendar = Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE));
        expectedCalendar.add(Calendar.DATE, DATE_OFFSET);
        DateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
        formatter.setCalendar(expectedCalendar);
        formatter.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
        Date expectedDate = expectedCalendar.getTime();
        expectedDateAsString = formatter.format(expectedDate);
    }

    @Test
    public void formatGherkinMessageHeadingTest() {
        Assert.assertEquals("  Scenario: Landing Page Components", LoggerHelper.formatGherkinMessage(GHERKIN_HEADING_MESSAGE));
    }

    @Test
    public void formatGherkinMessageCoreTest() {
        Assert.assertEquals("    Then The Landing Page is displayed with expected data", LoggerHelper.formatGherkinMessage(GHERKIN_CORE_MESSAGE));
    }

    @Test
    public void formatGherkinMessageInformationalTest() {
        Assert.assertEquals(INFORMATIONAL_MESSAGE, LoggerHelper.formatGherkinMessage(INFORMATIONAL_MESSAGE));
    }

    @Test
    public void formatGherkinMessageGeneralLogTest() {
        Assert.assertEquals("      Verify elementTextExists() exists text=Response from OneIp-WebService for Patents. {Page Title: 'OneIP'} is <true> was <true>", LoggerHelper.formatGherkinMessage(GENERAL_LOG_MESSAGE));
    }

    @Test
    public void formatMessageTest() {
        Assert.assertEquals("Scenario: Landing Page Components", LoggerHelper.formatMessage(FORMATTING_MESSAGE, new Object[]{"Components"}));
    }

    @Test
    public void formatMessageInvalidCountOfSubstitutionsTest() {
        Assert.assertEquals("Scenario: Landing  Page Components", LoggerHelper.formatMessage("Scenario: Landing %s Page %s", new Object[]{"Components"}));
    }

    @Test(expected = UnknownFormatConversionException.class)
    public void formatMessageInvalidArgumentTest() {
        Assert.assertEquals("Scenario: Landing Page Components", LoggerHelper.formatMessage("Scenario: Landing Page %3", new Object[]{"Components"}));
    }

    @Test
    public void getClassNameTest() {
        Assert.assertEquals("LoggerBaseTest", LoggerHelper.getClassName(mockTestResult));
    }

    @Test
    public void getClassNameExceptionTest() {
        when(mockTestResult.getMethod()).thenReturn(mockITestNGMethod);
        when(mockITestNGMethod.getTestClass()).thenReturn(mockTestClass);
        when(mockTestClass.toString()).thenThrow(NullPointerException.class);
        Assert.assertEquals("", LoggerHelper.getClassName(mockTestResult));
    }

    @Test
    public void getClassNameNullResultTest() {
        Assert.assertEquals("", LoggerHelper.getClassName(null));
    }

    @Test
    public void getClassNameFromClasspathTest() {
        Assert.assertEquals("LoggerBaseTest", LoggerHelper.getClassNameFromClasspath(mockITestNGMethod));
    }

    @Test(expected = NullPointerException.class)
    public void getClassNameFromClasspathNullResultTest() {
        Assert.assertEquals("", LoggerHelper.getClassNameFromClasspath(null));
    }

    @Test
    public void getDateTimePatterTest() {
        Assert.assertEquals(expectedDateAsString, LoggerHelper.getDateTime(DATE_PATTERN));
    }

    @Test
    public void getDateTimePatternAndTimezoneTest() {
        Assert.assertEquals(expectedDateAsString, LoggerHelper.getDateTime(DATE_PATTERN, TIMEZONE));
    }

    @Test
    public void getDateTimePatternAndTimezoneAndOffsetTest() {
        Assert.assertEquals(expectedDateAsString, LoggerHelper.getDateTime(DATE_PATTERN, TIMEZONE, DATE_OFFSET));
    }

    @Test
    public void getDateTimePatternAndCalendarAndOffsetTest() {
        Assert.assertEquals(expectedDateAsString, LoggerHelper.getDateTime(DATE_PATTERN, expectedCalendar, TIMEZONE));
    }

    @Test
    public void getDateTest() {
        Assert.assertNotNull(LoggerHelper.getDate(TIMEZONE, DATE_OFFSET));
    }

    @Test
    public void getDateJustOffsetTest() {
        Assert.assertNotNull(LoggerHelper.getDate(DATE_OFFSET));
    }

    @Test
    public void convertStackTraceTest() {
        String expectedStackTrace = "java.lang.NullPointerException: This threw a null pointer";
        Assert.assertTrue(StringUtils.contains(LoggerHelper.convertStackTrace(new NullPointerException("This threw a null pointer")), expectedStackTrace));
    }

}
