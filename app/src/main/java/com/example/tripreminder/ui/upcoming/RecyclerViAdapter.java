package com.example.tripreminder.ui.upcoming;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Update;


import com.example.tripreminder.ApplicationR;
import com.example.tripreminder.R;
import com.example.tripreminder.database.Repository;
import com.example.tripreminder.database.TripData;

import java.util.List;

public class RecyclerViAdapter extends RecyclerView.Adapter<RecyclerViAdapter.ViewHolder> {

    final Context context;
    List<TripData> tripDataList;

    public RecyclerViAdapter(Context context, List<TripData> tripDataList) {
        this.context = context;
        this.tripDataList = tripDataList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View layout;
        ConstraintLayout row;
        TextView textViewTripName, textViewTripDate, textViewTripTime,
                textViewTripFrom, textViewTripTo, textViewTripWay, textViewRepeat;
        Button btnTripStart, btnTripCancel, btnTripNotes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = itemView;
            row = itemView.findViewById(R.id.row);
            textViewTripName = itemView.findViewById(R.id.textViewTripName);
            textViewTripDate = itemView.findViewById(R.id.textViewTripDate);
            textViewTripTime = itemView.findViewById(R.id.textTime);
            textViewTripFrom = itemView.findViewById(R.id.textViewTripWayFrom);
            textViewTripTo = itemView.findViewById(R.id.textViewTripWayTo);
            textViewTripWay = itemView.findViewById(R.id.txtType);
            textViewRepeat = itemView.findViewById(R.id.txtRepeat);
            btnTripStart = itemView.findViewById(R.id.buttonTripStart);
            btnTripNotes = itemView.findViewById(R.id.buttonTripNotes);
            btnTripCancel = itemView.findViewById(R.id.buttonTripCancel);


        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.trip_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TripData current = tripDataList.get(position);
        holder.textViewTripName.setText(current.getTripName());
        holder.textViewTripDate.setText(current.getDate());
        holder.textViewTripTime.setText(current.getTime());
        holder.textViewTripFrom.setText(current.getStartPoint());
        holder.textViewTripTo.setText(current.getEnaPoint());
        holder.textViewRepeat.setText(current.getRepeatData());
        holder.textViewTripWay.setText(current.getWayData());

        holder.btnTripStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr=" + tripDataList.get(position).getLat_long_endPoint()));
                String title = "TripReminder ";
                Intent chooser = Intent.createChooser(intent, title);

                try {
                    context.startActivity(chooser);
                    updateTrip(current, "done");
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(context, "NO APP Can Open THIS !!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.btnTripCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTrip(current, "cancel");
            }
        });



    }

    public void setValues(List<TripData> list) {
        tripDataList = list;
    }

    public List<TripData> getTripDataList() {
        return tripDataList;
    }

    public void updateTrip(TripData tripData, String state) {
        Repository repository = new Repository(ApplicationR.getApplication());
        tripData.setState(state);
        repository.update(tripData);

    }

    @Override
    public int getItemCount() {
        return tripDataList.size();
    }


}
