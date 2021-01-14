package com.alexlearn.unittestingpractice.di;

import com.alexlearn.unittestingpractice.ui.note.NoteActivity;
import com.alexlearn.unittestingpractice.ui.noteslist.NotesListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract NotesListActivity contributeNotesListActivity();

    @ContributesAndroidInjector
    abstract NoteActivity contributeNotesActivity();
}
