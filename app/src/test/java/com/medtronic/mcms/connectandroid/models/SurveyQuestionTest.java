package com.medtronic.mcms.connectandroid.models;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by trappr2 on 2/2/2018.
 */
public class SurveyQuestionTest {
    private SurveyQuestion surveyQuestion;
    private ArrayList<Choice> choices;
    private ArrayList<IValidator> validators;

    public static SurveyQuestion createTestSurveyQuestion() {
        SurveyQuestion toReturn = new SurveyQuestion();
        toReturn.setType(SurveyQuestion.TYPES.RADIO_GROUP);
        toReturn.setTaskId("12345");
        toReturn.setName("question");
        toReturn.setTitle("This is a radio question");
        toReturn.setText("This is question text");
        toReturn.setRichText("<b>This is rich text</b>");

        ArrayList<Choice> choices = new ArrayList<>();
        choices.add(new Choice("one","1"));
        choices.add(new Choice("two", "2"));
        toReturn.setChoices(choices);

        ArrayList<IValidator> validators = new ArrayList<>();
        validators.add(new NumberValidator(0.0,5.0,null,"Bad number"));

        toReturn.setAnswer("1");
        toReturn.setValidators(validators);

        return toReturn;
    }

    @Before
    public void setup() {
        validators = new ArrayList<>();
        validators.add(new NumberValidator(0.0,5.0,null,"Bad number"));

        choices = new ArrayList<>();
        choices.add(new Choice("one","1"));
        choices.add(new Choice("two", "2"));

        surveyQuestion = createTestSurveyQuestion();
    }

    @Test
    public void getAnswer() throws Exception {
        assertEquals("1", surveyQuestion.getAnswer());
    }

    @Test
    public void setAnswer() throws Exception {
        surveyQuestion.setAnswer("2");
        assertEquals("2", surveyQuestion.getAnswer());
    }

    @Test
    public void getType() throws Exception {
        assertEquals(SurveyQuestion.TYPES.RADIO_GROUP, surveyQuestion.getType());
    }

    @Test
    public void setType() throws Exception {
        surveyQuestion.setType(SurveyQuestion.TYPES.RATING);
        assertEquals(SurveyQuestion.TYPES.RATING, surveyQuestion.getType());
    }

    @Test
    public void getTaskId() throws Exception {
        assertEquals("12345", surveyQuestion.getTaskId());
    }

    @Test
    public void setTaskId() throws Exception {
        surveyQuestion.setTaskId("54321");
        assertEquals("54321", surveyQuestion.getTaskId());
    }

    @Test
    public void getName() throws Exception {
        assertEquals("question", surveyQuestion.getName());
    }

    @Test
    public void setName() throws Exception {
        surveyQuestion.setName("radio");
        assertEquals("radio", surveyQuestion.getName());
    }

    @Test
    public void getTitle() throws Exception {
        assertEquals("This is a radio question", surveyQuestion.getTitle());
    }

    @Test
    public void setTitle() throws Exception {
        surveyQuestion.setTitle("This is another question");
        assertEquals("This is another question", surveyQuestion.getTitle());
    }

    @Test
    public void getText() throws Exception {
        assertEquals("This is question text", surveyQuestion.getText());
    }

    @Test
    public void setText() throws Exception {
        surveyQuestion.setText("This is text");
        assertEquals("This is text", surveyQuestion.getText());
    }

    @Test
    public void getRichText() throws Exception {
        assertEquals("<b>This is rich text</b>", surveyQuestion.getRichText());
    }

    @Test
    public void setRichText() throws Exception {
        surveyQuestion.setRichText("<h1>This is big</h1>");
        assertEquals("<h1>This is big</h1>", surveyQuestion.getRichText());
    }

    @Test
    public void getChoices() throws Exception {
        assertEquals(choices, surveyQuestion.getChoices());
    }

    @Test
    public void setChoices() throws Exception {
        surveyQuestion.setChoices(null);
        assertEquals(null, surveyQuestion.getChoices());
    }

    @Test
    public void getValidators() throws Exception {
        assertEquals(validators, surveyQuestion.getValidators());
    }

    @Test
    public void setValidators() throws Exception {
        IValidator newValidator = new NumberValidator( 0.0, 5.0, null,"Invalid number");
        ArrayList newValidators = new ArrayList<IValidator>();
        newValidators.add(newValidator);
        surveyQuestion.setValidators(newValidators);
        assertEquals(newValidators, surveyQuestion.getValidators());
    }

