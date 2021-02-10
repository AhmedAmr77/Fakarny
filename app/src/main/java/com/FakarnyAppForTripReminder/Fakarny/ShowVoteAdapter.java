package com.FakarnyAppForTripReminder.Fakarny;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.FakarnyAppForTripReminder.Fakarny.database.NoteData;

import java.util.ArrayList;
import java.util.List;


public class ShowVoteAdapter extends RecyclerView.Adapter<ShowVoteAdapter.ViewHolder> {
    private final Context context;
    private List<NoteData> values;
    private final boolean upcoming;

    public ShowVoteAdapter(Context context, List<NoteData> values, boolean upcoming) {
        this.context = context;
        this.values = values;
        this.upcoming = upcoming;
        if (values == null) {
            this.values = new ArrayList<>();
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_show_note, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NoteData current = values.get(position);
        holder.note.setText(current.getNote());
        holder.checkBox.setEnabled(false);
        boolean status = current.isStatus();
        holder.checkBox.setChecked(status);
        if (!upcoming) {
            if (!status) {
                holder.linearLayout.setBackgroundColor(Color.RED);
            } else {
                holder.linearLayout.setBackgroundColor(Color.GREEN);
            }
        }else{
            holder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
        }
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView note;
        public CheckBox checkBox;
        public CardView linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            note = itemView.findViewById(R.id.txtViewNote);
            checkBox = itemView.findViewById(R.id.checkBoxID);
            linearLayout = itemView.findViewById(R.id.row);
        }
    }
}
