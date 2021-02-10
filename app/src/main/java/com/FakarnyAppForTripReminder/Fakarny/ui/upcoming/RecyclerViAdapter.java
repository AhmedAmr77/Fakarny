package com.FakarnyAppForTripReminder.Fakarny.ui.upcoming;

import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.FakarnyAppForTripReminder.Fakarny.AddNote;
import com.FakarnyAppForTripReminder.Fakarny.FloatingViewService;
import com.FakarnyAppForTripReminder.Fakarny.MainActivity;
import com.FakarnyAppForTripReminder.Fakarny.R;
import com.FakarnyAppForTripReminder.Fakarny.ShowNotes;
import com.FakarnyAppForTripReminder.Fakarny.database.Repository;
import com.FakarnyAppForTripReminder.Fakarny.database.TripData;

import java.util.List;

public class RecyclerViAdapter extends RecyclerView.Adapter<RecyclerViAdapter.ViewHolder> {

    final Context context;
    List<TripData> tripDataList;
    private static final int SYSTEM_ALERT_WINDOW_PERMISSION = 2084;
    private Repository repository;

    public RecyclerViAdapter(Context context, List<TripData> tripDataList, Application application) {
        repository = new Repository(application);
        this.context = context;
        this.tripDataList = tripDataList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View layout;
        ConstraintLayout row;
        TextView textViewTripName, textViewTripDate, textViewTripTime,
                textViewTripFrom, textViewTripTo, textViewTripWay, textViewRepeat;
        Button btnTripStart, btnTripCancel, btnshowNote, btnAddNote;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = itemView;
            row = itemView.findViewById(R.id.row);
            textViewTripName = itemView.findViewById(R.id.textViewTripName);
            textViewTripDate = itemView.findViewById(R.id.textViewTripDate);
            textViewTripTime = itemView.findViewById(R.id.textTime1);
            textViewTripFrom = itemView.findViewById(R.id.textViewTripWayFrom);
            textViewTripTo = itemView.findViewById(R.id.textViewTripWayTo);
            textViewTripWay = itemView.findViewById(R.id.txtType);
            textViewRepeat = itemView.findViewById(R.id.txtRepeat);
            btnTripStart = itemView.findViewById(R.id.buttonTripStart);
            btnTripCancel = itemView.findViewById(R.id.buttonTripCancel);
            btnAddNote = itemView.findViewById(R.id.buttonAddNote);
            btnshowNote = itemView.findViewById(R.id.btnshowNote);
        }
    }

    private void askPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + context.getPackageName()));
        ((MainActivity) context).startActivityForResult(intent, SYSTEM_ALERT_WINDOW_PERMISSION);
    }

    void showWidget(int id) {
        Intent intent = new Intent(context.getApplicationContext(), FloatingViewService.class);
        intent.putExtra("tripID", id);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            (context.getApplicationContext()).startService(intent);
            ((MainActivity) context).finish();
        } else if (Settings.canDrawOverlays(context)) {
            (context.getApplicationContext()).startService(intent);
            ((MainActivity) context).finish();
        } else {
            askPermission();
            Toast.makeText(context, "You need System Alert Window Permission to do this", Toast.LENGTH_SHORT).show();
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
        holder.textViewRepeat.setText(checkStringRpt(current.getRepeatData()));
        holder.textViewTripWay.setText(checkString(current.getWayData()));

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
                    showWidget(current.getId());
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
        holder.btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddNote.class);
                intent.putExtra("TripData", current);
                context.startActivity(intent);
            }
        });

        holder.btnshowNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowNotes.class);
                intent.putExtra("tripNotes", current);
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

    String checkStringRpt(String s){
        switch (s){
            case "No Repeat":
                return context.getResources().getString(R.string.No_Repeat);
            case "Repeat Daily":
                return context.getResources().getString(R.string.Repeat_Daily);
            case "Repeat Weekly":
                return context.getResources().getString(R.string.Repeat_Weekly);
            case "Repeat Monthly":
                return context.getResources().getString(R.string.Repeat_Monthly);
            default:
                return null;
        }
    }

    public void setValues(List<TripData> list) {
        tripDataList = list;
    }

    public List<TripData> getTripDataList() {
        return tripDataList;
    }

    public void updateTrip(TripData tripData, String state) {
        tripData.setState(state);
        if (state.equals("done")) {
            repository.start(tripData);
        } else {
            repository.update(tripData);
        }
    }

    @Override
    public int getItemCount() {
        return tripDataList.size();
    }

}
