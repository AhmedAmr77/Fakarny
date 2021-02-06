package com.example.tripreminder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tripreminder.database.TripData;


public class AddNote extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button btnAddNotes, btnAddNewNote;
    private EditText addNewNote;
    private AddNoteViewModle model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        model = new ViewModelProvider(this).get(AddNoteViewModle.class);

        TripData trip=(TripData) getIntent().getSerializableExtra("TripData");
        if(trip!=null){
            model.intiData(trip);
        }
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewNote);
        recyclerView.setHasFixedSize(true);
        btnAddNotes = findViewById(R.id.btnAddNotes);
        btnAddNewNote = findViewById(R.id.btnAddNote);
        addNewNote = findViewById(R.id.txtNewNote);

        layoutManager = new LinearLayoutManager(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NoteAdapter(this, model.getNotes());
        recyclerView.setAdapter(adapter);
        btnAddNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oneNote = addNewNote.getText().toString();
                if (!oneNote.trim().isEmpty()) {

                    model.addNote(oneNote);
                    adapter.setValues(model.getNotes());
                    adapter.notifyDataSetChanged();
                    addNewNote.setText("");
                }
            }
        });
        btnAddNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.save();
                finish();
            }
        });
        swipedDelete();
    }

    private void swipedDelete() {
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper
                .SimpleCallback(ItemTouchHelper.LEFT, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (ItemTouchHelper.ACTION_STATE_DRAG == actionState && viewHolder != null) {
                    String s = model.getNote(viewHolder.getAdapterPosition());
                    model.remove(viewHolder.getAdapterPosition());
                    adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    addNewNote.setText(s);
                }
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                 int direction) {
                new AlertDialog.Builder(AddNote.this)
                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete this note ?")
                        .setPositiveButton("cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("yes I am sure", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                model.remove(viewHolder.getAdapterPosition());
                                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        helper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("note",addNewNote.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        addNewNote.setText(savedInstanceState.getString("note"));
    }
}