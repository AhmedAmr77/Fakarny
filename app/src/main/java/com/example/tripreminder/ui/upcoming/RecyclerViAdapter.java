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


import com.example.tripreminder.R;
import com.example.tripreminder.TripData;

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
        TextView textViewTripName, textViewTripDate, textViewTripTime, textViewTripFrom, textViewTripTo, textViewTripWay,textViewRepeat;
        Button btnTripStart, btnTripOptions, btnTripNotes;

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
            btnTripOptions = itemView.findViewById(R.id.buttonTripOptions);



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
        holder.textViewTripName.setText(tripDataList.get(position).getTripName());
        holder.textViewTripDate.setText(tripDataList.get(position).getDate());
        holder.textViewTripTime.setText(tripDataList.get(position).getTime());
        holder.textViewTripFrom.setText(tripDataList.get(position).getStartPoint());
        holder.textViewTripTo.setText(tripDataList.get(position).getEnaPoint());
        holder.textViewRepeat.setText(tripDataList.get(position).getRepeatData());
        holder.textViewTripWay.setText(tripDataList.get(position).getWayData());

        holder.btnTripStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr="+tripDataList.get(position).getLat_long_endPoint()));
                String title = "Choose an Appp ";//context.getResources().getString(R.string.chooser_title);
                Intent chooser = Intent.createChooser(intent, title);
                try {
                    context.startActivity(chooser);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(context, "NO APp Can Open THIS !!!", Toast.LENGTH_SHORT).show();
                    // Define what your app should do if no activity can handle the intent.
                }
                Toast.makeText(context, "StartClickedddddddd", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return tripDataList.size();
    }






}
