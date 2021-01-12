package com.alexlearn.unittestingpractice2.util;

import com.alexlearn.unittestingpractice.models.Note;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TestUtil {

    public static final String TIMESTAMP_1 = "05-2019";
    public static final Note TEST_NOTE_1 = new Note("Take out trash", "It`s new day tomorrow", TIMESTAMP_1);

    public static final String TIMESTAMP_2 = "05-2019";
    public static final Note TEST_NOTE_2 = new Note("Buy a car", "I really need it", TIMESTAMP_2);

    public static final List<Note> TEST_NOTES_LIST = Collections.unmodifiableList(
            new ArrayList<Note>() {{

                add(new Note(1, "Take out trash", "It`s new day tomorrow", TIMESTAMP_1));
                add(new Note(2, "Buy a car", "I really need it", TIMESTAMP_2));

            }}
    );
}
