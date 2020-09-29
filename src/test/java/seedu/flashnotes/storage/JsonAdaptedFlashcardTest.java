package seedu.flashnotes.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.flashnotes.storage.JsonAdaptedFlashcard.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.flashnotes.testutil.Assert.assertThrows;
import static seedu.flashnotes.testutil.TypicalFlashcards.WHO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.flashnotes.commons.exceptions.IllegalValueException;
import seedu.flashnotes.model.flashcard.Answer;
import seedu.flashnotes.model.flashcard.Question;

public class JsonAdaptedFlashcardTest {
    private static final String INVALID_QUESTION = "wh@";
    private static final String INVALID_ANSWER = "a";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_QUESTION = WHO.getQuestion().toString();
    private static final String VALID_ANSWER = WHO.getAnswer().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = WHO.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validFlashcardDetails_returnsFlashcard() throws Exception {
        JsonAdaptedFlashcard flashcard = new JsonAdaptedFlashcard(WHO);
        assertEquals(WHO, flashcard.toModelType());
    }

    @Test
    public void toModelType_invalidQuestion_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard =
                new JsonAdaptedFlashcard(INVALID_QUESTION, VALID_ANSWER, VALID_TAGS);
        String expectedMessage = Question.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, flashcard::toModelType);
    }

    @Test
    public void toModelType_nullQuestion_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard = new JsonAdaptedFlashcard(null, VALID_ANSWER, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Question.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, flashcard::toModelType);
    }

    @Test
    public void toModelType_invalidAnswer_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard =
                new JsonAdaptedFlashcard(VALID_QUESTION, INVALID_ANSWER, VALID_TAGS);
        String expectedMessage = Answer.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, flashcard::toModelType);
    }

    @Test
    public void toModelType_nullAnswer_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard = new JsonAdaptedFlashcard(VALID_QUESTION, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Answer.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, flashcard::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedFlashcard flashcard =
                new JsonAdaptedFlashcard(VALID_QUESTION, VALID_ANSWER, invalidTags);
        assertThrows(IllegalValueException.class, flashcard::toModelType);
    }

}