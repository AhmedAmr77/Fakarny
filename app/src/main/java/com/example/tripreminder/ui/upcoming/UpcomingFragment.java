package com.example.tripreminder.ui.upcoming;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripreminder.NewTripActivity;
import com.example.tripreminder.R;
import com.example.tripreminder.RecyclerViAdapter;
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
        tripDataList = Arrays.asList(new TripData("1", "TripOne", "Meno", "Cai", "12-6-2021", "3:45 PM", "No", "Round"),
                new TripData("2", "TripTwo", "Lux", "Asw", "16-6-2021", "11:45 PM", "Yes", "OneWay"),
                new TripData("3", "TripThree", "Cai", "Alex", "30-6-2021", "2:45 PM", "Yes", "Round"),
                new TripData("4", "TripFour", "Alex", "Asw", "25-6-2021", "1:45 PM", "No", "OneWay"),
                new TripData("5", "TripFive", "Asw", "Meno", "4-6-2021", "5:45 PM", "No", "Round"),
                new TripData("1", "TripOne", "Meno", "Cai", "12-6-2021", "3:45 PM", "No", "Round"));
        adapter = new RecyclerViAdapter(this.getContext(), tripDataList);
        recyclerView.setAdapter(adapter);


        return root;
    }
}