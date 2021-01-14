package com.alexlearn.unittestingpractice.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.alexlearn.unittestingpractice.ui.note.NoteViewModel;
import com.alexlearn.unittestingpractice.ui.noteslist.NotesListViewModel;
import com.alexlearn.unittestingpractice.viewmodels.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelProviderFactory);

    @Binds
    @IntoMap
    @ViewModelKey(NoteViewModel.class)
    public abstract ViewModel bindNoteViewModel(NoteViewModel noteViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(NotesListViewModel.class)
    public abstract ViewModel bindNotesListViewModel(NotesListViewModel notesListViewModel);
}
