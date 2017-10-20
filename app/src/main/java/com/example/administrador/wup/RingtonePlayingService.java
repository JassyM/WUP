package com.example.administrador.wup;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Jassy on 09/10/2017.
 */

public class RingtonePlayingService extends Service {

    MediaPlayer media_song;
    int starId;
    boolean isRunning;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        String state = intent.getExtras().getString("extra");

        assert state != null;
        switch (state){
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }

        if(!this.isRunning && startId ==1){
            media_song = MediaPlayer.create(this, R.raw.sound_effects_extreme_clock_alarm);
            media_song.start();
            this.isRunning = true;
            this.starId = 0;
        }
        else if(this.isRunning && startId == 0){
            media_song.stop();
            media_song.reset();
            this.isRunning = false;
            this.starId = 0;
        }
        else if(!this.isRunning && startId == 0){
            this.isRunning = false;
            this.starId = 0;
        }
        else if(this.isRunning && startId == 1){
            this.isRunning = true;
            this.starId = 1;
        }

        /*media_song = MediaPlayer.create(this, R.raw.sound_effects_extreme_clock_alarm);
        media_song.start();*/

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        // Tell the user we stopped.
        super.onDestroy();
        this.isRunning = false;
    }

}