package com.example.tripreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class ShowNotes extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private AddNoteViewModle model;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notes);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerShow);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        model = new ViewModelProvider(this).get(AddNoteViewModle.class);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NoteAdapter(this, model.getNotes());
        recyclerView.setAdapter(adapter);

    }
}