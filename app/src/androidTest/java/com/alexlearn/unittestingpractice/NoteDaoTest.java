package com.alexlearn.unittestingpractice;

import android.database.sqlite.SQLiteConstraintException;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.alexlearn.unittestingpractice.models.Note;
import com.alexlearn.unittestingpractice2.util.LiveDataTestUtil;
import com.alexlearn.unittestingpractice2.util.TestUtil;

import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class NoteDaoTest extends NoteDatabaseTest {

    public static final String TEST_TITLE = "This is a test title";
    public static final String TEST_CONTENT = "This is a test content";
    public static final String TEST_TIMESTAMP = "05-2019";

    // This rule allows me to make testing on background thread. Otherwise all of them will fail
    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();
    /*
        Insert, Read, Delete
     */

    @Test
    public void insertReadDelete() throws Exception {
        Note note = new Note(TestUtil.TEST_NOTE_1);

        //Insert
        getNoteDao().insertNote(note).blockingGet(); // wait until inserted

        //Read
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        List<Note> insertedNotes = liveDataTestUtil.getValue(getNoteDao().getNotes());

        assertNotNull(insertedNotes);
        assertEquals(note.getContent(), insertedNotes.get(0).getContent());
        assertEquals(note.getTimestamp(), insertedNotes.get(0).getTimestamp());
        assertEquals(note.getTitle(), insertedNotes.get(0).getTitle());

        note.setId(insertedNotes.get(0).getId());
        assertEquals(note, insertedNotes.get(0));

        //Delete
        getNoteDao().deleteNote(note).blockingGet();
        insertedNotes = liveDataTestUtil.getValue(getNoteDao().getNotes());
        assertEquals(0, insertedNotes.size());

    }
    /*
        Insert, Read, Update, Read, Delete
     */
    @Test
    public void insertReadUpdateReadDelete() throws Exception {
        Note note = new Note(TestUtil.TEST_NOTE_1);

        //Insert
        getNoteDao().insertNote(note).blockingGet(); // wait until inserted

        //Read
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        List<Note> insertedNotes = liveDataTestUtil.getValue(getNoteDao().getNotes());

        assertNotNull(insertedNotes);
        assertEquals(note.getContent(), insertedNotes.get(0).getContent());
        assertEquals(note.getTimestamp(), insertedNotes.get(0).getTimestamp());
        assertEquals(note.getTitle(), insertedNotes.get(0).getTitle());



        note.setId(insertedNotes.get(0).getId());
        assertEquals(note, insertedNotes.get(0));

        //Update
        note.setTitle(TEST_TITLE);
        note.setContent(TEST_CONTENT);
        note.setTimestamp(TEST_TIMESTAMP);
        getNoteDao().updateNote(note).blockingGet();


        //Read
        insertedNotes = liveDataTestUtil.getValue(getNoteDao().getNotes());
        assertEquals(TEST_TITLE, insertedNotes.get(0).getTitle());
        assertEquals(TEST_CONTENT, insertedNotes.get(0).getContent());
        assertEquals(TEST_TIMESTAMP, insertedNotes.get(0).getTimestamp());

        note.setId(insertedNotes.get(0).getId());
        assertEquals(note, insertedNotes.get(0));
        //Delete
        getNoteDao().deleteNote(note).blockingGet();

        //Confirm the database is empty
        insertedNotes = liveDataTestUtil.getValue(getNoteDao().getNotes());
        assertEquals(0, insertedNotes.size());
    }

    /*
        Insert note with null title, throw Exception
     */
    @Test(expected = SQLiteConstraintException.class)
    public void insert_nullTitle_throwSQLiteConstraintException() throws Exception {
        final Note note = new Note(TestUtil.TEST_NOTE_1);
        note.setTitle(null);

        //Insert
        getNoteDao().insertNote(note).blockingGet();
    }

    /*
        Insert, Update with null title, throw Exception
     */

    @Test(expected = SQLiteConstraintException.class)
    public void update_nullTitle_throwSQLiteConstraintException() throws Exception {

        Note note = new Note(TestUtil.TEST_NOTE_1);

        //Insert
        getNoteDao().insertNote(note).blockingGet();

        //read
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        List<Note> insertedNotes = liveDataTestUtil.getValue(getNoteDao().getNotes());
        assertNotNull(insertedNotes);

        //Update
        note = new Note(insertedNotes.get(0));
        note.setTitle(null);
        getNoteDao().updateNote(note).blockingGet();
    }
}
