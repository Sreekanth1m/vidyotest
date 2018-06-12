package com.medtronic.mcms.connectandroid.models;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by mekals1 on 4/13/2018.
 */

public class RSLocaleTest {

    private RSLocale rslocale;

    public static RSLocale createTestRSLocale() {

        return new RSLocale(
               "en-US",
               "Understanding COPD",
               "An Animated Patient’s guide to Chronic Obstructive Pulmonary Disease",
               "https://www.youtube.com/watch?v=T1G9Rl65M-Q"

        );
    }

    @Before
    public void setup() {
        rslocale = createTestRSLocale();
    }
    @Test
    public void getRSLocale() {
        assertEquals("en-US",rslocale.getRSLocale());
    }
    @Test
    public void setRSLocale() {
        rslocale.setRSLocale("en-ES");
        assertEquals("en-ES",rslocale.getRSLocale());
    }
    @Test
    public void getTitle() {
        assertEquals("Understanding COPD",rslocale.getTitle());
    }
    @Test
    public void setTitle() {
        rslocale.setTitle("PASS Pain");
        assertEquals("PASS Pain",rslocale.getTitle());
    }
    @Test
    public void getDescription() {

        assertEquals("An Animated Patient’s guide to Chronic Obstructive Pulmonary Disease",rslocale.getDescription());
    }
    @Test
    public void setDescription() {
        rslocale.setDescription("setDescription");
        assertEquals("setDescription",rslocale.getDescription());
    }
    @Test
    public void getContent() {
        assertEquals("https://www.youtube.com/watch?v=T1G9Rl65M-Q",rslocale.getContent());
    }
    @Test
    public void setContent() {
        rslocale.setContent("setContent");
        assertEquals("setContent",rslocale.getContent());
    }
    @Test
    public void equals() throws Exception {
        assertEquals(rslocale, rslocale);
        RSLocale compareTo = new RSLocale("en-ES",
                "Understanding COPD",
                "An Animated Patient’s guide to Chronic Obstructive Pulmonary Disease",
                "https://www.youtube.com/watch?v=T1G9Rl65M-Q");
//        assertNotEquals(rslocale,compareTo);
//        compareTo.setRSLocale("en-ES");
//        assertNotEquals(rslocale,compareTo);
//        compareTo.setTitle("PASS Pain");
//        assertNotEquals(rslocale,compareTo);
//        compareTo.setDescription("setDescription");
//        assertNotEquals(rslocale,compareTo);
//        compareTo.setContent("setContent");
//        assertEquals(rslocale,compareTo);

        compareTo.setRSLocale(null);
        assertNotEquals(rslocale,compareTo);
        rslocale.setRSLocale(null);
        assertEquals(rslocale, compareTo);
        compareTo.setTitle(null);
        assertNotEquals(rslocale,compareTo);
        rslocale.setTitle(null);
        assertEquals(rslocale,compareTo);
        compareTo.setDescription(null);
        assertNotEquals(rslocale,compareTo);
        rslocale.setDescription(null);
        assertEquals(rslocale,compareTo);
        compareTo.setContent(null);
        assertNotEquals(rslocale,compareTo);
        rslocale.setContent(null);
        assertEquals(rslocale, compareTo);
    }

    @Test
    public void fromJSON() throws Exception {
        RSLocale compareTo = RSLocale.fromJSON(rslocale.toJSON());
        assertEquals(rslocale, compareTo);
    }

    @Test
    public void toJSON() throws Exception {
        RSLocale compareTo = RSLocale.fromJSON(rslocale.toJSON());
        assertEquals(rslocale, compareTo);
    }
}
