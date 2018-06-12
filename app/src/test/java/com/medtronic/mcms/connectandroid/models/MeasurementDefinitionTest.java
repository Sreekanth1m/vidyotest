package com.medtronic.mcms.connectandroid.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mrgoodbytes on 3/27/18.
 */
public class MeasurementDefinitionTest {
    private MeasurementDefinition mDefinition;

    @Before
    public void setup() throws Exception {
        mDefinition = new MeasurementDefinition(
                MeasurementDefinition.TYPES.NUMBER,
                Measurement.IDS.WEIGHT,
                "Enter your weight",
                new Unit(Unit.IDS.KILOGRAMS,"kg"),
                new NumberValidator(95.0,300.0,2,"Invalid Weight")
        );
    }

    @Test
    public void getType() throws Exception {
        assertEquals(MeasurementDefinition.TYPES.NUMBER, mDefinition.getType());
    }

    @Test
    public void setType() throws Exception {
        mDefinition.setType(MeasurementDefinition.TYPES.DATE);
        assertEquals(MeasurementDefinition.TYPES.DATE, mDefinition.getType());
    }

    @Test
    public void getId() throws Exception {
        assertEquals(Measurement.IDS.WEIGHT,mDefinition.getId());
    }

    @Test
    public void setId() throws Exception {
        mDefinition.setId(Measurement.IDS.TEMPERATURE);
        assertEquals(Measurement.IDS.TEMPERATURE,mDefinition.getId());
    }

    @Test
    public void getLabel() throws Exception {
        assertEquals("Enter your weight",mDefinition.getLabel());
    }

    @Test
    public void setLabel() throws Exception {
        mDefinition.setLabel("Weight:");
        assertEquals("Weight:",mDefinition.getLabel());
    }

    @Test
    public void getUnit() throws Exception {
        assertEquals(new Unit(Unit.IDS.KILOGRAMS,"kg"),mDefinition.getUnit());
    }

    @Test
    public void setUnit() throws Exception {
        mDefinition.setUnit(new Unit(Unit.IDS.POUNDS,"pounds"));
        assertEquals(new Unit(Unit.IDS.POUNDS,"pounds"),mDefinition.getUnit());
    }

    @Test
    public void getValidator() throws Exception {
        assertEquals(new NumberValidator(95.0,300.0,2,"Invalid Weight"), mDefinition.getValidator());
    }

    @Test
    public void setValidator() throws Exception {
        NumberValidator v =new NumberValidator(50.0,300.0,2,"Invalid Weight");
        mDefinition.setValidator(v);
        assertEquals(v, mDefinition.getValidator());
    }

    @Test
    public void createMeasurement() throws Exception {
        // Outside validator precision should return null
        assertEquals(null, mDefinition.createMeasurement("50.0"));

        Measurement compareTo = new Measurement(Measurement.IDS.WEIGHT, "100.00",Unit.IDS.KILOGRAMS);
        assertEquals(compareTo, mDefinition.createMeasurement("100.00"));

        // check that when there isn't a validator, creates a measurement
        mDefinition.setValidator(null);
        assertEquals(compareTo, mDefinition.createMeasurement("100.00"));
    }

    @Test
    public void equals() throws Exception {
        assertEquals(mDefinition, mDefinition);
        MeasurementDefinition compareTo = new MeasurementDefinition(
                MeasurementDefinition.TYPES.DATE,
                Measurement.IDS.TEMPERATURE,
                "Enter your temperature",
                new Unit(Unit.IDS.DEGREES_FAHRENHEIT,"F"),
                new NumberValidator(90.0,105.0,2,"Invalid Temperature")
        );
        assertNotEquals(mDefinition,compareTo);

        compareTo.setType(MeasurementDefinition.TYPES.NUMBER);
        assertNotEquals(mDefinition,compareTo);
        compareTo.setId(Measurement.IDS.WEIGHT);
        assertNotEquals(mDefinition,compareTo);
        compareTo.setLabel("Enter your weight");
        assertNotEquals(mDefinition,compareTo);
        compareTo.setUnit(new Unit(Unit.IDS.KILOGRAMS,"kg"));
        assertNotEquals(mDefinition,compareTo);
        compareTo.setValidator(new NumberValidator(95.0,300.0,2,"Invalid Weight"));
        assertEquals(mDefinition,compareTo);

        compareTo.setType(null);
        assertNotEquals(mDefinition, compareTo);
        mDefinition.setType(null);
        assertEquals(mDefinition,compareTo);
        compareTo.setId(null);
        assertNotEquals(mDefinition,compareTo);
        mDefinition.setId(null);
        assertEquals(mDefinition, compareTo);

        compareTo.setLabel(null);
        assertNotEquals(mDefinition,compareTo);
        mDefinition.setLabel(null);
        assertEquals(mDefinition,compareTo);

        compareTo.setUnit(null);
        assertNotEquals(mDefinition,compareTo);
        mDefinition.setUnit(null);
        assertEquals(mDefinition,compareTo);

        compareTo.setValidator(null);
        assertNotEquals(mDefinition,compareTo);
        mDefinition.setValidator(null);
        assertEquals(mDefinition,compareTo);
    }

    @Test
    public void fromJSON() throws Exception {
        MeasurementDefinition compareTo = MeasurementDefinition.fromJSON(mDefinition.toJSON());
        assertEquals(mDefinition, compareTo);
    }

    @Test
    public void toJSON() throws Exception {
        MeasurementDefinition compareTo = MeasurementDefinition.fromJSON(mDefinition.toJSON());
        assertEquals(mDefinition, compareTo);
    }

}