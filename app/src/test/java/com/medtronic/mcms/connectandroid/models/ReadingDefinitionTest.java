package com.medtronic.mcms.connectandroid.models;

import android.widget.ArrayAdapter;

import com.medtronic.netresponseblelib.Constants;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static org.junit.Assert.*;

/**
 * Created by mrgoodbytes on 3/26/18.
 */
public class ReadingDefinitionTest {

    ReadingDefinition readingDefinition;
    ArrayList<MeasurementDefinition> mDefinitions;
    MeasurementDefinition mDefinition;

    public static ReadingDefinition createTestReadingDefinition() {
        MeasurementDefinition mDefinition1 = new MeasurementDefinition(
                MeasurementDefinition.TYPES.NUMBER,
                Measurement.IDS.WEIGHT,
                "Enter your weight",
                new Unit(Unit.IDS.KILOGRAMS,"kg"),
                new NumberValidator(95.0,300.0,2,"Invalid Weight")
        );

        MeasurementDefinition mDefinition2 = new MeasurementDefinition(
                MeasurementDefinition.TYPES.DATE,
                Measurement.IDS.DATE,
                "Enter the date",
                null,
                new DateTimeValidator(new SimpleDateFormat("yyyy-MM-dd", Locale.US), null, null,"Invalid Date")
        );

        MeasurementDefinition mDefinition3 = new MeasurementDefinition(
                MeasurementDefinition.TYPES.TIME,
                Measurement.IDS.TIME,
                "Enter the time",
                null,
                new DateTimeValidator(new SimpleDateFormat("HH:mm", Locale.US), null, null,"Invalid Time")
        );

        ArrayList<MeasurementDefinition> mDefinitions = new ArrayList<>();
        mDefinitions.add(mDefinition1);
        mDefinitions.add(mDefinition2);
        mDefinitions.add(mDefinition3);

        return new ReadingDefinition(
                "weight",
                "Weight",
                "12345", mDefinitions);
    }

    public static Reading createTestReading1() {
        ReadingDefinition rD = createTestReadingDefinition();

        ArrayList<Measurement> measurements = new ArrayList<>();
        measurements.add(rD.getMeasurementDefinitions().get(0).createMeasurement("100.00"));
        measurements.add(rD.getMeasurementDefinitions().get(1).createMeasurement("01-01-1995"));
        measurements.add(rD.getMeasurementDefinitions().get(2).createMeasurement("10:30"));

        return rD.createReading("manual",1,measurements);
    }

    public static Reading createTestReading2() {
        ReadingDefinition rD = createTestReadingDefinition();

        ArrayList<Measurement> measurements = new ArrayList<>();
        measurements.add(rD.getMeasurementDefinitions().get(0).createMeasurement("250.00"));
        measurements.add(rD.getMeasurementDefinitions().get(1).createMeasurement("01-01-2000"));
        measurements.add(rD.getMeasurementDefinitions().get(2).createMeasurement("10:35"));

        return rD.createReading("peripheral",2,measurements);
    }

    @Before
    public void setup() throws Exception {
        mDefinition = new MeasurementDefinition(
                MeasurementDefinition.TYPES.NUMBER,
                Measurement.IDS.WEIGHT,
                "Enter your weight",
                new Unit(Unit.IDS.KILOGRAMS,"kg"),
                new NumberValidator(95.0,300.0,2,"Invalid Weight")
        );

        mDefinitions = new ArrayList<>();
        mDefinitions.add(mDefinition);

        readingDefinition = new ReadingDefinition(
                "weight",
                "Weight",
                "12345", mDefinitions);
    }

    @Test
    public void getId() throws Exception {
        assertEquals("weight", readingDefinition.getId());
    }

    @Test
    public void setId() throws Exception {
        readingDefinition.setId("Temperature");
        assertEquals("Temperature", readingDefinition.getId());
    }

    @Test
    public void getLabel() throws Exception {
        assertEquals("Weight", readingDefinition.getLabel());
    }

    @Test
    public void setLabel() throws Exception {
        readingDefinition.setLabel("Enter your weight");
        assertEquals("Enter your weight", readingDefinition.getLabel());
    }

    @Test
    public void getTaskId() throws Exception {
        assertEquals("12345", readingDefinition.getTaskId());
    }

