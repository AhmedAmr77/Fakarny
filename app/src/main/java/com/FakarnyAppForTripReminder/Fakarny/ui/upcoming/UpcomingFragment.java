package com.FakarnyAppForTripReminder.Fakarny.ui.upcoming;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.FakarnyAppForTripReminder.Fakarny.NewTripActivity;
import com.FakarnyAppForTripReminder.Fakarny.R;
import com.FakarnyAppForTripReminder.Fakarny.database.TripData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class UpcomingFragment extends Fragment {


    private RecyclerView recyclerView;
    private RecyclerViAdapter adapter;
    private FloatingActionButton fab;
    private UpcomingViewModel model;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_upcoming, container, false);

        fab = root.findViewById(R.id.btnNewTrip);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent outIntent = new Intent(getContext(), NewTripActivity.class);
                startActivity(outIntent);
            }
        });
        recyclerView = root.findViewById(R.id.recyclerViID);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        adapter = new RecyclerViAdapter(getContext(), new ArrayList<>(),getActivity().getApplication());

        model = ViewModelProviders.of(this).get(UpcomingViewModel.class);
        recyclerView.setLayoutManager(linearLayoutManager);
        model.getUpcoming().observe(getViewLifecycleOwner(), new Observer<List<TripData>>() {
            @Override
            public void onChanged(List<TripData> tripData) {
                adapter.setValues(tripData);
                adapter.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(adapter);
        swipedDelete();
        return root;
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
                    new AlertDialog.Builder(getContext())
                            .setTitle("Edit")
                            .setMessage("you want to Edit this Trip ?")
                            .setPositiveButton("no", null)
                            .setNegativeButton("yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    TripData tripData = adapter.getTripDataList().get(viewHolder.getAdapterPosition());
                                    Intent intent = new Intent(getContext(), NewTripActivity.class);
                                    intent.putExtra("updateObj", tripData);
                                    startActivity(intent);
                                }
                            })
                            .show();

                }
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                 int direction) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete this Trip ?")
                        .setPositiveButton("cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("yes I am sure", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                model.remove(adapter.getTripDataList().get(viewHolder.getAdapterPosition()));
                                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                            }
                        })
                        .setIcon(R.drawable.ic_baseline_report_problem_24)
                        .show();
            }
        });

        helper.attachToRecyclerView(recyclerView);
    }


}