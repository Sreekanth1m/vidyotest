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

public class RSCategoryResourceTest {
    private RSCategoryResource rsCategoryResource;
    private RSCategory rsCategory;
    private ArrayList<RSResource> rsResources;

    public static RSCategoryResource createTestRSCategoryResource(RSCategory rsCategory, ArrayList<RSResource> rsResources) {

        return new RSCategoryResource(
                rsCategory,
                rsResources
        );
    }

    @Before
    public void setup() {
        ArrayList<RSLocale> rsLocales = new ArrayList<RSLocale>();
        rsLocales.add(RSLocaleTest.createTestRSLocale());
        this.rsResources = new ArrayList<RSResource>();
        this.rsResources.add(RSResourceTest.createTestRSResource(rsLocales));
        this.rsCategory = RSCategoryTest.createTestRSCategory(rsLocales);
        rsCategoryResource = createTestRSCategoryResource(rsCategory,rsResources);
    }

    @Test
    public void getCategory() {
        assertEquals(rsCategory,rsCategoryResource.getCategory());
    }

    @Test
    public void setCategory() {
        rsCategoryResource.setCategory(null);
        assertEquals(null,rsCategoryResource.getCategory());
    }

    @Test
    public void getResources() {
        assertEquals(rsResources, rsCategoryResource.getResources());
    }

    @Test
    public void setResources() {
        rsCategoryResource.setResources(null);
        assertEquals(null,rsCategoryResource.getResources());
    }

    @Test
    public void fromJSON() throws Exception {
        RSCategoryResource compareTo = RSCategoryResource.fromJSON(rsCategoryResource.toJSON());
        Assert.assertEquals(rsCategoryResource, compareTo);
    }

    @Test
    public void toJSON() throws Exception {
        RSCategoryResource compareTo = RSCategoryResource.fromJSON(rsCategoryResource.toJSON());
        Assert.assertEquals(rsCategoryResource, compareTo);
    }
}
