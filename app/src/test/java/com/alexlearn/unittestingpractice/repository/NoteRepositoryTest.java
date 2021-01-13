package com.alexlearn.unittestingpractice.repository;

import com.alexlearn.unittestingpractice.models.Note;
import com.alexlearn.unittestingpractice.persistence.NoteDao;
import com.alexlearn.unittestingpractice.ui.Resource;
import com.alexlearn.unittestingpractice2.util.TestUtil;
import org.junit.Assert;

import static com.alexlearn.unittestingpractice.repository.NoteRepository.INSERT_FAILURE;
import static com.alexlearn.unittestingpractice.repository.NoteRepository.INSERT_SUCCESS;
import static com.alexlearn.unittestingpractice.repository.NoteRepository.NOTE_TITLE_NULL;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import io.reactivex.Single;

import static org.mockito.Mockito.*;

public class NoteRepositoryTest {
//Unit test
    //To use @BeforeAll I need to write above the class @TestInstance(TestInstance.Lifecycle.REP_CLASS)

    public static final Note NOTE_1 = new Note(TestUtil.TEST_NOTE_1);

    //system under test
    private NoteRepository noteRepository;

    //option number 1
    //@Mock
//    private NoteDao noteDao;
//
//    @BeforeEach
//    public void initEach(){
//        MockitoAnnotations.initMocks(this);
//    }

    //option number 2
    private NoteDao noteDao;

    @BeforeEach
    public void initEach(){
        noteDao = mock(NoteDao.class);
        noteRepository = new NoteRepository(noteDao);
    }

    /*
    insert note
    verify the correct method is called
    confirm that observer is triggered
    confirm that new row is inserted
     */

    @Test
    void insertNote_returnRow() throws Exception {
        //Arrange

        //What I what the mockDAO to do when I try to insert data in base
        final Long insertedRow = 1L;
        final Single<Long> returnedData = Single.just(insertedRow);
        when(noteDao.insertNote(any(Note.class))).thenReturn(returnedData);

        //Act
        final Resource<Integer> returnedValue = noteRepository.insertNote(NOTE_1).blockingFirst();

        //Assert
        verify(noteDao).insertNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);

        System.out.println("Returned value " + returnedValue.data);

        // 1 is a row
        assertEquals(Resource.success(1, INSERT_SUCCESS) , returnedValue);

        //or test using Rx Java
//        noteRepository.insertNote(NOTE_1)
//                .test()
//                .await()
//                .assertValue(Resource.success(1, INSERT_SUCCESS));
    }

    /*
    Insert note
    Failure (-1)
     */
    @Test
    void insertNote_returnFailure() throws Exception {
        //Arrange

        //What I what the mockDAO to do when I try to insert data in base
        final Long failedInsert = -1L;
        final Single<Long> returnedData = Single.just(failedInsert);
        when(noteDao.insertNote(any(Note.class))).thenReturn(returnedData);

        //Act
        final Resource<Integer> returnedValue = noteRepository.insertNote(NOTE_1).blockingFirst();

        //Assert
        verify(noteDao).insertNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);

        // null is a row
        assertEquals(Resource.error(null, INSERT_FAILURE) , returnedValue);
    }

    /*
    insert note
    null title
    confirm throw exception
     */

    @Test
    void insertNote_nullTitle_throwException() throws Exception {
        Exception exception = assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                final Note note = new Note(TestUtil.TEST_NOTE_1);
                note.setTitle(null);
                noteRepository.insertNote(note);
            }
        });

        assertEquals(NOTE_TITLE_NULL, exception.getMessage());
    }
}
