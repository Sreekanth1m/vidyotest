package com.medtronic.mcms.connectandroid.models;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mrgoodbytes on 3/26/18.
 */
public class RegexValidatorTest {
    private RegexValidator validator;

    @Before
    public void setup() throws Exception {
        validator = new RegexValidator("[0-9]+(\\.[0-9]+)*?", "Value is invalid positive decimal (US)");
    }

    @Test
    public void getRegex() throws Exception {
        assertEquals("[0-9]+(\\.[0-9]+)*?",validator.getRegex());
    }

    @Test
    public void setRegex() throws Exception {
    }

    @Test
    public void getMessage() throws Exception {
        assertEquals("Value is invalid positive decimal (US)", validator.getMessage());
    }

    @Test
    public void setMessage() throws Exception {
    }

    // TODO: see where to put invalid message first
    /*@Test
    public void getInvalidMessage() throws Exception {
        //assertEquals("Value is invalid decimal (US)", validator.getInvalidMessage());
        assertTrue(false);
    }*/

    @Test
    public void isValid() throws Exception {
        assertFalse(validator.isValid("abcde"));
        assertFalse(validator.isValid("-1"));
        assertFalse(validator.isValid("-1.5"));
        assertTrue(validator.isValid("1"));
        assertTrue(validator.isValid("0.0"));
        assertTrue(validator.isValid("15.012345"));
    }

    @Test
    public void equals() throws Exception {
        assertEquals(validator,validator);
        RegexValidator compareTo = new RegexValidator("*", "Invalid");

        assertNotEquals(validator, compareTo);
        compareTo.setRegex("[0-9]+(\\.[0-9]+)*?");
        assertNotEquals(validator, compareTo);
        compareTo.setMessage("Value is invalid positive decimal (US)");
        assertEquals(validator, compareTo);

        compareTo.setRegex(null);
        assertNotEquals(validator,compareTo);
        validator.setRegex(null);
        assertEquals(validator, compareTo);

        compareTo.setMessage(null);
        assertNotEquals(validator,compareTo);
        validator.setMessage(null);
        assertEquals(validator, compareTo);
    }

    @Test
    public void fromJSON() throws Exception {
        RegexValidator compareTo = RegexValidator.fromJSON(validator.toJSON());
        assertEquals(validator,compareTo);
    }

    @Test
    public void toJSON() throws Exception {
        RegexValidator compareTo = RegexValidator.fromJSON(validator.toJSON());
        assertEquals(validator,compareTo);
    }
}