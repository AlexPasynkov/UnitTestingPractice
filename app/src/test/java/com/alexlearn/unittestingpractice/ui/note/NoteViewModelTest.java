package com.alexlearn.unittestingpractice.ui.note;

import com.alexlearn.unittestingpractice.models.Note;
import com.alexlearn.unittestingpractice.repository.NoteRepository;
import com.alexlearn.unittestingpractice.ui.Resource;
import com.alexlearn.unittestingpractice.util.InstantExecutorExtension;
import com.alexlearn.unittestingpractice2.util.LiveDataTestUtil;
import com.alexlearn.unittestingpractice2.util.TestUtil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Not;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.internal.operators.single.SingleToFlowable;

import static com.alexlearn.unittestingpractice.repository.NoteRepository.INSERT_SUCCESS;
import static com.alexlearn.unittestingpractice.repository.NoteRepository.UPDATE_SUCCESS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


//This class helps me to deal with error  Method getMainLooper in android.os.Looper not mocked, caused by Junit problems with background threads
@ExtendWith(InstantExecutorExtension.class)
public class NoteViewModelTest {

    //system under test
    private NoteViewModel noteViewModel;

    //Setup the test
    @Mock
    private NoteRepository noteRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        noteViewModel = new NoteViewModel(noteRepository);
    }

    /*
    can`t observe a note that hasn`t been set
     */

    @Test
    void observeEmptyNote_whenSet() throws Exception {
        //Arrange
        LiveDataTestUtil<Note> liveDataTestUtil = new LiveDataTestUtil<>();

        //Act

        Note note = liveDataTestUtil.getValue(noteViewModel.observeNote());

        //Assert
        assertNull(note);
    }

    /*
    Observe a note has been set and onChanged will trigger in activity
     */

    @Test
    void observeNote_whenSet() throws Exception {
        //Arrange
        Note note = new Note(TestUtil.TEST_NOTE_1);
        LiveDataTestUtil<Note> liveDataTestUtil = new LiveDataTestUtil<>();

        //Act
        noteViewModel.setNote(note);
        Note observedNote = liveDataTestUtil.getValue(noteViewModel.observeNote());

        //Assert
        assertEquals(note, observedNote);
    }
    /*
    insert a new note and observe a row returned
     */

    @Test
    void insertNote_returnRow() throws Exception {
        //Arrange
        Note note = new Note(TestUtil.TEST_NOTE_1);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        final int insertedRow = 1;
        Flowable<Resource<Integer>> returnedData = SingleToFlowable.just(Resource.success(insertedRow, INSERT_SUCCESS));
        when(noteRepository.insertNote(any(Note.class))).thenReturn(returnedData);
        //Act

        noteViewModel.setNote(note);
        Resource<Integer> returnedValue = liveDataTestUtil.getValue(noteViewModel.insertNote());

        //Assert
        assertEquals(Resource.success(insertedRow, INSERT_SUCCESS), returnedValue);
    }

    /*
    insert: don`t return a new row without observer
     */

    @Test
    void dontReturnNoteWithoutObserver() throws Exception {
        //Arrange
        Note note = new Note(TestUtil.TEST_NOTE_1);

        //Act
        noteViewModel.setNote(note);

        //Assert
        verify(noteRepository, never()).insertNote(any(Note.class));
    }

    /*
    set note, null title, throw exception
     */

    @Test
    void setNote_nullTitle_throwException() throws Exception {
        //Arrange
        final Note note = new Note(TestUtil.TEST_NOTE_1);
        note.setTitle(null);

        //Act
        assertThrows(Exception.class, new Executable() {
            @Override
            //Assert
            public void execute() throws Throwable {
                noteViewModel.setNote(note);
            }
        });
    }

    /*
    insert a update note and observe a row returned
     */

    @Test
    void updateNote_returnRow() throws Exception {
        //Arrange
        Note note = new Note(TestUtil.TEST_NOTE_1);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        final int updatedRow = 1;
        Flowable<Resource<Integer>> returnedData = SingleToFlowable.just(Resource.success(updatedRow, UPDATE_SUCCESS));
        when(noteRepository.updateNote(any(Note.class))).thenReturn(returnedData);
        //Act

        noteViewModel.setNote(note);
        Resource<Integer> returnedValue = liveDataTestUtil.getValue(noteViewModel.updateNote());

        //Assert
        assertEquals(Resource.success(updatedRow, UPDATE_SUCCESS), returnedValue);
    }

    /*
        update: don`t return a new row without observer
     */

    @Test
    void dontReturnUpdateRowNumberWithoutObserver() throws Exception {
        //Arrange
        Note note = new Note(TestUtil.TEST_NOTE_1);

        //Act
        noteViewModel.setNote(note);

        //Assert
        verify(noteRepository, never()).updateNote(any(Note.class));
    }
}
