package com.hacker.eaun.cigmanotes.core;

import android.app.Application;
import android.content.Context;

/**
 * Created by Eaun-Ballinger on 12/03/2017.
 */

public class MainApplication extends Application {

    public final Context GlobalContext;

    public MainApplication(Context globalContext) {
        GlobalContext = globalContext;
    }
}
