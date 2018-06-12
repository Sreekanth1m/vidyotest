package com.medtronic.mcms.connectandroid.models;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by mekals1 on 4/16/2018.
 */

public class RSCategoryTest {
    private RSCategory rsCategory;
    private ArrayList<RSLocale> rsLocales;

    public static RSCategory createTestRSCategory(ArrayList<RSLocale> rsLocales) {
//        rsLocales = new ArrayList<RSLocale>();
//        rsLocales.add(RSLocaleTest.createTestRSLocale());
        return new RSCategory(
                11,
                "COPD",
                "2018-04-04T18:09:03.925Z",
                rsLocales
        );
    }

    @Before
    public void setup() {
        rsLocales = new ArrayList<RSLocale>();
        rsLocales.add(RSLocaleTest.createTestRSLocale());
        rsCategory = createTestRSCategory(rsLocales);
    }

    @Test
    public void getId() throws Exception {
        assertEquals(11,(int) rsCategory.getId());
    }

    @Test
    public void setId() {
        rsCategory.setId(12);
        assertEquals(12,(int) rsCategory.getId());
    }
    @Test
    public void getCategoryName() {
        assertEquals("COPD",rsCategory.getCategoryName());
    }
    @Test
    public void setCategoryName() {
        rsCategory.setCategoryName("HeartFailure");
        assertEquals("HeartFailure",rsCategory.getCategoryName());
    }
    @Test
    public void getLastUpdatedOn() {
        assertEquals("2018-04-04T18:09:03.925Z",rsCategory.getLastUpdatedOn());
    }
    @Test
    public void setLastUpdatedOn() {
        rsCategory.setLastUpdatedOn("2019-04-04T18:09:03.925Z");
        assertEquals("2019-04-04T18:09:03.925Z",rsCategory.getLastUpdatedOn());
    }
    @Test
    public void getRSLocales() {
        assertEquals(rsLocales,rsCategory.getRSLocales());
    }
    @Test
    public void setRSLocales() {
        rsCategory.setRSLocales(null);
        assertEquals(null,rsCategory.getRSLocales());
    }


    @Test
    public void equals() throws Exception {
        assertEquals(rsCategory, rsCategory);
        rsLocales = new ArrayList<RSLocale>();
        rsLocales.add(RSLocaleTest.createTestRSLocale());
       RSCategory compareTo = new RSCategory(
                11,
                "COPD",
                "2018-04-04T18:09:03.925Z",
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
        assertNotEquals(rsCategory,compareTo);
        rsCategory.setId(0);
        assertEquals(rsCategory, compareTo);
        compareTo.setCategoryName(null);
        assertNotEquals(rsCategory,compareTo);
        rsCategory.setCategoryName(null);
        assertEquals(rsCategory,compareTo);
        compareTo.setLastUpdatedOn(null);
        assertNotEquals(rsCategory,compareTo);
        rsCategory.setLastUpdatedOn(null);
        assertEquals(rsCategory,compareTo);
        compareTo.setRSLocales(null);
        assertNotEquals(rsCategory,compareTo);
        rsCategory.setRSLocales(null);
        assertEquals(rsCategory, compareTo);
    }

    @Test
    public void fromJSON() throws Exception {
        RSCategory compareTo = RSCategory.fromJSON(rsCategory.toJSON());
        assertEquals(rsCategory, compareTo);
    }

    @Test
    public void toJSON() throws Exception {
        RSCategory compareTo = RSCategory.fromJSON(rsCategory.toJSON());
        assertEquals(rsCategory, compareTo);
    }
}
