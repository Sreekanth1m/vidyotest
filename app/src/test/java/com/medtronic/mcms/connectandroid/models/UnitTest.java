package com.medtronic.mcms.connectandroid.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mrgoodbytes on 3/9/18.
 */
public class UnitTest {

    private Unit unit;

    @Before
    public void setup() {
        unit = new Unit(Unit.IDS.KILOGRAMS, "Weight in Kilograms");
    }

    @Test
    public void getId() throws Exception {
        assertEquals(Unit.IDS.KILOGRAMS, unit.getId());
    }

    @Test
    public void setId() throws Exception {
        unit.setId(Unit.IDS.POUNDS);
        assertEquals(Unit.IDS.POUNDS, unit.getId());
    }

    @Test
    public void getLabel() throws Exception {
        assertEquals("Weight in Kilograms", unit.getLabel());
    }

    @Test
    public void setLabel() throws Exception {
        unit.setLabel("Weight in Kgs");
        assertEquals("Weight in Kgs", unit.getLabel());
    }

    @Test
    public void equals() throws Exception {
        Unit newUnit = new Unit();
        assertNotEquals(newUnit, unit);
        newUnit.setId(Unit.IDS.POUNDS);
        assertNotEquals(newUnit, unit);
        newUnit.setId(Unit.IDS.KILOGRAMS);
        assertNotEquals(newUnit, unit);
        newUnit.setLabel("Weight in Kgs");
        assertNotEquals(newUnit, unit);
        newUnit.setLabel("Weight in Kilograms");
        assertEquals(newUnit, unit);
    }

    @Test
    public void fromJSON() throws Exception {
        assertEquals(unit, Unit.fromJSON(unit.toJSON()));
    }

    @Test
    public void toJSON() throws Exception {
        assertEquals(unit, Unit.fromJSON(unit.toJSON()));
    }

}