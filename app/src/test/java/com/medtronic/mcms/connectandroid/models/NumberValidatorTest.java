package com.medtronic.mcms.connectandroid.models;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mrgoodbytes on 3/26/18.
 */
public class NumberValidatorTest {
    NumberValidator validator;

    @Before
    public void setUp() throws Exception {
        validator = new NumberValidator(5.0, 100.0, 2, "Invalid Number");
    }

    @Test
    public void getMinValue() throws Exception {
        assertEquals((Double)5.0, validator.getMinValue());
    }

    @Test
    public void setMinValue() throws Exception {
        validator.setMinValue(-1.0);
        assertEquals((Double)(-1.0), validator.getMinValue());
    }

    @Test
    public void setMinValue1() throws Exception {
        validator.setMinValue("-1.0");
        assertEquals((Double)(-1.0), validator.getMinValue());
    }

    @Test
    public void getMaxValue() throws Exception {
        assertEquals((Double)100.0, validator.getMaxValue());
    }

    @Test
    public void setMaxValue() throws Exception {
        validator.setMaxValue(500.0);
        assertEquals((Double)500.0, validator.getMaxValue());
    }

    @Test
    public void setMaxValue1() throws Exception {
        validator.setMaxValue("500.0");
        assertEquals((Double)500.0, validator.getMaxValue());
    }

    @Test
    public void getPrecision() throws Exception {
        assertEquals((Integer)2,validator.getPrecision());
    }

    @Test
    public void setPrecision() throws Exception {
        validator.setPrecision(3);
        assertEquals((Integer)3,validator.getPrecision());
    }

    @Test
    public void setPrecision1() throws Exception {
        validator.setPrecision("3");
        assertEquals((Integer)3,validator.getPrecision());
    }

    @Test
    public void getMessage() throws Exception {
        assertEquals("Invalid Number", validator.getMessage());
    }

    @Test
    public void setMessage() throws Exception {
        validator.setMessage("Bad Number");
        assertEquals("Bad Number", validator.getMessage());
    }

    @Test
    public void isValid() throws Exception {
        assertTrue(validator.isValid("50.0"));
        assertFalse(validator.isValid("-1.0"));
        assertFalse(validator.isValid("200.0"));
    }

    @Test
    public void isValid1() throws Exception {
        assertFalse(validator.isValid("-1.0"));
        assertFalse(validator.isValid("200.0"));
        assertFalse(validator.isValid("50.0"));
        assertTrue(validator.isValid("50.00"));

        validator.setPrecision("0");
        assertTrue(validator.isValid("100"));
    }

    @Test
    public void equals() throws Exception {
        assertEquals(validator, validator);
        NumberValidator compareTo = new NumberValidator(1.0, 101.0, 3, "Invalid Double");
        assertNotEquals(validator,compareTo);
        compareTo.setMinValue(5.0);
        assertNotEquals(validator,compareTo);
        compareTo.setMaxValue(100.0);
        assertNotEquals(validator,compareTo);
        compareTo.setPrecision(2);
        assertNotEquals(validator,compareTo);
        compareTo.setMessage("Invalid Number");
        assertEquals(validator,compareTo);

        compareTo.setMinValue((Double)null);
        assertNotEquals(validator,compareTo);
        validator.setMinValue((Double)null);
        assertEquals(validator, compareTo);

        compareTo.setMaxValue((Double) null);
        assertNotEquals(validator,compareTo);
        validator.setMaxValue((Double)null);
        assertEquals(validator,compareTo);

        compareTo.setPrecision((Integer)null);
        assertNotEquals(validator,compareTo);
        validator.setPrecision((Integer)null);
        assertEquals(validator,compareTo);

        compareTo.setMessage((String) null);
        assertNotEquals(validator,compareTo);
        validator.setMessage((String) null);
        assertEquals(validator,compareTo);
    }

    @Test
    public void fromJSON() throws Exception {
        NumberValidator compareTo = NumberValidator.fromJSON(validator.toJSON());
        assertEquals(validator, compareTo);
    }

    @Test
    public void toJSON() throws Exception {
        NumberValidator compareTo = NumberValidator.fromJSON(validator.toJSON());
        assertEquals(validator, compareTo);
    }

}