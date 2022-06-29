/*
 *  Owlsight App
 *  Created by frostik0409@gmail.com
 *  Kirill Stulnikov (Woipot)
 *  on 26.05.21 20:31
 *
 *  Copyright © 2019 Petr Baev. All rights reserved.
 *  Last modified 26.05.21 20:30
 */

package com.aqulasoft.fireman.mobile;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import java.util.prefs.Preferences;


public class App extends Application {
    public static final String DEBUG_TAG = "Fireman";

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    public static String getKey() {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
