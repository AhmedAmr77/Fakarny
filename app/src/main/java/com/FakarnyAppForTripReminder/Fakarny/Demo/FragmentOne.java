package com.FakarnyAppForTripReminder.Fakarny.Demo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.FakarnyAppForTripReminder.Fakarny.MainActivity;
import com.FakarnyAppForTripReminder.Fakarny.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class FragmentOne extends Fragment {

    Button next;
    TextView skip;
    ViewPager viewPager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences shared = this.getActivity().getSharedPreferences("shared", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shared.edit();
        editor.putBoolean("firstStart",false);
        editor.apply();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_one,container,false);
        viewPager =getActivity().findViewById(R.id.viewPager);
        SharedPreferences shared = this.getActivity().getSharedPreferences("shared", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shared.edit();
        editor.putBoolean("firstStart",false);
        editor.apply();

        next=view.findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });

        skip=view.findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


        return view;
    }


}