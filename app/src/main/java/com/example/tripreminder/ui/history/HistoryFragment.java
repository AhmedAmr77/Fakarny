package com.example.tripreminder.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripreminder.R;
import com.example.tripreminder.TripData;

import java.util.Arrays;
import java.util.List;

public class HistoryFragment extends Fragment {

    private HistoryViewModel historyViewModel;

    RecyclerView recyclerView;
    HistoryAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<TripData> tripDataList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        historyViewModel =
                new ViewModelProvider(this).get(HistoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = root.findViewById(R.id.recycle_view_history);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        tripDataList = Arrays.asList(new TripData("1", "TripOne", "Meno", "Cai", "12-6-2021", "3:45 PM", "No", "Round","Done"),
                new TripData("2", "TripTwo", "Lux", "Asw", "16-6-2021", "11:45 PM", "Yes", "OneWay","Cancelled"),
                new TripData("3", "TripThree", "Cai", "Alex", "30-6-2021", "2:45 PM", "Yes", "Round","Done"),
                new TripData("4", "TripFour", "Alex", "Asw", "25-6-2021", "1:45 PM", "No", "OneWay","Done"),
                new TripData("5", "TripFive", "Asw", "Meno", "4-6-2021", "5:45 PM", "No", "Round","Cancelled"),
                new TripData("1", "TripOne", "Meno", "Cai", "12-6-2021", "3:45 PM", "No", "Round","Done"));
        adapter = new HistoryAdapter(this.getContext(), tripDataList);
        recyclerView.setAdapter(adapter);

        return root;
    }
}