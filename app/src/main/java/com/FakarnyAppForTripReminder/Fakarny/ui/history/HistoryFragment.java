package com.FakarnyAppForTripReminder.Fakarny.ui.history;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.FakarnyAppForTripReminder.Fakarny.HistoryMaps;
import com.FakarnyAppForTripReminder.Fakarny.R;
import com.FakarnyAppForTripReminder.Fakarny.database.TripData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private HistoryViewModel model;
    FloatingActionButton btnMap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView = root.findViewById(R.id.recycle_view_history);
        btnMap=root.findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent outIntent = new Intent(getContext(), HistoryMaps.class);
                startActivity(outIntent);
            }
        });
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new HistoryAdapter(getContext(), new ArrayList<>());
        model = ViewModelProviders.of(this).get(HistoryViewModel.class);
        model.getData().observe(getViewLifecycleOwner(), new Observer<List<TripData>>() {
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
            public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                 int direction) {
                new AlertDialog.Builder(getContext())
                        .setTitle(getResources().getString(R.string.delete))
                        .setMessage(getResources().getString(R.string.delete_ms))
                        .setPositiveButton(getResources().getString(R.string.cancel_btn_alertDialog), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.yes_i_am), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                model.remove(adapter.getValues().get(viewHolder.getAdapterPosition()));
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