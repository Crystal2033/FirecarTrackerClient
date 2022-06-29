/*
 *  Owlsight ActivityBaseView
 *  Created by frostik0409@gmail.com
 *  Kirill Stulnikov (Woipot)
 *  on 29.05.20 16:56
 *
 *  Copyright © 2019 Petr Baev. All rights reserved.
 *  Last modified 29.05.20 16:10
 */

package com.aqulasoft.fireman.mobile.ui.base;

import androidx.annotation.Nullable;

import moxy.viewstate.strategy.SingleStateStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(SingleStateStrategy.class)
public interface ActivityBaseView extends BaseView {
    void ShowInternetAccessSnake(boolean show);

    @StateStrategyType(SkipStrategy.class)
    void closeScreenWithResult(int resultCode, @Nullable String msg);
}

