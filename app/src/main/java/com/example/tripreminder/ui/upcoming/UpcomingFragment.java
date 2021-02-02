package com.example.tripreminder.ui.upcoming;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripreminder.NewTripActivity;
import com.example.tripreminder.R;
import com.example.tripreminder.database.TripData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class UpcomingFragment extends Fragment {


    RecyclerView recyclerView;
    RecyclerViAdapter adapter;
    FloatingActionButton fab;

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
        adapter = new RecyclerViAdapter(getContext(), new ArrayList<>());

        UpcomingViewModel model = ViewModelProviders.of(this).get(UpcomingViewModel.class);
        recyclerView.setLayoutManager(linearLayoutManager);
        model.getUpcoming().observe(getViewLifecycleOwner(), new Observer<List<TripData>>() {
            @Override
            public void onChanged(List<TripData> tripData) {
                adapter.setValues(tripData);
                adapter.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(adapter);
        return root;
    }


}