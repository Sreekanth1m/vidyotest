package com.medtronic.mcms.connectandroid.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.*;

/**
 * Created by mrgoodbytes on 2/28/18.
 */
public class ImageMetaDataTest {
    private ImageMetaData imageMetaData;
    private static final SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);

    private String serverString = "{\"createdAt\":\"2018-02-27T20:28:30.902Z\",\"modifiedAt\":\"2018-02-28T21:26:16.236Z\",\"version\":\"0\",\"tags\":\"[client1]\",\"id\":\"5a95bf6ef8fadda616f7d450\"}";
    private String imageMetaDataJson = "{\n" +
            "    \"createdAt\": \"2018-02-27T20:28:30.902Z\",\n" +
            "    \"modifiedAt\": \"2018-02-28T21:26:16.236Z\",\n" +
            "    \"version\": \"1\",\n" +
            "    \"tags\": [\n" +
            "        \"client1\"\n" +
            "    ],\n" +
            "    \"id\": \"5a95bf6ef8fadda616f7d450\"\n" +
            "}";
    @Before
    public void setUp() {
        try {
            imageMetaData = new ImageMetaData(imageMetaDataJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getCreatedAt() throws Exception {
        Date createdDate = mDateFormat.parse("2018-02-27T20:28:30.902Z");
        assertEquals(createdDate, imageMetaData.getCreatedAt());
    }

    @Test
    public void setCreatedAt() throws Exception {
        Date createdDate = mDateFormat.parse("2019-02-27T20:28:30.902Z");
        imageMetaData.setCreatedAt(createdDate);
        assertEquals(createdDate, imageMetaData.getCreatedAt());
    }

    @Test
    public void getModifiedAt() throws Exception {
        Date modifiedDate = mDateFormat.parse("2018-02-28T21:26:16.236Z");
        assertEquals(modifiedDate, imageMetaData.getModifiedAt());
    }

    @Test
    public void setModifiedAt() throws Exception {
        Date modifiedDate = mDateFormat.parse("2019-02-27T20:28:30.902Z");
        imageMetaData.setCreatedAt(modifiedDate);
        assertEquals(modifiedDate, imageMetaData.getCreatedAt());
    }

    @Test
    public void getVersion() throws Exception {
        String version = "1";
        assertEquals(version, imageMetaData.getVersion());
    }

    @Test
    public void setVersion() throws Exception {
        String version = "2";
        imageMetaData.setVersion(version);
        assertEquals(version, imageMetaData.getVersion());
    }

    @Test
    public void getTags() throws Exception {
        ArrayList<String> tags = new ArrayList<String>(Arrays.asList(new String[]{"client1"}));
        assertTrue(imageMetaData.getTags().containsAll(tags));
        assertTrue(tags.containsAll(imageMetaData.getTags()));
    }

    @Test
    public void setTags() throws Exception {
        ArrayList<String> tags = new ArrayList<String>(Arrays.asList(new String[]{"client2"}));
        imageMetaData.setTags(tags);
        assertTrue(imageMetaData.getTags().containsAll(tags));
        assertTrue(tags.containsAll(imageMetaData.getTags()));
    }

    @Test
    public void getId() throws Exception {
        String id = "5a95bf6ef8fadda616f7d450";
        assertEquals(id,imageMetaData.getId());
    }

    @Test
    public void setId() throws Exception {
        String id = "12345";
        imageMetaData.setId(id);
        assertEquals(id,imageMetaData.getId());
    }

    @Test
    public void toJson() throws Exception {
        JSONObject jsonObject = new JSONObject(imageMetaDataJson);
        assertEquals(jsonObject.toString(),imageMetaData.toJson());
    }

    @Test
    public void fromJson(){
        ImageMetaData data = ImageMetaData.fromJSON(serverString);
        assertEquals(data.getId(),"5a95bf6ef8fadda616f7d450");
    }

}