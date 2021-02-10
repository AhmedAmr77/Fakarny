package com.FakarnyAppForTripReminder.Fakarny.Demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.FakarnyAppForTripReminder.Fakarny.R;

public class FragmentSix extends Fragment {

    Button next;
    Button back;
    ViewPager viewPager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_six,container,false);
        viewPager =getActivity().findViewById(R.id.viewPager);
        next=view.findViewById(R.id.next6);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(5);
            }
        });
        back=view.findViewById(R.id.back6);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(3);
            }
        });



        return view;
    }


}