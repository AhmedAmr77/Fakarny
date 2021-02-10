package com.FakarnyAppForTripReminder.Fakarny;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.FakarnyAppForTripReminder.Fakarny.database.NoteData;

import java.util.List;


public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private final Context context;
    private List<NoteData> values;

    public NoteAdapter(Context context, List<NoteData> values) {
        this.context = context;
        this.values = values;
    }



    public List<NoteData> getValues() {
        return values;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_note, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public void setValues(List<NoteData> list) {
        values = list;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String current=values.get(position).getNote();
        holder.note.setText(current);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView note;
        public LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            note = itemView.findViewById(R.id.txtViewNote);
            linearLayout = itemView.findViewById(R.id.row);
        }
    }
}
