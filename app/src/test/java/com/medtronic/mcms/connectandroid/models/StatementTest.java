package com.medtronic.mcms.connectandroid.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mrgoodbytes on 4/2/18.
 */
public class StatementTest {
    private Statement statement;

    public static Statement createTestStatement() {
        Statement toReturn = new Statement("text","Aye em ay kwip","I am a quip");
        return toReturn;
    }

    @Before
    public void setup() {
        statement = createTestStatement();
    }

    @Test
    public void getType() throws Exception {
        assertEquals("text", statement.getType());
    }

    @Test
    public void setType() throws Exception {
        statement.setType("video");
        assertEquals("video", statement.getType());
    }

    @Test
    public void getSsml() throws Exception {
        assertEquals("Aye em ay kwip", statement.getSsml());
    }

    @Test
    public void setSsml() throws Exception {
        statement.setSsml("I um a quip");
        assertEquals("I um a quip", statement.getSsml());
    }

    @Test
    public void getText() throws Exception {
        assertEquals("I am a quip", statement.getText());
    }

    @Test
    public void setText() throws Exception {
        statement.setText("This is another quip");
        assertEquals("This is another quip", statement.getText());
    }

    @Test
    public void equals() throws Exception {
        assertEquals(statement, statement);
        Statement compareTo = new Statement("video","a video", "a video");
        assertNotEquals(statement,compareTo);
        compareTo.setType("text");
        assertNotEquals(statement,compareTo);
        compareTo.setSsml("Aye em ay kwip");
        assertNotEquals(statement,compareTo);
        compareTo.setText("I am a quip");
        assertEquals(statement,compareTo);

        compareTo.setType(null);
        assertNotEquals(statement,compareTo);
        statement.setType(null);
        assertEquals(statement, compareTo);
        compareTo.setSsml(null);
        assertNotEquals(statement,compareTo);
        statement.setSsml(null);
        assertEquals(statement,compareTo);
        compareTo.setText(null);
        assertNotEquals(statement,compareTo);
        statement.setText(null);
        assertEquals(statement,compareTo);
    }

    @Test
    public void fromJSON() throws Exception {
        Statement compareTo = Statement.fromJSON(statement.toJSON());
        assertEquals(statement,compareTo);
    }

    @Test
    public void toJSON() throws Exception {
        Statement compareTo = Statement.fromJSON(statement.toJSON());
        assertEquals(statement,compareTo);
    }

}