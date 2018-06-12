package com.medtronic.mcms.connectandroid.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mrgoodbytes on 3/30/18.
 */
public class ChoiceTest {
    private Choice choice;

    @Before
    public void setup() throws Exception {
        choice = new Choice("key", "value");
    }

    @Test
    public void getText() throws Exception {
        assertEquals("key", choice.getText());
    }

    @Test
    public void setText() throws Exception {
        choice.setText("key2");
        assertEquals("key2", choice.getText());
    }

    @Test
    public void getValue() throws Exception {
        assertEquals("value", choice.getValue());
    }

    @Test
    public void setValue() throws Exception {
        choice.setValue("value2");
        assertEquals("value2", choice.getValue());
    }

    @Test
    public void equals() throws  Exception {
        assertEquals(choice, choice);

        Choice compareTo = new Choice("key2", "value2");
        assertNotEquals(choice, compareTo);
        compareTo.setText("key");
        assertNotEquals(choice, compareTo);
        compareTo.setValue("value");
        assertEquals(choice, compareTo);

        compareTo.setText(null);
        assertNotEquals(choice, compareTo);
        choice.setText(null);
        assertEquals(choice, compareTo);

        compareTo.setValue(null);
        assertNotEquals(choice, compareTo);
        choice.setValue(null);
        assertEquals(choice, compareTo);
    }

    @Test
    public void fromJSON() throws Exception {
        Choice compareTo = Choice.fromJSON(choice.toJSON());
        assertEquals(choice, compareTo);
    }

    @Test
    public void toJSON() throws Exception {
        Choice compareTo = Choice.fromJSON(choice.toJSON());
        assertEquals(choice, compareTo);
    }

}