package com.FakarnyAppForTripReminder.Fakarny.ui.history;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.FakarnyAppForTripReminder.Fakarny.R;
import com.FakarnyAppForTripReminder.Fakarny.ShowNotes;
import com.FakarnyAppForTripReminder.Fakarny.database.TripData;

import java.util.List;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private final Context context;
    private List<TripData> values;

    public HistoryAdapter(Context context, List<TripData> values) {
        this.context = context;
        this.values = values;
    }

    public List<TripData> getValues() {
        return values;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public void setValues(List<TripData> list) {
        values = list;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TripData current=values.get(position);
        holder.tripName.setText(current.getTripName());
        holder.txtStates.setText(current.getState());
        holder.fromVal.setText(current.getStartPoint());
        holder.toVal.setText(current.getEnaPoint());
        holder.typeVal.setText(checkString(current.getWayData()));
        holder.dateVal.setText(current.getDate()+"  "+ context.getResources().getString(R.string.time_card) +"  "+current.getTime());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, ShowNotes.class);
                intent.putExtra("tripNotes",current);
                context.startActivity(intent);
            }
        });
    }

    String checkString(String s){
        switch (s){
            case "One Way Trip":
                return context.getResources().getString(R.string.One_Way_Trip);
            case "Round Trip":
                return context.getResources().getString(R.string.Round_Trip);
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tripName, txtStates, fromVal;
        public TextView toVal,  dateVal, typeVal;
        public ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tripName = itemView.findViewById(R.id.textViewTripName);
            txtStates = itemView.findViewById(R.id.txtState);
            fromVal = itemView.findViewById(R.id.textViewTripWayFrom);
            toVal = itemView.findViewById(R.id.textViewTripWayTo);
            dateVal = itemView.findViewById(R.id.textViewTripDate);
            typeVal = itemView.findViewById(R.id.txtType);

            constraintLayout = itemView.findViewById(R.id.row);

        }
    }
}
