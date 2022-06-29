/*
 *  Owlsight Preferences
 *  Created by frostik0409@gmail.com
 *  Kirill Stulnikov (Woipot)
 *  on 02.06.21 11:49
 *
 *  Copyright © 2019 Petr Baev. All rights reserved.
 *  Last modified 02.06.21 11:49
 */

package com.aqulasoft.fireman.mobile.base.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.aqulasoft.fireman.mobile.di.scopes.AppScope;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferencesImpl implements FiremanPreferences {

    private static final String PREFERENCES_NAME = "fireman-preferences-";

    private static final String PREFERENCE_TEST = "test";

    private final SharedPreferences preferences;

    @Inject
    public PreferencesImpl(Context context) {
        preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }
}
