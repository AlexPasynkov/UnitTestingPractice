package com.alexlearn.unittestingpractice.ui.noteslist;


import androidx.lifecycle.MutableLiveData;

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
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static com.alexlearn.unittestingpractice.repository.NoteRepository.DELETE_FAILURE;
import static com.alexlearn.unittestingpractice.repository.NoteRepository.DELETE_SUCCESS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//Because we work with rxJava
@ExtendWith(InstantExecutorExtension.class)
public class NotesListViewModelTest {

    //system under test
    private NotesListViewModel viewModel;

    @Mock
    private NoteRepository noteRepository;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        viewModel = new NotesListViewModel(noteRepository);
    }

    /*
        Retrieve list of notes
        observe the list
        return the list
     */

    @Test
    void retrieveNotes_returnNotesList() throws Exception {
        //Arrange
        List<Note> returnedData = TestUtil.TEST_NOTES_LIST;
        LiveDataTestUtil <List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        //When we save live data in the view model we need MutableLiveData
        MutableLiveData<List<Note>> returnedValue = new MutableLiveData<>();
        returnedValue.setValue(returnedData);
        when(noteRepository.getNotes()).thenReturn(returnedValue);

        //Act
        viewModel.getNotes();
        List<Note> observedData = liveDataTestUtil.getValue(viewModel.observeNotes());
        //Assert
        assertEquals(returnedData, observedData);
    }

    /*
        retrieve list of notes
        observe the list
        return empty list
     */

    @Test
    void retrieveNotes_returnEmptyNotesList() throws Exception {
        //Arrange
        List<Note> returnedData = new ArrayList<>();
        LiveDataTestUtil <List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        //When we save live data in the view model we need MutableLiveData
        MutableLiveData<List<Note>> returnedValue = new MutableLiveData<>();
        returnedValue.setValue(returnedData);
        when(noteRepository.getNotes()).thenReturn(returnedValue);

        //Act
        viewModel.getNotes();
        List<Note> observedData = liveDataTestUtil.getValue(viewModel.observeNotes());
        //Assert
        assertEquals(returnedData, observedData);
    }

    /*
        delete note
        observe Resource.success
        return Resource.success
     */

    @Test
    void deleteNote_observeResourceSuccess() throws Exception {
        //Arrange
        Note deletedNote = new Note (TestUtil.TEST_NOTE_1);
        Resource<Integer> returnedData = Resource.success(1, DELETE_SUCCESS);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<Resource<Integer>> returnedValue = new MutableLiveData<>();
        returnedValue.setValue(returnedData);
        when(noteRepository.deleteNote(any(Note.class))).thenReturn(returnedValue);

        //Act
        Resource<Integer> observedValueLiveData = liveDataTestUtil.getValue(viewModel.deleteNote(deletedNote));

        //Assert
        assertEquals(returnedData, observedValueLiveData);
    }

    /*
        delete note
        observe Resource.error
        return Resource.error
     */

    @Test
    void deleteNote_observeResourceError() throws Exception {
        //Arrange
        Note deletedNote = new Note (TestUtil.TEST_NOTE_1);
        Resource<Integer> returnedData = Resource.success(null, DELETE_FAILURE);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<Resource<Integer>> returnedValue = new MutableLiveData<>();
        returnedValue.setValue(returnedData);
        when(noteRepository.deleteNote(any(Note.class))).thenReturn(returnedValue);

        //Act
        Resource<Integer> observedValueLiveData = liveDataTestUtil.getValue(viewModel.deleteNote(deletedNote));

        //Assert
        assertEquals(returnedData, observedValueLiveData);
    }
}
