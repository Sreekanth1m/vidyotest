package com.medtronic.mcms.connectandroid.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
//import org.junit.runner.RunWith;
//import org.robolectric.Robolectric;
//import org.robolectric.RobolectricTestRunner;

/**
 * Created by trappr2 on 2/2/2018.
 */
//@RunWith(RobolectricTestRunner.class)
public class NavigationItemTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void setId() throws Exception {
        NavigationItem navigationItem = new NavigationItem();
        navigationItem.setId(null);
        assertTrue(navigationItem.getId() == null);
        navigationItem.setId(NavigationItem.IDS.READINGS);
        assertTrue(navigationItem.getId().equals("readings"));
        navigationItem.setId(NavigationItem.IDS.RESOURCES);
        assertTrue(navigationItem.getId().equals("resources"));
        navigationItem.setId(NavigationItem.IDS.SETTINGS);
        assertTrue(navigationItem.getId().equals("settings"));
    }

    @Test
    public void setLabel() throws Exception {
        NavigationItem navigationItem = new NavigationItem();
        navigationItem.setId(null);
        assertTrue(navigationItem.getId() == null);
        navigationItem.setId("readings");
        assertTrue(navigationItem.getId().equals("readings"));
    }

    @Test
    public void setUrl() throws Exception {
        NavigationItem navigationItem = new NavigationItem();
        navigationItem.setUrl(null);
        assertTrue(navigationItem.getUrl() == null);
        navigationItem.setUrl("http://www.google.com");
        assertTrue(navigationItem.getUrl().equals("http://www.google.com"));
    }

    @Test
    public void setDescription() throws Exception {
        NavigationItem navigationItem = new NavigationItem();
        navigationItem.setDescription(null);
        assertTrue(navigationItem.getDescription() == null);
        navigationItem.setDescription("This is a test description");
        assertTrue(navigationItem.getDescription().equals("This is a test description"));
    }

    @Test
    public void fromJSON() throws Exception {
        String inputString = "{\"id\":\"testId\",\"label\":\"testLabel\",\"url\":\"testUrl\",\"description\":\"testDescription\"}";
        NavigationItem navItem = NavigationItem.fromJSON(inputString);
        assertTrue(navItem.getDescription().equals("testDescription"));
        assertTrue(navItem.getUrl().equals("testUrl"));
        assertTrue(navItem.getId().equals("testId"));
        assertTrue(navItem.getLabel().equals("testLabel"));

    }

}