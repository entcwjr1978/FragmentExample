package com.lightcyclesoftware.fragmentexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.logging.Level;
import java.util.logging.Logger;


public class SampleObserver extends BroadcastReceiver {

    private SampleLoader mLoader;

    public SampleObserver(SampleLoader loader) {
        mLoader = loader;
        IntentFilter filter= new IntentFilter();
        filter.addAction("com.google.android.c2dm.intent.RECEIVE");
        filter.addCategory("com.lightcyclesoftware.fragmentexample");
        loader.getContext().registerReceiver(this, filter);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
        String messageType = gcm.getMessageType(intent);

        if (extras != null && !extras.isEmpty()) {  // has effect of unparcelling Bundle
            // Since we're not using two way messaging, this is all we really to check for
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                Logger.getLogger("GCM_RECEIVED").log(Level.INFO, extras.toString());
                mLoader.onContentChanged();
            }
        }
    }
}
