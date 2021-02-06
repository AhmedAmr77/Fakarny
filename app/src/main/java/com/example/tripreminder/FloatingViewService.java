package com.example.tripreminder;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;

public class FloatingViewService extends Service implements View.OnClickListener {

    private WindowManager mWindowManager;
    private View mFloatingView;
    private View collapsedView;
    private View expandedView;
    private LinearLayout linearLayoutCheckBox;
    private ScrollView scrolly;
    private int versionFlag;

    ArrayList<noteData> notesList;

    public FloatingViewService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //addItemsNotesList();

        //getting the widget layout from xml using layout inflater
        mFloatingView = LayoutInflater.from(this).inflate(R.layout.floating_widget, null);

        checkVersion();

        //setting the layout parameters
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                versionFlag,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        //getting windows services and adding the floating view to it
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mFloatingView, params);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "CANNOT OVERLAY", Toast.LENGTH_SHORT).show();
        }

        //getting the collapsed and expanded view from the floating view
        collapsedView = mFloatingView.findViewById(R.id.layoutCollapsed);
        expandedView = mFloatingView.findViewById(R.id.layoutExpanded);

        scrolly = mFloatingView.findViewById(R.id.scrolly);
        linearLayoutCheckBox = mFloatingView.findViewById(R.id.linearLayoutCheckBox);

        //adding click listener to close button and expanded view
        mFloatingView.findViewById(R.id.buttonClose).setOnClickListener(this);
        scrolly.setOnClickListener(this);
        expandedView.setOnClickListener(this);

        createCheckBox();

/*
        collapsedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FloatingViewService.this, "TOOOOAST", Toast.LENGTH_SHORT).show();
                collapsedView.setVisibility(View.GONE);
                expandedView.setVisibility(View.VISIBLE);
            }
        });
        expandedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FloatingViewService.this, "TOOOOAST", Toast.LENGTH_SHORT).show();
                collapsedView.setVisibility(View.VISIBLE);
                expandedView.setVisibility(View.GONE);
            }
        });
*/

        mFloatingView.findViewById(R.id.relativeLayoutParent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FloatingViewService.this, "CLICK", Toast.LENGTH_SHORT).show();
                if (collapsedView.getVisibility() == View.VISIBLE) {
                    collapsedView.setVisibility(View.GONE);
                    expandedView.setVisibility(View.VISIBLE);
                } else {                                        //NOT USED
                    collapsedView.setVisibility(View.VISIBLE);
                    expandedView.setVisibility(View.GONE);
                }
            }
        });

        //adding an touchlistener to make drag movement of the floating widget
        mFloatingView.findViewById(R.id.relativeLayoutParent).setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Toast.makeText(FloatingViewService.this, "TOOTooooooOOAST", Toast.LENGTH_SHORT).show();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return false;
/*
                    case MotionEvent.ACTION_UP:
                        //when the drag is ended switching the state of the widget
                        collapsedView.setVisibility(View.GONE);
                        expandedView.setVisibility(View.VISIBLE);
                        return true;
*/
                    case MotionEvent.ACTION_MOVE:
                        //this code is helping the widget to move around the screen with fingers
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        mWindowManager.updateViewLayout(mFloatingView, params);
                        return false;
                }
                return false;
            }
        });
    }
/*
    void addItemsNotesList(){
        notesList = new ArrayList<>();
        notesList.add("Vege");
        notesList.add("meat");
        notesList.add("water");
        notesList.add("ATM");
        notesList.add("Pharm");
        notesList.add("car");
        notesList.add("beaks");
        notesList.add("Vege");
        notesList.add("meat");
        notesList.add("water");
        notesList.add("ATM");
        notesList.add("Pharm");
        notesList.add("car");
        notesList.add("beaks");
        notesList.add("Vege");
        notesList.add("meat");
        notesList.add("water");
        notesList.add("ATM");
        notesList.add("Pharm");
        notesList.add("car");
        notesList.add("beaks");
    }
*/
    private void createCheckBox() {
        if(notesList.size()==0) {
            Toast.makeText(this, "There is no notes", Toast.LENGTH_LONG).show();
        } else {
            for(int i=0; i<notesList.size();i++){
                CheckBox c = new CheckBox(this);
                c.setId(i);
                c.setText(notesList.get(i).getNote());
                c.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayoutCheckBox.addView(c);
            }
        }

    }

    void checkVersion(){
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
            versionFlag = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        else
            versionFlag = WindowManager.LayoutParams.TYPE_PHONE;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFloatingView != null)
            mWindowManager.removeView(mFloatingView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.layoutExpanded:
                //switching views
                collapsedView.setVisibility(View.VISIBLE);
                expandedView.setVisibility(View.GONE);
                break;
            */
            case R.id.buttonClose:
                //closing the widget
                checkedCheckBox();
                stopSelf();
                break;

            case R.id.layoutExpanded:
                Toast.makeText(this, "testScrolla", Toast.LENGTH_SHORT).show();
                collapsedView.setVisibility(View.VISIBLE);
                expandedView.setVisibility(View.GONE);
        }
    }

    public void checkedCheckBox(){         //to store in DB what user had check from list
        CheckBox c;
        if(notesList.size()==0) {
            Toast.makeText(this, "Dev, empty notes list", Toast.LENGTH_LONG).show();
        } else {
            for(int i=0; i<notesList.size();i++){
                c = (CheckBox)linearLayoutCheckBox.getChildAt(i);
                if (c.isChecked()){
                    notesList.get(i).setStatus(true);
                }
            }
        }
    }
}

//to open note from any where and when touch anywhere close note ...  TRY two activity: 1-tranceparent 2-widet.  (THINK BEFORE DO IT)