package com.example.tripreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.Arrays;
import java.util.List;

public class AddNote extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<String> model;
    Button btnAddNotes ,btnAddNewNote;
    EditText addNewNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        recyclerView =(RecyclerView)findViewById(R.id.recyclerViewNote);
        recyclerView.setHasFixedSize(true);
        btnAddNotes=findViewById(R.id.btnAddNotes);
        btnAddNewNote=findViewById(R.id.btnAddNote);
        addNewNote=findViewById(R.id.txtNewNote);

        layoutManager=new LinearLayoutManager(this);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        model = Arrays.asList("cakeOne","cakeOne",
                "cakeThreccccccccccmmm cccccccccccccmmm mmmmmmmmmm ccccc ccccccccccc ccccccc ccccee",
                "cakeOne","cakeOne","cakeOne");


        adapter =new NoteAdapter(this,model);
        recyclerView.setAdapter(adapter);

    }
}