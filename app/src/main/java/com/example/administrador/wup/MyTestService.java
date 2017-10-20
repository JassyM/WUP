package com.example.administrador.wup;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Administrador on 20/10/2017.
 */

public class MyTestService extends IntentService {

    public MyTestService() {
        super("MyTestService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //Do the task here
        Log.i("MyTestService", "Servicio ejecutandose. Recordatorios");
    }
}
