package com.example.tripreminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("trip","onReceive"+intent.getIntExtra("tripData", -1));
        Intent MyAlertDialog = new Intent(context.getApplicationContext(), AlarmDialog.class);
        MyAlertDialog.putExtra("tripData", intent.getIntExtra("tripData", -1));
        MyAlertDialog.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        context.getApplicationContext().startActivity(MyAlertDialog);
    }
}