package com.medtronic.mcms.connectandroid.models;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by mrgoodbytes on 3/9/18.
 */
public class ReadingTest {
    private Reading reading;
    private ArrayList<Measurement> measurements;
    private Measurement measurement;

    @Before
    public void setUp() {
        measurement = new Measurement(Measurement.IDS.DATE,"2018-03-28",null);
        measurements = new ArrayList<>();
        measurements.add(measurement);

        reading = new Reading("weight",
                "manual",
                "12345",
                5,
                measurements
        );
    }

    @Test
    public void getId() throws Exception {
        assertEquals("weight", reading.getId());
    }

    @Test
    public void setId() throws Exception {
        reading.setId("temperature");
        assertEquals("temperature", reading.getId());
    }

    @Test
    public void getSource() throws Exception {
        assertEquals("manual", reading.getSource());
    }

    @Test
    public void setSource() throws Exception {
        reading.setSource("peripheral");
        assertEquals("peripheral", reading.getSource());
    }

    @Test
    public void getTaskId() throws Exception {
        assertEquals("12345", reading.getTaskId());
    }

    @Test
    public void setTaskId() throws Exception {
        reading.setTaskId("1");
        assertEquals("1", reading.getTaskId());
    }

    @Test
    public void getEntryNumber() throws Exception {
        assertEquals("5", reading.getEntryNumber());
    }

    @Test
    public void setEntryNumber() throws Exception {
        reading.setEntryNumber(1);
        assertEquals((Integer)1, reading.getEntryNumber());
    }

    @Test
    public void getMeasurements() throws Exception {
        assertEquals(measurements, reading.getMeasurements());
    }

    @Test
    public void setMeasurements() throws Exception {
        reading.setMeasurements(null);
        assertNotEquals(null, reading.getMeasurements());
        assertEquals(new ArrayList<>(), reading.getMeasurements());
    }

    @Test
    public void addMeasurement() throws Exception {
        assertEquals(1, reading.getMeasurements().size());
        Measurement m = new Measurement(Measurement.IDS.WEIGHT,"100.00", Unit.IDS.KILOGRAMS);
        reading.addMeasurement(m);
        assertEquals(2,reading.getMeasurements().size());
        assertTrue(reading.getMeasurements().contains(m));
    }

    @Test
    public void removeMeasurement() throws Exception {
        assertEquals(1, reading.getMeasurements().size());
        reading.removeMeasurement(0);
        assertEquals(0, reading.getMeasurements().size());
    }

    @Test
    public void removeMeasurement1() throws Exception {
        assertEquals(1, reading.getMeasurements().size());
        Measurement m = new Measurement(Measurement.IDS.WEIGHT,"100.00", Unit.IDS.KILOGRAMS);
        reading.addMeasurement(m);
        assertEquals(2,reading.getMeasurements().size());
        assertTrue(reading.getMeasurements().contains(m));
        reading.removeMeasurement(m);
        assertEquals(1, reading.getMeasurements().size());
        assertFalse(reading.getMeasurements().contains(m));
    }

    @Test
    public void getDateMeasurement() throws Exception {
        assertEquals(measurement, reading.getDateMeasurement());
    }

    @Test
    public void equals() throws Exception {
        assertEquals(reading, reading);
        Reading compareTo = new Reading("temperature","peripheral","54321",5,null);
        assertNotEquals(reading,compareTo);
        compareTo.setId("weight");
        assertNotEquals(reading,compareTo);
        compareTo.setSource("manual");
        assertNotEquals(reading,compareTo);
        compareTo.setTaskId("12345");
        assertNotEquals(reading,compareTo);
        compareTo.setEntryNumber(5);
        assertNotEquals(reading,compareTo);
        compareTo.setMeasurements(measurements);
        assertEquals(reading,compareTo);

        compareTo.setId(null);
        assertNotEquals(reading,compareTo);
        reading.setId(null);
        assertEquals(reading, compareTo);
        compareTo.setSource(null);
        assertNotEquals(reading,compareTo);
        reading.setSource(null);
        assertEquals(reading,compareTo);
        compareTo.setTaskId(null);
        assertNotEquals(reading,compareTo);
        reading.setTaskId(null);
        assertEquals(reading,compareTo);

        compareTo.setEntryNumber(null);
        assertNotEquals(reading,compareTo);
        reading.setEntryNumber(null);
        assertEquals(reading,compareTo);

        compareTo.setMeasurements(null);
        assertNotEquals(reading,compareTo);
        reading.setMeasurements(null);
        assertEquals(reading,compareTo);
    }

    @Test
    public void fromJSON() throws Exception {
        Reading toCompare = Reading.fromJSON(reading.toJSON());
        assertEquals(reading, toCompare);
    }

    @Test
    public void toJSON() throws Exception {
        Reading toCompare = Reading.fromJSON(reading.toJSON());
        assertEquals(reading, toCompare);
    }

    @Test
    public void measurementsToJSONArray() throws Exception {
        JSONArray compareTo = new JSONArray();
        compareTo.put(new JSONObject(measurement.toJSON()));
        assertEquals(reading.measurementsToJSONArray().length(), compareTo.length());
        assertEquals(Measurement.fromJSON(compareTo.get(0).toString()),
                Measurement.fromJSON(reading.measurementsToJSONArray().get(0).toString())
        );
    }
}