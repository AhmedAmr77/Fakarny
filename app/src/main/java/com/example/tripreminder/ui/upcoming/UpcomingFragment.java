package com.example.tripreminder.ui.upcoming;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripreminder.NewTripActivity;
import com.example.tripreminder.R;
import com.example.tripreminder.TripData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.List;

public class UpcomingFragment extends Fragment {

    private UpcomingViewModel upcomingViewModel;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<TripData> tripDataList;
    FloatingActionButton fab;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        upcomingViewModel =
                new ViewModelProvider(this).get(UpcomingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_upcoming, container, false);

        fab = root.findViewById(R.id.btnNewTrip);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent outIntent =new Intent(getContext(), NewTripActivity.class);
                startActivity(outIntent);
            }
        });
        recyclerView = root.findViewById(R.id.recyclerViID);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
                tripDataList = Arrays.asList(new TripData ("1",  "state",  "TripOne",  "Meno",  "Cai",  "12-6-2021",  "3:45 PM",  "No",  "Round", "lat_long_startPoint", "30.123986, 31.242308"),
                                new TripData("1",  "state",  "TripOne",  "Meno",  "Cai",  "12-6-2021",  "3:45 PM",  "No",  "OneWay", "29.958980, 30.893728", "29.958980, 30.893728"),
                                new TripData("2",  "state",  "TripOne",  "Meno",  "Cai",  "12-6-2021",  "3:45 PM",  "No",  "Round", "lat_long_startPoint", " 31.202326, 29.902622"),
                                new TripData("3",  "state",  "TripOne",  "Meno",  "Cai",  "12-6-2021",  "3:45 PM",  "No",  "Round", "lat_long_startPoint", " 30.571658, 31.010919"),
                                new TripData("4",  "state",  "TripOne",  "Meno",  "Cai",  "12-6-2021",  "3:45 PM",  "No",  "Round", "lat_long_startPoint", " 30.458855, 31.185531"),
                                new TripData("5",  "state",  "TripOne",  "Meno",  "Cai",  "12-6-2021",  "3:45 PM",  "No",  "Round", "lat_long_startPoint", " 31.193773, 32.262458"));
        adapter = new RecyclerViAdapter(this.getContext(), tripDataList);
        recyclerView.setAdapter(adapter);


        return root;
    }
}