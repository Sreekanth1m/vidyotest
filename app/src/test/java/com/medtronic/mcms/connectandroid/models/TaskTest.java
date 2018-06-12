package com.medtronic.mcms.connectandroid.models;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by trappr2 on 2/2/2018.
 */
public class TaskTest {
    Task task;
    @Before
    public void setUp() throws Exception {
        String jsonString = "{\"title\":\"testTitle\"," +
                "\"description\":\"testDescription\"," +
                "\"startDate\":\"testStartDate\"," +
                "\"endDate\":\"testEndDate\"," +
                "\"isAllDay\":\"testIsAllDay\"," +
                "\"reminder\":[]," +
                "\"isDeleted\":true," +
                "\"status\":\"testStatus\"," +
                "\"keepUpdatesOfCompletion\":true," +
                "\"taskTemplateId\":\"testTaskTemplateId\"}";
        JSONObject jsonObject = new JSONObject(jsonString);
        task = new Task(jsonObject);
    }

    @Test
    public void getTitle() throws Exception {
        assertTrue(task.getTitle().equals("testTitle"));
    }

    @Test
    public void setTitle() throws Exception {
        task.setTitle("newTitle");
        assertTrue(!task.getTitle().equals("testTitle"));
        assertTrue(task.getTitle().equals("newTitle"));
    }

    @Test
    public void getDescription() throws Exception {
        assertTrue(task.getDescription().equals("testDescription"));
    }

    @Test
    public void setDescription() throws Exception {
        task.setDescription("newDescription");
        assertTrue(!task.getDescription().equals("testDescription"));
        assertTrue(task.getDescription().equals("newDescription"));
    }

    @Test
    public void getStartDate() throws Exception {
        assertTrue(task.getStartDate().equals("testStartDate"));
    }

    @Test
    public void setStartDate() throws Exception {
        task.setStartDate("newStartDate");
        assertTrue(!task.getStartDate().equals("testStartDate"));
        assertTrue(task.getStartDate().equals("newStartDate"));
    }

    @Test
    public void getEndDate() throws Exception {
        assertTrue(task.getEndDate().equals("testEndDate"));
    }

    @Test
    public void setEndDate() throws Exception {
        task.setEndDate("newEndDate");
        assertTrue(!task.getEndDate().equals("testEndDate"));
        assertTrue(task.getEndDate().equals("newEndDate"));
    }

    @Test
    public void getIsAllDay() throws Exception {
        assertTrue(task.getIsAllDay().equals("testIsAllDay"));
    }

    @Test
    public void setIsAllDay() throws Exception {
        task.setIsAllDay("newIsAllDay");
        assertTrue(!task.getIsAllDay().equals("testIsAllDay"));
        assertTrue(task.getIsAllDay().equals("newIsAllDay"));
    }

    @Test
    public void getReminder() throws Exception {
        assertTrue(task.getReminder().length() == 0);
    }

    @Test
    public void setReminder() throws Exception {
    }

    @Test
    public void getFrequency() throws Exception {
    }

    @Test
    public void setFrequency() throws Exception {
    }

    @Test
    public void isDeleted() throws Exception {
        assertTrue(task.isDeleted());
    }

    @Test
    public void setDeleted() throws Exception {
        task.setDeleted(false);
        assertTrue(!task.isDeleted());
    }

    @Test
    public void getStatus() throws Exception {
        assertTrue(task.getStatus().equals("testStatus"));
    }

    @Test
    public void setStatus() throws Exception {
        task.setStatus("newStatus");
        assertTrue(!task.getStatus().equals("testStatus"));
        assertTrue(task.getStatus().equals("newStatus"));
    }

    @Test
    public void isKeepUpdatesOfCompletion() throws Exception {
        assertTrue(task.isKeepUpdatesOfCompletion());
    }

    @Test
    public void setKeepUpdatesOfCompletion() throws Exception {
        task.setKeepUpdatesOfCompletion(false);
        assertTrue(!task.isKeepUpdatesOfCompletion());
    }

    @Test
    public void getTaskTemplateId() throws Exception {
        assertTrue(task.getTaskTemplateId().equals("testTaskTemplateId"));
    }

    @Test
    public void setTaskTemplateId() throws Exception {
        task.setTaskTemplateId("newTaskTemplateId");
        assertTrue(!task.getTaskTemplateId().equals("testTaskTemplateId"));
        assertTrue(task.getTaskTemplateId().equals("newTaskTemplateId"));
    }

}