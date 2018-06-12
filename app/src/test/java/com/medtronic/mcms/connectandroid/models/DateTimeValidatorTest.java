package com.medtronic.mcms.connectandroid.models;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by mrgoodbytes on 3/26/18.
 */
public class DateTimeValidatorTest {
    private DateTimeValidator validator;
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("'T'HH:mm:ss.SSS'Z'", Locale.US);
    private Date min;
    private Date max;

    @Before
    public void setup() throws Exception{
        min = timeFormat.parse("T01:00:00.000Z");
        max = timeFormat.parse("T23:00:00.000Z");
        validator = new DateTimeValidator(timeFormat,
                min,
                max,
                "Bad Time");
    }

    @Test
    public void getTimeOffset() throws Exception {

//        Calendar calendar = new GregorianCalendar();
//
//        int hour1 = calendar.get(Calendar.HOUR_OF_DAY);
//        Date date1 = calendar.getTime();
//        String time1 = DateUtility.ADJUSTED_TIME_FORMAT.format(date1);
//
//        TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
//        calendar.setTimeZone(tz);
//
//        int hour2 = calendar.get(Calendar.HOUR_OF_DAY);
//        Date date2 = calendar.getTime();
//        String time2 = DateUtility.ADJUSTED_TIME_FORMAT.format(date2);

        String adjustedTime = DateUtility.buildUploadTimeString("22:21:00-0500");

        Log.d("","");
    }

    @Test
    public void getMinValue() throws Exception {
        assertEquals(min, validator.getMinValue());
    }

    @Test
    public void setMinValue() throws Exception {
        validator.setMinValue(timeFormat.parse("T02:00:00.000Z"));
        assertEquals(timeFormat.parse("T02:00:00.000Z"), validator.getMinValue());
    }

    @Test
    public void setMinValue1() throws Exception {
        validator.setMinValue("T02:00:00.000Z");
        assertEquals(timeFormat.parse("T02:00:00.000Z"), validator.getMinValue());
    }

    @Test
    public void getMaxValue() throws Exception {
        assertEquals(max, validator.getMaxValue());
    }

    @Test
    public void setMaxValue() throws Exception {
        validator.setMaxValue(timeFormat.parse("T02:00:00.000Z"));
        assertEquals(timeFormat.parse("T02:00:00.000Z"), validator.getMaxValue());
    }

    @Test
    public void setMaxValue1() throws Exception {
        validator.setMaxValue("T02:00:00.000Z");
        assertEquals(timeFormat.parse("T02:00:00.000Z"), validator.getMaxValue());
    }

    @Test
    public void getFormat() throws Exception {
        assertEquals(timeFormat, validator.getFormat());
    }

    @Test
    public void setFormat() throws Exception {
        validator.setFormat(DateUtility.isoDateFormat);
        assertEquals(DateUtility.isoDateFormat, validator.getFormat());
    }

    @Test
    public void isValid() throws Exception {
        assertFalse(validator.isValid((Date)null));
        assertFalse(validator.isValid(timeFormat.parse("T00:00:00.000Z")));
        assertFalse(validator.isValid(timeFormat.parse("T24:00:00.000Z")));

        assertFalse(validator.isValid(timeFormat.parse("T00:59:59.999Z")));
        assertFalse(validator.isValid(timeFormat.parse("T23:00:00.001Z")));

        assertTrue(validator.isValid(timeFormat.parse("T01:00:00.000Z")));
        assertTrue(validator.isValid(timeFormat.parse("T23:00:00.000Z")));
        assertTrue(validator.isValid(timeFormat.parse("T12:00:00.000Z")));
    }

    @Test
    public void isValid1() throws Exception {
        assertFalse(validator.isValid((String) null));

        assertFalse(validator.isValid("T00:00:00.000Z"));
        assertFalse(validator.isValid("T24:00:00.000Z"));

        assertFalse(validator.isValid("T00:59:59.999Z"));
        assertFalse(validator.isValid("T23:00:00.001Z"));

        assertTrue(validator.isValid("T01:00:00.000Z"));
        assertTrue(validator.isValid("T23:00:00.000Z"));
        assertTrue(validator.isValid("T12:00:00.000Z"));

        assertFalse(validator.isValid("12:00 AM"));
    }

    @Test
    public void equals() throws Exception {
        assertEquals(validator, validator);

        DateTimeValidator compareTo = new DateTimeValidator(DateUtility.isoDateFormat,
                timeFormat.parse("T00:00:00.000Z"),
                timeFormat.parse("T24:00:00.000Z"),
                "Bad DateTime");

        assertNotEquals(validator,compareTo);

        compareTo.setFormat(timeFormat);
        assertNotEquals(validator,compareTo);

        compareTo.setMinValue(min);
        assertNotEquals(validator,compareTo);
        compareTo.setMaxValue(max);
        assertNotEquals(validator,compareTo);
        compareTo.setMessage("Bad Time");
        assertEquals(validator,compareTo);

        compareTo.setFormat((SimpleDateFormat)null);
        assertNotEquals(validator,compareTo);
        validator.setFormat((SimpleDateFormat)null);
        assertEquals(validator,compareTo);

        compareTo.setMinValue((Date)null);
        assertNotEquals(validator,compareTo);
        validator.setMinValue((Date)null);
        assertEquals(validator,compareTo);

        compareTo.setMaxValue((Date)null);
        assertNotEquals(validator,compareTo);
        validator.setMaxValue((Date)null);
        assertEquals(validator,compareTo);

        compareTo.setMessage(null);
        assertNotEquals(validator,compareTo);
        validator.setMessage(null);
        assertEquals(validator,compareTo);
    }

    @Test
    public void fromJSON() throws Exception {
        DateTimeValidator compareTo = DateTimeValidator.fromJSON(validator.toJSON());
        assertEquals(validator,compareTo);
    }

    @Test
    public void toJSON() throws Exception {
        DateTimeValidator compareTo = DateTimeValidator.fromJSON(validator.toJSON());
        assertEquals(validator,compareTo);
    }

}