package com.aqulasoft.fireman.mobile.di.base.preferences;

import com.aqulasoft.fireman.mobile.base.preferences.FiremanPreferences;
import com.aqulasoft.fireman.mobile.base.preferences.PreferencesImpl;

import dagger.Binds;
import dagger.Module;

@Module
abstract public class PrefModule {

    @Binds
    abstract FiremanPreferences prefs(PreferencesImpl preferences);
}
