/*
 *  Owlsight IconsProvider
 *  Created by frostik0409@gmail.com
 *  Kirill Stulnikov (Woipot)
 *  on 29.05.20 16:56
 *
 *  Copyright © 2019 Petr Baev. All rights reserved.
 *  Last modified 29.05.20 16:10
 */

package com.aqulasoft.fireman.mobile.ui.base;

import android.util.Pair;
import android.view.View;

public interface IconsProvider {

    default Pair<View.OnClickListener, Integer> getAdditionalIcon() {
        return null;
    }
}
