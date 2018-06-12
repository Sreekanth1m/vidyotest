package com.medtronic.mcms.connectandroid.models;

import android.icu.util.Measure;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by trappr2 on 2/2/2018.
 */
public class MeasurementTest {

    Measurement measurement;

    @Before
    public void setUp() throws Exception {
        measurement = new Measurement(Measurement.IDS.WEIGHT,
                "100.100",
                "US Pounds");
    }

    @Test
    public void getId() throws Exception {
        assertEquals(Measurement.IDS.WEIGHT, measurement.getId());
    }

    @Test
    public void setId() throws Exception {
        measurement.setId(Measurement.IDS.DIASTOLIC);
        assertEquals(Measurement.IDS.DIASTOLIC, measurement.getId());
    }

    @Test
    public void getUnit() throws Exception {
        assertEquals( "US Pounds", measurement.getUnit());
    }

    @Test
    public void setUnit() throws Exception {
        measurement.setUnit("Kilograms");
        assertEquals( "Kilograms", measurement.getUnit());
    }

    @Test
    public void getValue() throws Exception {
        assertEquals(measurement.getValue(), "100.100");
    }

    @Test
    public void setValue() throws Exception {
        measurement.setValue("123.123");
        assertNotEquals(measurement.getValue(), "100.1");
        assertEquals(measurement.getValue(), "123.123");
    }

    @Test
    public void equals() throws Exception {
        assertEquals(measurement, measurement);
        Measurement compareTo = new Measurement(Measurement.IDS.DATE,"10:00 AM","Kilograms");
        assertNotEquals(measurement,compareTo);
        compareTo.setId(Measurement.IDS.WEIGHT);
        assertNotEquals(measurement,compareTo);
        compareTo.setValue("100.100");
        assertNotEquals(measurement,compareTo);
        compareTo.setUnit("US Pounds");
        assertEquals(measurement,compareTo);

        compareTo.setId(null);
        assertNotEquals(measurement,compareTo);
        measurement.setId(null);
        assertEquals(measurement, compareTo);
        compareTo.setValue(null);
        assertNotEquals(measurement,compareTo);
        measurement.setValue(null);
        assertEquals(measurement,compareTo);
        compareTo.setUnit(null);
        assertNotEquals(measurement,compareTo);
        measurement.setUnit(null);
        assertEquals(measurement,compareTo);
    }

    @Test
    public void fromJSON() throws Exception {
        Measurement compareTo = Measurement.fromJSON(measurement.toJSON());
        assertEquals(measurement, compareTo);
    }

    @Test
    public void toJSON() throws Exception {
        Measurement compareTo = Measurement.fromJSON(measurement.toJSON());
        assertEquals(measurement, compareTo);
    }
}