package com.aqulasoft.fireman.mobile.di.base.app;

import android.content.Context;

import com.aqulasoft.fireman.mobile.base.preferences.FiremanPreferences;
import com.aqulasoft.fireman.mobile.di.base.preferences.PrefModule;
import com.aqulasoft.fireman.mobile.di.scopes.AppScope;

import javax.inject.Singleton;

import dagger.Component;

@AppScope
@Singleton
@Component( modules = {AppModule.class, PrefModule.class})
public interface AppComponent {

    FiremanPreferences getPrefs();
    Context getContext();

}
