package com.alexlearn.unittestingpractice;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;


import com.alexlearn.unittestingpractice.persistence.NoteDao;
import com.alexlearn.unittestingpractice.persistence.NoteDatabase;

import org.junit.After;
import org.junit.Before;

//jUnit4 is chosen, because jUnit5 does not support API lower 26

public abstract class NoteDatabaseTest {

    //System under test
    private NoteDatabase noteDatabase;


    public NoteDao getNoteDao() {
        return noteDatabase.getNoteDao();
    }

    //Temporary database for testing. We need Application provider to get Application context
    @Before
    public void init() {
        noteDatabase = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                NoteDatabase.class
        ).build();
    }

    @After
    public void finish() {
        noteDatabase.close();
    }
}
