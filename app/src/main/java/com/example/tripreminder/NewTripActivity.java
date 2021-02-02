package com.example.tripreminder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.tripreminder.database.Database;
import com.example.tripreminder.database.Repository;
import com.example.tripreminder.database.TripDao;
import com.example.tripreminder.database.TripData;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class NewTripActivity extends AppCompatActivity implements View.OnClickListener {
    private final String[] dataRepeat = {"No Repeat", "Repeat Daily", "Repeat Weekly", "Repeat Monthly"};
    private final String[] dataWay = {"One Way Trip", "Round Trip"};
    private final static int START_PLACE = 33;
    private final static int END_PLACE = 55;
    private TextView textDate, textTime, startPlace, endPlace;
    private int year, month, day;
    private int finalSelectedYear, finalSelectedMoth, finalSelectedDay;
    private int hour, minute;
    private int finalHours, finalMinute;
    private List<Place.Field> fields;
    private EditText name;
    private LatLng startLatLng, endLatLng;
    private Button btnTime, btnDate, addNewTrip;
    private Spinner repeat, way;
    private Calendar calendar;
    private String finalWay, finalRepeat, finalStartAddress, finalEndAddress;
    private TripData data;
    private ArrayAdapter<String> repeatAd, wayAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyDnbtBwgXmFh-e3jDYu3ffqDpOEOb8vU3Y", Locale.US);
        }

        calendar = Calendar.getInstance();
        initFindView();
        initOnAction();
        repeatAd = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dataRepeat);
        wayAd = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dataWay);
        repeatAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wayAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repeat.setAdapter(repeatAd);
        way.setAdapter(wayAd);
        repeat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                finalRepeat = dataRepeat[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        way.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                finalWay = dataWay[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fields = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG);
        data = (TripData) getIntent().getSerializableExtra("updateObj");
        if (data != null) {
            initUpdateValues();
            initUpdateView();
        }
    }

    private void initUpdateValues() {
        finalStartAddress = data.getStartPoint();
        finalEndAddress = data.getEnaPoint();
        String[] times = data.getTime().split(":");
        finalMinute = Integer.parseInt(times[1]);
        finalHours = Integer.parseInt(times[0]);
        String[] dates = data.getDate().split("-");
        finalSelectedDay = Integer.parseInt(dates[0]);
        finalSelectedMoth = Integer.parseInt(dates[1]);
        finalSelectedYear = Integer.parseInt(dates[2]);
        finalWay = data.getWayData();
        finalRepeat = data.getRepeatData();
        String sll[] = data.getLat_long_startPoint().split(",");
        startLatLng = new LatLng(Double.parseDouble(sll[0]), Double.parseDouble(sll[1]));
        String ell[] = data.getLat_long_endPoint().split(",");
        endLatLng = new LatLng(Double.parseDouble(ell[0]), Double.parseDouble(ell[1]));
    }

    private void initUpdateView() {
        addNewTrip.setText("update");
        name.setText(data.getTripName());
        startPlace.setText(data.getStartPoint());
        endPlace.setText(data.getEnaPoint());
        textDate.setText(data.getDate());
        textTime.setText(data.getTime());
        int posR = repeatAd.getPosition(data.getRepeatData());
        repeat.setSelection(posR);
        int posW = wayAd.getPosition(data.getWayData());
        way.setSelection(posW);
    }

    private void initOnAction() {
        endPlace.setOnClickListener(this);
        btnDate.setOnClickListener(this);
        btnTime.setOnClickListener(this);
        startPlace.setOnClickListener(this);
        addNewTrip.setOnClickListener(this);
        addNewTrip.setOnClickListener(this);
    }

    private void initFindView() {
        startPlace = findViewById(R.id.editStartPoint);
        endPlace = findViewById(R.id.editEndPoint);
        btnTime = findViewById(R.id.btnTime);
        btnDate = findViewById(R.id.btnDate);
        textDate = findViewById(R.id.textDate);
        textTime = findViewById(R.id.textTime);
        repeat = (Spinner) findViewById(R.id.spinner1);
        way = (Spinner) findViewById(R.id.spinner2);
        name = findViewById(R.id.editName);
        addNewTrip = findViewById(R.id.addNewTrip);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        startPlace.setEnabled(true);
        endPlace.setEnabled(true);
        if (requestCode == START_PLACE) {
            if (resultCode == RESULT_OK) {
                Place place = null;
                if (data != null) {
                    place = Autocomplete.getPlaceFromIntent(data);
                    finalStartAddress = place.getAddress();
                    startPlace.setText(finalStartAddress);
                    startLatLng = place.getLatLng();
                }
            }
        } else if (requestCode == END_PLACE) {
            if (resultCode == RESULT_OK) {
                Place place;
                if (data != null) {
                    place = Autocomplete.getPlaceFromIntent(data);
                    finalEndAddress = place.getAddress();
                    endPlace.setText(finalEndAddress);
                    endLatLng = place.getLatLng();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnDate) {
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog mDatePicker = new DatePickerDialog(NewTripActivity.this, new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                    textDate.setText(selectedDay + "-" + (selectedMonth + 1) + "-" + selectedYear);
                    finalSelectedDay = selectedDay;
                    finalSelectedMoth = selectedMonth;
                    finalSelectedYear = selectedYear;
                }
            }, year, month, day);
            mDatePicker.setTitle("Select Trip date");
            mDatePicker.show();
        } else if (id == R.id.btnTime) {
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(NewTripActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    textTime.setText(selectedHour + ":" + selectedMinute);
                    finalHours = selectedHour;
                    finalMinute = selectedMinute;
                }
            }, hour, minute, true);
            mTimePicker.setTitle("Select Trip Time");
            mTimePicker.show();
        } else if (id == R.id.editStartPoint) {
            setPlace(START_PLACE);
        } else if (id == R.id.editEndPoint) {
            setPlace(END_PLACE);
        } else if (id == R.id.addNewTrip) {
            String cDate = year + "" + month + "" + day;
            String sDate = finalSelectedYear + "" + finalSelectedMoth + "" + finalSelectedDay;
            if (finalStartAddress != null && finalEndAddress != null) {
                if (Integer.parseInt(cDate) < Integer.parseInt(sDate)) {
                    if (!textTime.getText().toString().isEmpty()) {
                        setTrip();
                    } else {
                        Toast.makeText(this, "please enter time", Toast.LENGTH_LONG).show();
                    }
                } else if (Integer.parseInt(cDate) == Integer.parseInt(sDate)) {
                    if (hour < finalHours) {
                        setTrip();
                    } else {
                        if (minute < finalMinute) {
                            setTrip();
                        } else {
                            Toast.makeText(this, "please enter correct time", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(this, "the date you selected is less than current date", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "please enter start and end points", Toast.LENGTH_LONG).show();
            }
        }

    }

    public void setTrip() {
        Repository repository = new Repository(getApplication());
        if (data == null) {
            data = new TripData();
            data.setDate(textDate.getText().toString());
            data.setTime(finalHours + ":" + finalMinute);
            data.setStartPoint(finalStartAddress);
            data.setEnaPoint(finalEndAddress);
            data.setTripName(name.getText().toString());
            data.setLat_long_startPoint(startLatLng.latitude + "," + startLatLng.longitude);
            data.setLat_long_endPoint(endLatLng.latitude + "," + endLatLng.longitude);
            data.setRepeatData(finalRepeat);
            data.setWayData(finalWay);
            data.setState("upcoming");
            repository.insert(data);
        } else {
            data.setDate(textDate.getText().toString());
            data.setTime(finalHours + ":" + finalMinute);
            data.setStartPoint(finalStartAddress);
            data.setEnaPoint(finalEndAddress);
            data.setTripName(name.getText().toString());
            data.setLat_long_startPoint(startLatLng.latitude + "," + startLatLng.longitude);
            data.setLat_long_endPoint(endLatLng.latitude + "," + endLatLng.longitude);
            data.setRepeatData(finalRepeat);
            data.setWayData(finalWay);
            repository.update(data);
        }
        finish();
    }

    public void setPlace(int id) {
        startPlace.setEnabled(false);
        endPlace.setEnabled(false);
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                .build(this);
        startActivityForResult(intent, id);
    }
}