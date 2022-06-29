/*
 *  Owlsight MainView
 *  Created by frostik0409@gmail.com
 *  Kirill Stulnikov (Woipot)
 *  on 29.09.20 17:14
 *
 *  Copyright © 2019 Petr Baev. All rights reserved.
 *  Last modified 29.09.20 16:47
 */

package com.aqulasoft.fireman.mobile.ui.postlogin;

import android.net.Uri;

import com.aqulasoft.fireman.mobile.ui.base.ActivityBaseView;

import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface MainView extends ActivityBaseView {

    @StateStrategyType(SkipStrategy.class)
    void openWebPage(Uri uri);
}
