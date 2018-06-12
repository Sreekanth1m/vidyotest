package com.medtronic.mcms.connectandroid.models;

/**
 * Created by trappr2 on 2/2/2018.
 */
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ModelTest {
    @Test
    public void getInstance() throws Exception {
        assertTrue(Model.getInstance() != null);
    }

    @Test
    public void addSurveyQuestion() throws Exception {
        Model model = Model.getInstance();
        model.updateHealthCheck(null);
        assertTrue(model.getHealthCheck().getQuestions().size() == 0);
        String surveyQuestion = "{\"status\":\"InProgress\",\"questions\":[{\"type\":\"radiogroup\",\"name\":\"happiness\",\"extendDynaSchema\":{\"customSchemaData\":{}},\"expressionObject\":{\"visibleIfObj\":{\"children\":[]}},\"title\":\"<p>Are you happy?</p>\\n\",\"choices\":[{\"text\":\"Yes\",\"value\":\"Yes\"},{\"text\":\"No\",\"value\":\"No\"}],\"visibleIf\":\"\"}]}";
        model.updateHealthCheck(surveyQuestion);
        assertTrue(model.getHealthCheck().getQuestions().size() > 0);
    }

    @Test
    public void setCallConfiguration() throws Exception {
    }

    @Test
    public void addNavigationItems() throws Exception {
    }

    @Test
    public void getCurrentSurveyQuestion() throws Exception {

    }

    @Test
    public void resetSurveyQuestions() throws Exception {
    }

    @Test
    public void addBiometric() throws Exception {
    }

    @Test
    public void getBiometics() throws Exception {
    }

    @Test
    public void storeResources() throws Exception {
    }

    @Test
    public void getResources() throws Exception {
    }

    @Test
    public void getReadings() throws Exception {
    }

    @Test
    public void setReadings() throws Exception {
    }

    @Test
    public void setDiastolic() throws Exception {
    }

    @Test
    public void getDiastolic() throws Exception {
    }

    @Test
    public void setSystolic() throws Exception {
    }

    @Test
    public void getSystolic() throws Exception {
    }

    @Test
    public void setOrbitaLoginToken() throws Exception {
    }

    @Test
    public void getOrbitaLoginToken() throws Exception {
    }

    @Test
    public void getPinpointPushID() throws Exception {
    }

    @Test
    public void setPinpointPushID() throws Exception {
    }

    @Test
    public void getPatientId() throws Exception {
    }

    @Test
    public void buildTasksFromDailyTasks() throws Exception {
    }

    @Test
    public void getBloodPressureData() throws Exception {
    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
}