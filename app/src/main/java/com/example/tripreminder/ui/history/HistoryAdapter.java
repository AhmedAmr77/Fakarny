package com.example.tripreminder.ui.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripreminder.R;
import com.example.tripreminder.database.TripData;

import java.util.List;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private final Context context;
    private List<TripData> values;

    public HistoryAdapter(Context context, List<TripData> values) {
        this.context = context;
        this.values = values;
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
        holder.typeVal.setText(current.getWayData());
        holder.dateVal.setText(current.getDate()+" At "+current.getTime());
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
            tripName = itemView.findViewById(R.id.extName);
            txtStates = itemView.findViewById(R.id.txtState);
            fromVal = itemView.findViewById(R.id.txtFromVal);
            toVal = itemView.findViewById(R.id.txtToVal);
            dateVal = itemView.findViewById(R.id.txtDateVal);
            typeVal = itemView.findViewById(R.id.txtTypeVal);

            constraintLayout = itemView.findViewById(R.id.row);

        }
    }
}
