package com.medtronic.mcms.connectandroid.models;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by mekals1 on 4/17/2018.
 */

public class RSResourceTest {
    private RSResource rsResource;
    private ArrayList<RSLocale> rsLocales;

    public static RSResource createTestRSResource(ArrayList<RSLocale> rsLocales) {
//        rsLocales = new ArrayList<RSLocale>();
//        rsLocales.add(RSLocaleTest.createTestRSLocale());
        return new RSResource(
                 9,
                 1,
                 "videoLink",
                 "Wellbeing Meditation",
                 "2018-04-04T19:39:15",
                  rsLocales
        );
    }

    @Before
    public void setup() {
        rsLocales = new ArrayList<RSLocale>();
        rsLocales.add(RSLocaleTest.createTestRSLocale());
        rsResource = createTestRSResource(rsLocales);
    }

    @Test
    public void getId() throws Exception {
        assertEquals(9,(int) rsResource.getId());
    }

    @Test
    public void setId() {
        rsResource.setId(12);
        assertEquals(12,(int) rsResource.getId());
    }
    public void getResourceVersion() {
        assertEquals(1,(int) rsResource.getResourceVersion());
    }

    public void setResourceVersion() {
        rsResource.setId(2);
        assertEquals(2,(int) rsResource.getResourceVersion());
    }

    public void getResourceFormat() {
        assertEquals("videoLink", rsResource.getResourceFormat());
    }

    public void setResourceFormat() {
        rsResource.setResourceFormat("pdf");
        assertEquals("pdf", rsResource.getResourceFormat());
    }

    public void getResourceName() {
        assertEquals("Wellbeing Meditation", rsResource.getResourceName());
    }

    public void setResourceName() {
        rsResource.setResourceName("Wellbeing Meditation Test");
        assertEquals("Wellbeing Meditation Test", rsResource.getResourceName());
    }

    public void getLastUpdatedOn() {
        assertEquals("2018-04-04T19:39:15",rsResource.getLastUpdatedOn());
    }

    public void setLastUpdatedOn() {
        rsResource.setLastUpdatedOn("2019-04-04T19:39:15");
        assertEquals("2019-04-04T19:39:15",rsResource.getLastUpdatedOn());
    }

    @Test
    public void getRSLocales() {
        assertEquals(rsLocales,rsResource.getRSLocales());
    }
    @Test
    public void setRSLocales() {
        rsResource.setRSLocales(null);
        assertEquals(null,rsResource.getRSLocales());
    }


    @Test
    public void equals() throws Exception {
        assertEquals(rsResource, rsResource);
        rsLocales = new ArrayList<RSLocale>();
        rsLocales.add(RSLocaleTest.createTestRSLocale());
        RSResource compareTo = new RSResource(
                9,
                1,
                "videoLink",
                "Wellbeing Meditation",
                "2018-04-04T19:39:15",
                rsLocales
        );
//        assertNotEquals(rslocale,compareTo);
//        compareTo.setRSLocale("en-ES");
//        assertNotEquals(rslocale,compareTo);
//        compareTo.setTitle("PASS Pain");
//        assertNotEquals(rslocale,compareTo);
//        compareTo.setDescription("setDescription");
//        assertNotEquals(rslocale,compareTo);
//        compareTo.setContent("setContent");
//        assertEquals(rslocale,compareTo);

        compareTo.setId(0);
        assertNotEquals(rsResource,compareTo);
        rsResource.setId(0);
        assertEquals(rsResource, compareTo);

        compareTo.setResourceVersion(0);
        assertNotEquals(rsResource,compareTo);
        rsResource.setResourceVersion(0);
        assertEquals(rsResource, compareTo);

        compareTo.setResourceName(null);
        assertNotEquals(rsResource,compareTo);
        rsResource.setResourceName(null);
        assertEquals(rsResource,compareTo);

        compareTo.setResourceFormat(null);
        assertNotEquals(rsResource,compareTo);
        rsResource.setResourceFormat(null);
        assertEquals(rsResource,compareTo);

        compareTo.setLastUpdatedOn(null);
        assertNotEquals(rsResource,compareTo);
        rsResource.setLastUpdatedOn(null);
        assertEquals(rsResource,compareTo);

        compareTo.setRSLocales(null);
        assertNotEquals(rsResource,compareTo);
        rsResource.setRSLocales(null);
        assertEquals(rsResource, compareTo);
    }

    @Test
    public void fromJSON() throws Exception {
        RSResource compareTo = RSResource.fromJSON(rsResource.toJSON());
        assertEquals(rsResource, compareTo);
    }

    @Test
    public void toJSON() throws Exception {
        RSResource compareTo = RSResource.fromJSON(rsResource.toJSON());
        assertEquals(rsResource, compareTo);
    }
}
