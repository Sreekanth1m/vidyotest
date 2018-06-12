package com.medtronic.mcms.connectandroid.models;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by mrgoodbytes on 3/29/18.
 */
public class HealthCheckStepTest {
    private HealthCheckStep healthCheck;
    private Reason reason;
    private ArrayList<Statement> statements;
    private ArrayList<SurveyQuestion> questions;
    private ArrayList<Biometric> biometrics;

    @Before
    public void setup() throws Exception {

        reason = ReasonTest.createTestReason();

        statements = new ArrayList<>();
        statements.add(StatementTest.createTestStatement());

        questions = new ArrayList<>();
        questions.add(SurveyQuestionTest.createTestSurveyQuestion());

        ArrayList<Reading> readings = new ArrayList<>();
        readings.add(ReadingDefinitionTest.createTestReading1());
        biometrics = new ArrayList<Biometric>();
        biometrics.add(BiometricTest.createTestBiometric(readings));

        healthCheck = new HealthCheckStep(HealthCheckStep.STATUS.BIOMETRIC_IN_PROGRESS,
                reason,
                statements,
                questions,
                biometrics);
        Log.d("healthCheckJson", healthCheck.toJSON());
    }

    @Test
    public void getStatus() throws Exception {
        Log.d("healthCheckJson", healthCheck.toJSON());
        assertEquals(HealthCheckStep.STATUS.BIOMETRIC_IN_PROGRESS, healthCheck.getStatus());
    }

    @Test
    public void setStatus() throws Exception {
        healthCheck.setStatus(HealthCheckStep.STATUS.START);
        assertEquals(HealthCheckStep.STATUS.START, healthCheck.getStatus());
    }

    @Test
    public void getReason() throws Exception {
        assertEquals(reason, healthCheck.getReason());
    }

    @Test
    public void setReason() throws Exception {
        healthCheck.setReason(null);
        assertEquals(null, healthCheck.getReason());
    }

    @Test
    public void getStatements() throws Exception {
        assertEquals(statements, healthCheck.getStatements());
    }

    @Test
    public void setStatements() throws Exception {
        ArrayList<Statement> compareTo = new ArrayList<Statement>();
        healthCheck.setStatements(compareTo);
        assertEquals(compareTo, healthCheck.getStatements());
    }

    @Test
    public void getQuestions() throws Exception {
        assertEquals(questions, healthCheck.getQuestions());
    }

    @Test
    public void setQuestions() throws Exception {
        ArrayList<SurveyQuestion> compareTo = new ArrayList<SurveyQuestion>();
        healthCheck.setQuestions(compareTo);
        assertEquals(compareTo, healthCheck.getQuestions());
    }

    @Test
    public void getBiometrics() throws Exception {
        assertEquals(biometrics, healthCheck.getBiometrics());
    }

    @Test
    public void setBiometrics() throws Exception {
        ArrayList<Biometric> compareTo = new ArrayList<Biometric>();
        healthCheck.setBiometrics(compareTo);
        assertEquals(compareTo, healthCheck.getBiometrics());
    }

    @Test
    public void equals() throws Exception {
        assertEquals(healthCheck, healthCheck);

        HealthCheckStep compareTo = new HealthCheckStep(
                HealthCheckStep.STATUS.BIOMETRIC_INVALID,
                new Reason("notequal","2","3"),
                new ArrayList<Statement>(),
                new ArrayList<SurveyQuestion>(),
                new ArrayList<Biometric>()
        );
        assertNotEquals(healthCheck, compareTo);
        compareTo.setStatus(HealthCheckStep.STATUS.BIOMETRIC_IN_PROGRESS);
        assertNotEquals(healthCheck, compareTo);
        compareTo.setReason(reason);
        assertNotEquals(healthCheck, compareTo);
        compareTo.setStatements(statements);
        assertNotEquals(healthCheck, compareTo);
        compareTo.setQuestions(questions);
        assertNotEquals(healthCheck, compareTo);
        compareTo.setBiometrics(biometrics);
        assertEquals(healthCheck, compareTo);

        compareTo.setStatus(null);
        assertNotEquals(healthCheck, compareTo);
        healthCheck.setStatus(null);
        assertEquals(healthCheck, compareTo);

        compareTo.setReason(null);
        assertNotEquals(healthCheck, compareTo);
        healthCheck.setReason(null);
        assertEquals(healthCheck, compareTo);

        compareTo.setStatements(null);
        assertNotEquals(healthCheck, compareTo);
        healthCheck.setStatements(null);
        assertEquals(healthCheck, compareTo);

        compareTo.setQuestions(null);
        assertNotEquals(healthCheck, compareTo);
        healthCheck.setQuestions(null);
        assertEquals(healthCheck, compareTo);

        compareTo.setBiometrics(null);
        assertNotEquals(healthCheck, compareTo);
        healthCheck.setBiometrics(null);
        assertEquals(healthCheck, compareTo);
    }

    @Test
    public void fromJSON() throws Exception {
        HealthCheckStep compareTo = HealthCheckStep.fromJSON(healthCheck.toJSON());
        assertEquals(healthCheck, compareTo);
    }

    @Test
    public void toJSON() throws Exception {
        HealthCheckStep compareTo = HealthCheckStep.fromJSON(healthCheck.toJSON());
        assertEquals(healthCheck, compareTo);
    }

}