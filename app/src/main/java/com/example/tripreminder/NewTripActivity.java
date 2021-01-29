package com.example.tripreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class NewTripActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener{
        String[] dataRepeat = { "No Repeat", "Repeat Daily","Repeat Weekly", "Repeat Monthly" };
        String[] dataWay = { "One Way Trip", "Round Trip" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);
        Spinner repeat = (Spinner) findViewById(R.id.spinner1);
        repeat.setOnItemSelectedListener(this);
        Spinner way = (Spinner) findViewById(R.id.spinner2);
        way.setOnItemSelectedListener(this);

        ArrayAdapter repeatAd = new ArrayAdapter(this,android.R.layout.simple_spinner_item,dataRepeat);
        ArrayAdapter wayAd = new ArrayAdapter(this,android.R.layout.simple_spinner_item,dataWay);

        repeatAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wayAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        repeat.setAdapter(repeatAd);
        way.setAdapter(wayAd);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}