    @Test
    public void setTaskId() throws Exception {
        readingDefinition.setTaskId("54321");
        assertEquals("54321", readingDefinition.getTaskId());
    }

    @Test
    public void getMeasurementDefinitions() throws Exception {
        ArrayList<MeasurementDefinition> definitions = readingDefinition.getMeasurementDefinitions();
        assertTrue(mDefinitions.containsAll(definitions));
        assertTrue(definitions.containsAll(mDefinitions));
    }

    @Test
    public void setMeasurementDefinitions() throws Exception {
        ArrayList<MeasurementDefinition> definitions = readingDefinition.getMeasurementDefinitions();
        assertTrue(mDefinitions.containsAll(definitions));
        assertTrue(definitions.containsAll(mDefinitions));
    }

    @Test
    public void addMeasurementDefinition() throws Exception {
        readingDefinition.setMeasurementDefinitions(new ArrayList<MeasurementDefinition>());
        readingDefinition.addMeasurementDefinition(mDefinitions.get(0));
        ArrayList<MeasurementDefinition> definitions = readingDefinition.getMeasurementDefinitions();
        assertTrue(mDefinitions.containsAll(definitions));
        assertTrue(definitions.containsAll(mDefinitions));
    }

    @Test
    public void removeMeasurementDefinition() throws Exception {
        assertEquals(1, readingDefinition.getMeasurementDefinitions().size());
        readingDefinition.removeMeasurementDefinition(0);
        assertEquals(0, readingDefinition.getMeasurementDefinitions().size());
    }

    @Test
    public void removeMeasurementDefinition1() throws Exception {
        assertEquals(1, readingDefinition.getMeasurementDefinitions().size());
        readingDefinition.removeMeasurementDefinition(mDefinition);
        assertEquals(0, readingDefinition.getMeasurementDefinitions().size());
    }

    @Test
    public void createReading() throws Exception {
        Measurement measurement = mDefinition.createMeasurement("200");
        ArrayList<Measurement> measurements = new ArrayList<>();
        measurements.add(measurement);
        Reading reading = readingDefinition.createReading("manual",1,measurements);
        assertEquals(readingDefinition.getId(), reading.getId());
        assertEquals("manual", reading.getSource());
        assertEquals(readingDefinition.getTaskId(), reading.getTaskId());
        assertEquals((Integer)1, reading.getEntryNumber());
        assertEquals(measurements, reading.getMeasurements());
    }

    @Test
    public void equals() throws Exception {
        assertEquals(readingDefinition, readingDefinition);
        ReadingDefinition compareTo = new ReadingDefinition(
                "temperature",
                "Temperature",
                "54321",
                null);
        assertNotEquals(readingDefinition,compareTo);

        compareTo.setId("weight");
        assertNotEquals(readingDefinition,compareTo);
        compareTo.setLabel("Weight");
        assertNotEquals(readingDefinition,compareTo);
        compareTo.setTaskId("12345");
        assertNotEquals(readingDefinition,compareTo);
        compareTo.setMeasurementDefinitions(mDefinitions);
        assertEquals(readingDefinition,compareTo);

        compareTo.setId(null);
        assertNotEquals(readingDefinition,compareTo);
        readingDefinition.setId(null);
        assertEquals(readingDefinition, compareTo);

        compareTo.setLabel(null);
        assertNotEquals(readingDefinition,compareTo);
        readingDefinition.setLabel(null);
        assertEquals(readingDefinition,compareTo);

        compareTo.setTaskId(null);
        assertNotEquals(readingDefinition,compareTo);
        readingDefinition.setTaskId(null);
        assertEquals(readingDefinition,compareTo);

        compareTo.setMeasurementDefinitions(null);
        assertNotEquals(readingDefinition,compareTo);
        readingDefinition.setMeasurementDefinitions(null);
        assertEquals(readingDefinition,compareTo);
    }

    @Test
    public void fromJSON() throws Exception {

        assertEquals(readingDefinition, ReadingDefinition.fromJSON(readingDefinition.toJSON()));
    }

    @Test
    public void toJSON() throws Exception {
        ReadingDefinition compareTo = ReadingDefinition.fromJSON(readingDefinition.toJSON());
        assertEquals(readingDefinition, compareTo);
    }
}