    @Test
    public void choose() throws Exception {
        surveyQuestion.choose(-1);
        assertEquals("", surveyQuestion.getAnswer());
        surveyQuestion.choose(2);
        assertEquals("", surveyQuestion.getAnswer());
        surveyQuestion.choose(0);
        assertEquals("1", surveyQuestion.getAnswer());
        surveyQuestion.choose(1);
        assertEquals("2", surveyQuestion.getAnswer());
    }

    @Test
    public void choose1() throws Exception {
        surveyQuestion.choose(null);
        assertEquals("", surveyQuestion.getAnswer());
        surveyQuestion.choose(new Choice("a", "3"));
        assertEquals("", surveyQuestion.getAnswer());
        surveyQuestion.choose(choices.get(0));
        assertEquals("1", surveyQuestion.getAnswer());
        surveyQuestion.choose(choices.get(1));
        assertEquals("2", surveyQuestion.getAnswer());
    }

    @Test
    public void equals() throws Exception {
        assertEquals(surveyQuestion, surveyQuestion);

        SurveyQuestion compareTo = new SurveyQuestion();
        assertNotEquals(surveyQuestion,compareTo);

        compareTo.setType(SurveyQuestion.TYPES.RADIO_GROUP);
        assertNotEquals(surveyQuestion,compareTo);
        compareTo.setTaskId("12345");
        assertNotEquals(surveyQuestion,compareTo);
        compareTo.setName("question");
        assertNotEquals(surveyQuestion,compareTo);
        compareTo.setTitle("This is a radio question");
        assertNotEquals(surveyQuestion,compareTo);
        compareTo.setText("This is question text");
        assertNotEquals(surveyQuestion,compareTo);
        compareTo.setRichText("<b>This is rich text</b>");
        assertNotEquals(surveyQuestion,compareTo);
        compareTo.setChoices(choices);
        assertNotEquals(surveyQuestion,compareTo);
        compareTo.setAnswer("1");
        assertNotEquals(surveyQuestion,compareTo);
        compareTo.setValidators(validators);

        assertEquals(surveyQuestion,compareTo);

        compareTo.setType(null);
        assertNotEquals(surveyQuestion, compareTo);
        surveyQuestion.setType(null);
        assertEquals(surveyQuestion,compareTo);

        compareTo.setTaskId(null);
        assertNotEquals(surveyQuestion,compareTo);
        surveyQuestion.setTaskId(null);
        assertEquals(surveyQuestion, compareTo);

        compareTo.setName(null);
        assertNotEquals(surveyQuestion,compareTo);
        surveyQuestion.setName(null);
        assertEquals(surveyQuestion,compareTo);

        compareTo.setTitle(null);
        assertNotEquals(surveyQuestion,compareTo);
        surveyQuestion.setTitle(null);
        assertEquals(surveyQuestion,compareTo);

        compareTo.setText(null);
        assertNotEquals(surveyQuestion,compareTo);
        surveyQuestion.setText(null);
        assertEquals(surveyQuestion,compareTo);


        compareTo.setRichText(null);
        assertNotEquals(surveyQuestion,compareTo);
        surveyQuestion.setRichText(null);
        assertEquals(surveyQuestion,compareTo);

        compareTo.setChoices(null);
        assertNotEquals(surveyQuestion,compareTo);
        surveyQuestion.setChoices(null);
        assertEquals(surveyQuestion,compareTo);

        compareTo.setAnswer(null);
        assertNotEquals(surveyQuestion,compareTo);
        surveyQuestion.setAnswer(null);
        assertEquals(surveyQuestion,compareTo);


        compareTo.setValidators(null);
        assertNotEquals(surveyQuestion,compareTo);
        surveyQuestion.setValidators(null);
        assertEquals(surveyQuestion,compareTo);
    }

    @Test
    public void fromJSON() throws Exception {
        SurveyQuestion compareTo = SurveyQuestion.fromJSON(surveyQuestion.toJSON());
        Log.d("JSON: ", surveyQuestion.toJSON());
        assertEquals(surveyQuestion, compareTo);
    }

    @Test
    public void toJSON() throws Exception {
        SurveyQuestion compareTo = SurveyQuestion.fromJSON(surveyQuestion.toJSON());
        assertEquals(surveyQuestion, compareTo);
    }
}