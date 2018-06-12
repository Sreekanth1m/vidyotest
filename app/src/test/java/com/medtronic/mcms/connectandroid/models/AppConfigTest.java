package com.medtronic.mcms.connectandroid.models;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mrgoodbytes on 2/16/18.
 */

public class AppConfigTest {
    private final String JSON_STRING = "{\"clientId\":\"007\",\"primaryColor\": \"#42f448\",\"secondaryColor\":\"#c141f4\",\"supportNumber\":\"&#43;1&#8211;888&#8211;243&#8211;8881\"}";
    private static JSONObject JSON_OBJECT;

    @Before
    public void SetUp() throws Exception {
        JSON_OBJECT = new JSONObject(JSON_STRING);
    }
    @Test
    public void FromJson() {
        AppConfig appconfig = AppConfig.FromJson(JSON_STRING);
        assertEquals("007",appconfig.getClientID());
        assertEquals("#42f448",appconfig.getPrimaryColor());
        assertEquals("#c141f4",appconfig.getSecondaryColor());
        assertEquals("&#43;1&#8211;888&#8211;243&#8211;8881",appconfig.getSupportNumber());
    }
    @Test
    public void toJson() throws Exception {
        AppConfig appconfig = new AppConfig();
        appconfig.setClientID("007");
        appconfig.setPrimaryColor("#42f448");
        appconfig.setSecondaryColor("#c141f4");
        appconfig.setSupportNumber("&#43;1&#8211;888&#8211;243&#8211;8881");
        assertEquals(JSON_OBJECT.toString(),new JSONObject(appconfig.toJson()).toString());
    }
}
