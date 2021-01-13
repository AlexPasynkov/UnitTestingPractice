package com.alexlearn.unittestingpractice.ui.noteslist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.alexlearn.unittestingpractice.R;
import com.alexlearn.unittestingpractice.di.DaggerAppComponent;
import com.alexlearn.unittestingpractice.repository.NoteRepository;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class NotesListActivity extends DaggerAppCompatActivity {

    private static final String TAG = "NotesListActivity";

    @Inject
    NoteRepository noteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        Log.d(TAG, "onCreate: " + noteRepository);
    }


}