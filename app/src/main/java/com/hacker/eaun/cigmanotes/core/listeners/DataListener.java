package com.hacker.eaun.cigmanotes.core.listeners;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by Eaun-Ballinger on 23/10/2016.
 * data connection for android wear :)
 *
 */


public class DataListener extends Activity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static final String TAG = "MyDataMap.....";
    public static final String WEARABLE_DATA_PATH = "/wearable/data/path";

    GoogleApiClient mGoogleApiClient = null;

    public void MyDataListener () {
        GoogleApiClient.Builder mBuilder = new GoogleApiClient.Builder(this);
        mBuilder.addApi(Wearable.API);
        mBuilder.addConnectionCallbacks(this);
        mBuilder.addOnConnectionFailedListener(this);
        mBuilder.build();
    }

    public boolean moveDatabaseFrom(Context sourceContext, String name) {
        return super.moveDatabaseFrom(sourceContext, name);
    }

    @Override
    public void onConnected(@Nullable Bundle pBundle) {

    }

    @Override
    public void onConnectionSuspended(int pI) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult pConnectionResult) {

    }
}
