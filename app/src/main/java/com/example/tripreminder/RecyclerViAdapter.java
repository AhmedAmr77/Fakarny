package com.example.tripreminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.testdrawer.ui.home.HomeFragment;

import java.util.List;

public class RecyclerViAdapter extends RecyclerView.Adapter<RecyclerViAdapter.ViewHolder> {

    final Context context;
    List<TripData> tripDataList;

    public RecyclerViAdapter(Context context, List<TripData> tripDataList) {
        this.context = context;
        this.tripDataList = tripDataList;
        Toast.makeText(context, "RVAdptConstruc"+tripDataList.get(0).getTripName(), Toast.LENGTH_SHORT).show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View layout;
        ConstraintLayout row;
        TextView textViewTripName, textViewTripDate, textViewTripTime, textViewTripFrom, textViewTripTo, textViewTripWay;
        Button btnTripStart, btnTripOptions, btnTripNotes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = itemView;
            row = itemView.findViewById(R.id.row);
            textViewTripName = itemView.findViewById(R.id.textViewTripName);
            textViewTripDate = itemView.findViewById(R.id.textViewTripDate);
            textViewTripTime = itemView.findViewById(R.id.textViewTripTime);
            textViewTripFrom = itemView.findViewById(R.id.textViewTripFrom);
            textViewTripTo = itemView.findViewById(R.id.textViewTripTo);
            textViewTripWay = itemView.findViewById(R.id.textViewTripWay);
            btnTripStart = itemView.findViewById(R.id.buttonTripStart);
            btnTripNotes = itemView.findViewById(R.id.buttonTripNotes);
            btnTripOptions = itemView.findViewById(R.id.buttonTripOptions);

            Toast.makeText(context, "VHConstructor"+tripDataList.get(0).getTripName(), Toast.LENGTH_SHORT).show();

        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.trip_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        Toast.makeText(context, "CREATE"+tripDataList.get(0).getTripName(), Toast.LENGTH_SHORT).show();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewTripName.setText(tripDataList.get(position).getTripName());
        holder.textViewTripDate.setText(tripDataList.get(position).getDate());
        holder.textViewTripTime.setText(tripDataList.get(position).getTime());
        holder.textViewTripFrom.setText(tripDataList.get(position).getStartPoint());
        holder.textViewTripTo.setText(tripDataList.get(position).getEnaPoint());
        holder.textViewTripWay.setText(tripDataList.get(position).getWayData());
        Toast.makeText(context, "BIND"+tripDataList.get(0).getTripName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return tripDataList.size();
    }






}
