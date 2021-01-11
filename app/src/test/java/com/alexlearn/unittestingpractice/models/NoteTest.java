package com.alexlearn.unittestingpractice.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NoteTest {
    public static final String TIMESTAMP_1 = "05-2019";
    public static final String TIMESTAMP_2 = "04-2019";

    /*
        Compare two equal Notes
    */
    @Test
    void isNotesEqual_identicalProperties_returnTrue() throws Exception {
        //Arrange
        Note note1 = new Note("Note #1", "This is note #1", TIMESTAMP_1);
        note1.setId(1);
        Note note2 = new Note("Note #1", "This is note #1", TIMESTAMP_1);
        note2.setId(1);

        //Act

        //Assert
        assertEquals(note1, note2);
        System.out.println("The notes are equal");
    }

    /*
        Compare notes with two different ids
     */
    @Test
    void isNotesEqual_differentIds_returnFalse() throws Exception {
        //Arrange
        Note note1 = new Note("Note #1", "This is note #1", TIMESTAMP_1);
        note1.setId(1);
        Note note2 = new Note("Note #1", "This is note #1", TIMESTAMP_1);
        note2.setId(2);

        //Act

        //Assert
        assertNotEquals(note1, note2);
        System.out.println("The notes are not equal");
    }

    /*
       Compare two notes with different timestamps
    */
    @Test
    void isNotesAreEqual_differentTimeStamps_returnTrue() throws Exception {
        //Arrange
        Note note1 = new Note("Note #1", "This is note #1", TIMESTAMP_1);
        note1.setId(1);
        Note note2 = new Note("Note #1", "This is note #1", TIMESTAMP_2);
        note2.setId(1);

        //Act

        //Assert
        assertEquals(note1, note2);
        System.out.println("The notes are equal");
    }

    /*
        Compare two notes with two different titles
    */
    @Test
    void isNotesAreEqual_differentTitles_returnFalse() throws Exception {
        //Arrange
        Note note1 = new Note("Note #1", "This is note #1", TIMESTAMP_1);
        note1.setId(1);
        Note note2 = new Note("Note #2", "This is note #1", TIMESTAMP_2);
        note2.setId(1);

        //Act

        //Assert
        assertNotEquals(note1, note2);
        System.out.println("The notes are not equal, they have different titles");
    }

    /*
        Compare two notes with different content
    */
    @Test
    void isNotesAreEqual_differentContent_returnFalse() throws Exception {
        //Arrange
        Note note1 = new Note("Note #1", "This is note #1", TIMESTAMP_1);
        note1.setId(1);
        Note note2 = new Note("Note #1", "This is note #2", TIMESTAMP_2);
        note2.setId(1);

        //Act

        //Assert
        assertNotEquals(note1, note2);
        System.out.println("The notes are not equal, they have different titles");
    }

}
