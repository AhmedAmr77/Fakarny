package com.FakarnyAppForTripReminder.Fakarny;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.FakarnyAppForTripReminder.Fakarny.Demo.FragmentsAdapter;

public class DemoActivity extends AppCompatActivity {
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        SharedPreferences shared =getSharedPreferences("shared",MODE_PRIVATE);
        boolean firstTime=shared.getBoolean("firstStart",true);
        if(firstTime)
        {
            viewPager =findViewById(R.id.viewPager);
            FragmentsAdapter introAdaptor= new FragmentsAdapter(getSupportFragmentManager());
            viewPager.setAdapter(introAdaptor);
            shared.edit().putBoolean("firstStart",false).apply();
        }
    }
}