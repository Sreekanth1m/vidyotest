package com.medtronic.mcms.connectandroid.models;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static org.junit.Assert.*;

/**
 * Created by mrgoodbytes on 3/9/18.
 */
public class BiometricTest {
    private Biometric biometric;
    private ArrayList<Reading> readings;

    public static Biometric createTestBiometric(ArrayList<Reading> readings) {

        readings = new ArrayList<>();

        readings.add(ReadingDefinitionTest.createTestReading1());

        return new Biometric(
                "1",
                "A timezone",
                ReadingDefinitionTest.createTestReadingDefinition(),
                readings
        );
    }

    @Before
    public void setup() {
        readings = new ArrayList<>();

        readings.add(ReadingDefinitionTest.createTestReading1());

        biometric = createTestBiometric(readings);
    }

    @Test
    public void getTimeZoneID() throws Exception {
        assertEquals("1", biometric.getTimeZoneID());
    }

    @Test
    public void setTimeZoneID() throws Exception {
        biometric.setTimeZoneID("2");
        assertEquals("2", biometric.getTimeZoneID());
    }

    @Test
    public void getTimeZoneShortText() throws Exception {
        assertEquals("A timezone", biometric.getTimeZoneShortText());
    }

    @Test
    public void setTimeZoneShortText() throws Exception {
        biometric.setTimeZoneShortText("Another Timezone");
        assertEquals("Another Timezone", biometric.getTimeZoneShortText());
    }

    @Test
    public void getReadingDefinition() throws Exception {
        assertEquals(ReadingDefinitionTest.createTestReadingDefinition(),
                biometric.getReadingDefinition());
    }

    @Test
    public void setReadingDefinition() throws Exception {
        ReadingDefinition newRD = ReadingDefinitionTest.createTestReadingDefinition();
        newRD.setTaskId("5");
        biometric.setReadingDefinition(newRD);
        assertEquals(newRD, biometric.getReadingDefinition());
    }

    @Test
    public void hasReadings() throws Exception {
        assertTrue(biometric.hasReadings());
        biometric.setReadings(null);
        assertFalse(biometric.hasReadings());
    }

    @Test
    public void getReadings() throws Exception {
        assertEquals(readings,
                biometric.getReadings());
    }

    @Test
    public void setReadings() throws Exception {
        biometric.setReadings(null);
        assertEquals(new ArrayList<Reading>(),biometric.getReadings());
    }

    @Test
    public void addReading() throws Exception {
        assertEquals(1, biometric.getReadings().size());

        Reading newReading = ReadingDefinitionTest.createTestReading2();
        assertFalse(biometric.getReadings().contains(newReading));

        biometric.addReading(newReading);

        assertEquals(2, biometric.getReadings().size());
        assertTrue(biometric.getReadings().contains(newReading));
    }

    @Test
    public void removeReading() throws Exception {
        assertEquals(1, biometric.getReadings().size());
        assertTrue(biometric.getReadings().contains(ReadingDefinitionTest.createTestReading1()));

        biometric.removeReading(0);

        assertEquals(0, biometric.getReadings().size());
    }

    @Test
    public void removeReading1() throws Exception {
        assertEquals(1, biometric.getReadings().size());
        assertTrue(biometric.getReadings().contains(ReadingDefinitionTest.createTestReading1()));

        biometric.removeReading(ReadingDefinitionTest.createTestReading1());

        assertEquals(0, biometric.getReadings().size());
    }

    @Test
    public void equals() throws Exception {
        assertEquals(biometric, biometric);

        Biometric compareTo = new Biometric(
                "2",
                "Another Timezone",
                null,
                new ArrayList<Reading>());

        assertNotEquals(biometric,compareTo);
        compareTo.setTimeZoneID("1");
        assertNotEquals(biometric,compareTo);
        compareTo.setTimeZoneShortText("A timezone");
        assertNotEquals(biometric,compareTo);
        compareTo.setReadingDefinition(ReadingDefinitionTest.createTestReadingDefinition());
        assertNotEquals(biometric,compareTo);
        compareTo.setReadings(readings);
        assertEquals(biometric,compareTo);

        compareTo.setTimeZoneID(null);
        assertNotEquals(biometric,compareTo);
        biometric.setTimeZoneID(null);
        assertEquals(biometric,compareTo);

        compareTo.setTimeZoneShortText(null);
        assertNotEquals(biometric,compareTo);
        biometric.setTimeZoneShortText(null);
        assertEquals(biometric,compareTo);

        compareTo.setReadingDefinition(null);
        assertNotEquals(biometric,compareTo);
        biometric.setReadingDefinition(null);
        assertEquals(biometric,compareTo);

        compareTo.setReadings(null);
        assertNotEquals(biometric,compareTo);
        biometric.setReadings(null);
        assertEquals(biometric,compareTo);
    }

    @Test
    public void fromJSON() throws Exception {
        Biometric toCompare = Biometric.fromJSON(biometric.toJSON());
        assertEquals(biometric, toCompare);
    }

    @Test
    public void toJSON() throws Exception {
        Biometric toCompare = Biometric.fromJSON(biometric.toJSON());
        System.out.println(biometric.toJSON());
        assertEquals(biometric, toCompare);
    }

    @Test
    public void readingsToJSONArray() throws Exception {
        JSONArray compareTo = new JSONArray();
        compareTo.put(new JSONObject(ReadingDefinitionTest.createTestReading1().toJSON()));
        assertEquals(biometric.readingsToJSONArray().length(), compareTo.length());
        assertEquals(Reading.fromJSON(compareTo.get(0).toString()),
                Reading.fromJSON(biometric.readingsToJSONArray().get(0).toString())
        );
    }
}