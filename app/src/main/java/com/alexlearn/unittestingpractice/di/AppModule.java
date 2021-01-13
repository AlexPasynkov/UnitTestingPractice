package com.alexlearn.unittestingpractice.di;


import android.app.Application;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.alexlearn.unittestingpractice.persistence.NoteDao;
import com.alexlearn.unittestingpractice.persistence.NoteDatabase;
import com.alexlearn.unittestingpractice.repository.NoteRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.alexlearn.unittestingpractice.persistence.NoteDatabase.DATABASE_NAME;

@Module
public class AppModule {

    @Singleton
    @Provides
    static NoteDatabase provideNoteDatabase(Application application){
        return Room.databaseBuilder(
                application,
                NoteDatabase.class,
                DATABASE_NAME
        ).build();
    }

    @Singleton
    @Provides
    static NoteDao providesNoteDao(NoteDatabase noteDatabase){
        return noteDatabase.getNoteDao();
    }

    @Singleton
    @Provides
    static NoteRepository provideNoteRepository(NoteDao noteDao){
        return new NoteRepository(noteDao);
    }
}
