package com.medtronic.mcms.connectandroid.customview;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by mrgoodbytes on 3/2/18.
 */
@RunWith(AndroidJUnit4.class)
public class VMeasurementFieldTest {

    private Context appContext = InstrumentationRegistry.getTargetContext();
    private MeasurementTextField testField;

    @Before
    public void setUp() throws Exception {
        MeasurementDefinition definition = new MeasurementDefinition(
                MeasurementDefinition.TYPES.NUMBER,
                Measurement.IDS.WEIGHT,
                "Enter your weight",
                new Unit("weight", "Kilograms"),
                null
        );
//        testField = new MeasurementTextField(appContext, definition);
    }

    @Test
    public void getLabel() throws Exception {
        assertEquals("Enter text here",testField.getLabel());
    }

    @Test
    public void setLabel() throws Exception {
        testField.setLabel("Hello");
        assertEquals("Hello",testField.getLabel());
    }

    @Test
    public void getValue() throws Exception {
        assertEquals(5.0,testField.getValue());
    }

    @Test
    public void setValue() throws Exception {
//        testField.setValue(135.0);
//        assertEquals(135.0,testField.getValue());
    }

    @Test
    public void getUnit() throws Exception {
//        assertEquals("Kilograms", testField.getUnitLabel());
    }

    @Test
    public void isValid() throws Exception {
        testField.setValue("Hello");
        assertEquals(false, testField.isValid());
        testField.setValue("-1");
        assertEquals(false, testField.isValid());
        testField.setValue("10.12");
        assertEquals(false, testField.isValid());
        testField.setValue("1.1");
        assertEquals(false, testField.isValid());
        testField.setValue("1.12");
        assertEquals(true, testField.isValid());
        testField.setValue("1.123");
        assertEquals(true, testField.isValid());
    }

    @Test
    public void testEditText() throws Exception {
        EditText et = new EditText(appContext);
        et.setText("Hello?");
        assertEquals("Hello?",et.getText().toString());
    }

}