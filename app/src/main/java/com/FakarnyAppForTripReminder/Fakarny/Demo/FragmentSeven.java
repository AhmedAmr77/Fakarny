package com.FakarnyAppForTripReminder.Fakarny.Demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.FakarnyAppForTripReminder.Fakarny.R;

public class FragmentSeven extends Fragment {

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
        View view= inflater.inflate(R.layout.fragment_seven,container,false);
        viewPager =getActivity().findViewById(R.id.viewPager);
        next=view.findViewById(R.id.next7);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(6);
            }
        });
        back=view.findViewById(R.id.back7);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(4);
            }
        });



        return view;
    }


}