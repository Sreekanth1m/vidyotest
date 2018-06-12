package com.medtronic.mcms.connectandroid.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mrgoodbytes on 4/2/18.
 */
public class ReasonTest {
    private Reason reason;

    public static Reason createTestReason() {
        Reason toReturn = new Reason("greaterthan","10","5");
        return toReturn;
    }

    @Before
    public void setup() {
        reason = createTestReason();
    }

    @Test
    public void getId() throws Exception {
        assertEquals("greaterthan", reason.getId());
    }

    @Test
    public void setId() throws Exception {
        reason.setId("lessthan");
        assertEquals("lessthan", reason.getId());
    }

    @Test
    public void getValue() throws Exception {
        assertEquals("10", reason.getValue());
    }

    @Test
    public void setValue() throws Exception {
        reason.setValue("6");
        assertEquals("6", reason.getValue());
    }

    @Test
    public void getLimit() throws Exception {
        assertEquals("5", reason.getLimit());
    }

    @Test
    public void setLimit() throws Exception {
        reason.setLimit("11");
        assertEquals("11", reason.getLimit());
    }

    @Test
    public void equals() throws Exception {
        assertEquals(reason, reason);
        Reason compareTo = new Reason("lessthan","2","4");
        assertNotEquals(reason,compareTo);
        compareTo.setId("greaterthan");
        assertNotEquals(reason,compareTo);
        compareTo.setValue("10");
        assertNotEquals(reason,compareTo);
        compareTo.setLimit("5");
        assertEquals(reason,compareTo);

        compareTo.setId(null);
        assertNotEquals(reason,compareTo);
        reason.setId(null);
        assertEquals(reason, compareTo);
        compareTo.setValue(null);
        assertNotEquals(reason,compareTo);
        reason.setValue(null);
        assertEquals(reason,compareTo);
        compareTo.setLimit(null);
        assertNotEquals(reason,compareTo);
        reason.setLimit(null);
        assertEquals(reason,compareTo);
    }

    @Test
    public void fromJSON() throws Exception {
        Reason compareTo = Reason.fromJSON(reason.toJSON());
        assertEquals(reason, compareTo);
    }

    @Test
    public void toJSON() throws Exception {
        Reason compareTo = Reason.fromJSON(reason.toJSON());
        assertEquals(reason, compareTo);
    }

}