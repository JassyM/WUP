package com.example.administrador.wup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Administrador on 09/10/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {


    public void onReceive(Context context, Intent intent) {
        /*Log.e("We are in the receiver.", "Tag!");
        String get_your_string = intent.getExtras().getString("extra");
        Log.e("Whay is the key?", get_your_string);
        Intent service_intent = new Intent(context, RingtonePlayingService.class);
        service_intent.putExtra("extra", get_your_string);
        context.startService(service_intent);*/

        Intent i = new Intent(context, MyTestService.class);
    }

}
