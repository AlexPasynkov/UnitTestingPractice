package com.alexlearn.unittestingpractice.repository;

import androidx.lifecycle.MutableLiveData;

import com.alexlearn.unittestingpractice.models.Note;
import com.alexlearn.unittestingpractice.persistence.NoteDao;
import com.alexlearn.unittestingpractice.ui.Resource;
import com.alexlearn.unittestingpractice.util.InstantExecutorExtension;
import com.alexlearn.unittestingpractice2.util.LiveDataTestUtil;
import com.alexlearn.unittestingpractice2.util.TestUtil;
import org.junit.Assert;

import static com.alexlearn.unittestingpractice.repository.NoteRepository.DELETE_FAILURE;
import static com.alexlearn.unittestingpractice.repository.NoteRepository.DELETE_SUCCESS;
import static com.alexlearn.unittestingpractice.repository.NoteRepository.INSERT_FAILURE;
import static com.alexlearn.unittestingpractice.repository.NoteRepository.INSERT_SUCCESS;
import static com.alexlearn.unittestingpractice.repository.NoteRepository.INVALID_NOTE_ID;
import static com.alexlearn.unittestingpractice.repository.NoteRepository.NOTE_TITLE_NULL;
import static com.alexlearn.unittestingpractice.repository.NoteRepository.UPDATE_FAILURE;
import static com.alexlearn.unittestingpractice.repository.NoteRepository.UPDATE_SUCCESS;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

import static org.mockito.Mockito.*;
@ExtendWith(InstantExecutorExtension.class)
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

    /*
        update note
        verify their correct method is called
        confirm the observer is triggered
        confirm the number of rows updated
     */

    @Test
    void updateNote_returnNumRowsUpdated() throws Exception {

        //Arrange
        final int updatedRow = 1;
        when(noteDao.updateNote(any(Note.class))).thenReturn(Single.just(updatedRow));

        //Act
        final Resource<Integer> returnedValue = noteRepository.updateNote(NOTE_1).blockingFirst();

        //Assert
        verify(noteDao).updateNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);

        assertEquals(Resource.success(updatedRow, UPDATE_SUCCESS), returnedValue);
    }

    /*
        update note
        failure (-1)
     */

    @Test
    void updateNote_returnFailure() throws Exception {
        //Arrange
        final int failedInsert = -1;
        final Single<Integer> returnedData = Single.just(failedInsert);
        when(noteDao.updateNote(any(Note.class))).thenReturn(returnedData);

        //Act
        final Resource<Integer> returnedValue = noteRepository.updateNote(NOTE_1).blockingFirst();

        //Assert
        verify(noteDao).updateNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);

        assertEquals(Resource.error(null, UPDATE_FAILURE), returnedValue);
    }

    /*
        update note
        null title
        throw exception
     */

    @Test
    void updateNote_nullTitle_throwException() throws Exception {
        Exception exception = assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                final Note note = new Note(TestUtil.TEST_NOTE_1);
                note.setTitle(null);
                noteRepository.updateNote(note);
            }
        });

        assertEquals(NOTE_TITLE_NULL, exception.getMessage());
    }

    /*
    delete note
    null id
    throw exception
     */

    @Test
    void deleteNote_nullId_throwException() throws Exception {
       Exception exception = assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                final Note note = new Note(TestUtil.TEST_NOTE_1);
                note.setId(-1);
                noteRepository.deleteNote(note);
            }
        });

        assertEquals(INVALID_NOTE_ID, exception.getMessage());
    }

    /*
    delete note
    delete success
    return Resource.success with deleted row
     */

    @Test
    void deleteNote_deleteSuccess_returnResourceSuccess() throws Exception {
        //Arrange
        final int deletedRow = 1;
        Resource<Integer> successResponse = Resource.success(deletedRow, DELETE_SUCCESS);
        LiveDataTestUtil <Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        when(noteDao.deleteNote(any(Note.class))).thenReturn(Single.just(deletedRow));
        //Act
        Resource<Integer> observedResponse = liveDataTestUtil.getValue(noteRepository.deleteNote(NOTE_1));
        //Assert
        assertEquals(successResponse, observedResponse);
    }

    /*
    delete note
    delete failure
    return Resource.error
     */
    @Test
    void deleteNote_deleteSuccess_returnResourceError() throws Exception {
        //Arrange
        final int deletedRow = -1;
        Resource<Integer> errorResponse = Resource.error(null, DELETE_FAILURE);
        LiveDataTestUtil <Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        when(noteDao.deleteNote(any(Note.class))).thenReturn(Single.just(deletedRow));
        //Act
        Resource<Integer> observedResponse = liveDataTestUtil.getValue(noteRepository.deleteNote(NOTE_1));
        //Assert
        assertEquals(errorResponse, observedResponse);
    }

    /*
    retrieve notes
    return list of notes
     */

    @Test
    void getNotes_returnListWithNotes() throws Exception {
        //Arrange
        List<Note> notes = TestUtil.TEST_NOTES_LIST;
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<List<Note>> returnedData = new MutableLiveData<>();
        returnedData.setValue(notes);
        when(noteDao.getNotes()).thenReturn(returnedData);

        //Act
        List<Note> observedData = liveDataTestUtil.getValue(noteRepository.getNotes());

        //Assert
        assertEquals(notes, observedData);
    }

    /*
        retrieve notes
        return empty list
     */

    @Test
    void getNotes_returnEmptyList() throws Exception {
        //Arrange
        List<Note> notes = new ArrayList<>();
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<List<Note>> returnedData = new MutableLiveData<>();
        returnedData.setValue(notes);
        when(noteDao.getNotes()).thenReturn(returnedData);

        //Act
        List<Note> observedData = liveDataTestUtil.getValue(noteRepository.getNotes());

        //Assert
        assertEquals(notes, observedData);
    }
}
