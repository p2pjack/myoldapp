package com.hacker.eaun.cigmanotes;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;


/**
 * Created by Eaun-Ballinger on 23/10/2016.
 *
 *
 */

public class MyListenerService extends WearableListenerService {

    public static final String TAG = "MyDataMap.....";
    public static final String WEARABLE_DATA_PATH = "/wearable/data/path";

    @Override
    public void onMessageReceived(MessageEvent pMessageEvent) {
        if (pMessageEvent.getPath().equals(WEARABLE_DATA_PATH)){
            final String message = new String(pMessageEvent.getData());
            Intent startintent = new Intent(this,MainWatchActivity.class);
            startintent.putExtra("message",message);
            startintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startintent);
            Log.d(TAG,"===Main Activity Started===");

        }
        else {
        super.onMessageReceived(pMessageEvent);        }
    }
}
