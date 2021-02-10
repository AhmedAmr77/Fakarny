package com.FakarnyAppForTripReminder.Fakarny.Demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.FakarnyAppForTripReminder.Fakarny.MainActivity;
import com.FakarnyAppForTripReminder.Fakarny.R;

public class FragmentNine extends Fragment {

    TextView done;
    Button back;
    ViewPager viewPager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_nine,container,false);
        viewPager =getActivity().findViewById(R.id.viewPager);
        done=view.findViewById(R.id.next9);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        back=view.findViewById(R.id.back9);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(6);
            }
        });



        return view;
    }


}