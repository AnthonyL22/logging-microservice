package com.pwc.logging;

import com.pwc.logging.service.LoggerService;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;

public class LoggerBaseTest {

    protected static final String DATE_TIME_REGEX = "^\\d{4}-\\d{2}-\\d{2}[ ]\\d{2}:\\d{2}:\\d{2}\\.\\d{3}.*?$";

    protected void verifyLogOutput(final String messageToVerify) {
        Assert.assertTrue(StringUtils.contains(LoggerService.getOutMessage(), messageToVerify));
        String assertDateInMessage = StringUtils.remove(LoggerService.getOutMessage(), "\n");
        Assert.assertTrue(assertDateInMessage.matches(DATE_TIME_REGEX));
    }

    protected void verifyLogOutputWithoutDate(final String messageToVerify) {
        Assert.assertTrue(StringUtils.contains(LoggerService.getOutMessage(), messageToVerify));
    }

}
