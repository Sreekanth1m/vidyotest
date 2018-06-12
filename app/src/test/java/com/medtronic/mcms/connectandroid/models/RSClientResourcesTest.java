package com.medtronic.mcms.connectandroid.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by mekals1 on 4/17/2018.
 */

public class RSClientResourcesTest {

    private RSClientResources rsClientResources;
    private String clientId;
    private ArrayList<RSCategoryResource> rsCategoryResources;

    public static RSClientResources createTestRSClientResources(ArrayList<RSCategoryResource> rsCategoryResources) {

        return new RSClientResources(
                "default",
                rsCategoryResources
        );
    }

    @Before
    public void setup() {
        ArrayList<RSLocale> rsLocales = new ArrayList<RSLocale>();
        rsLocales.add(RSLocaleTest.createTestRSLocale());
        ArrayList<RSResource> rsResources = new ArrayList<RSResource>();
        rsResources.add(RSResourceTest.createTestRSResource(rsLocales));
        RSCategory rsCategory = RSCategoryTest.createTestRSCategory(rsLocales);
        rsCategoryResources = new ArrayList<RSCategoryResource>();
        rsCategoryResources.add(RSCategoryResourceTest.createTestRSCategoryResource(rsCategory,rsResources));
        rsClientResources = createTestRSClientResources(rsCategoryResources);
    }

    @Test
    public void getClientId() {
        assertEquals("default",rsClientResources.getClientId());
    }
    @Test
    public void setClientId() {
        rsClientResources.setClientId("Test");
        assertEquals("Test",rsClientResources.getClientId());
    }

    @Test
    public void getCategoryResources() {
        assertEquals(rsCategoryResources,rsClientResources.getCategoryResources());
    }

    @Test
    public void setCategoryResources() {
        rsClientResources.setCategoryResources(null);
        assertEquals(null,rsClientResources.getCategoryResources());
    }

    @Test
    public void fromJSON() throws Exception {
        RSClientResources compareTo = RSClientResources.fromJSON(rsClientResources.toJSON());
        Assert.assertEquals(rsClientResources, compareTo);
    }

    @Test
    public void toJSON() throws Exception {
        RSClientResources compareTo = RSClientResources.fromJSON(rsClientResources.toJSON());
        Assert.assertEquals(rsClientResources, compareTo);
    }
}
