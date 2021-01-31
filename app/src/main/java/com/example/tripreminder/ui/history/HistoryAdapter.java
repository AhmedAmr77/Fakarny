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
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripreminder.R;
import com.example.tripreminder.TripData;

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
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.row_layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tripName.setText(values.get(position).getTripName());
        holder.txtStates.setText(values.get(position).getState());
        holder.fromVal.setText(values.get(position).getStartPoint());
        holder.toVal.setText(values.get(position).getEnaPoint());
        holder.typeVal.setText(values.get(position).getWayData());
        holder.txtStates.setText(values.get(position).getState());
        holder.dateVal.setText(values.get(position).getDate());
        holder.timeVal.setText(values.get(position).getTime());

        holder.linearLayout.setOnClickListener((v)->{
            Toast.makeText(context, values.get(position).getState(), Toast.LENGTH_SHORT).show();
        });
        holder.btnNotes.setOnClickListener((v)->{
        });
        holder.btnDelete.setOnClickListener((v)->{
        });

    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        public TextView tripName,txtStates,from,fromVal;
        public TextView to,toVal,date,dateVal,timeVal,type,typeVal;
        public Button btnNotes,btnDelete;
        public LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tripName=itemView.findViewById(R.id.extName);
            txtStates=itemView.findViewById(R.id.txtState);
            fromVal=itemView.findViewById(R.id.txtFromVal);
            toVal=itemView.findViewById(R.id.txtToVal);
            dateVal=itemView.findViewById(R.id.txtDateVal);
            timeVal=itemView.findViewById(R.id.txtTimeVal);
            typeVal=itemView.findViewById(R.id.txtTypeVal);
            btnNotes=itemView.findViewById(R.id.btnNotes);
            btnDelete=itemView.findViewById(R.id.btnDelete);

            linearLayout =itemView.findViewById(R.id.row);

        }
    }
}
