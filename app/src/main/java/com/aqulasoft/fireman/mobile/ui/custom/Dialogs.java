/*
 *  Owlsight Dialogs
 *  Created by frostik0409@gmail.com
 *  Kirill Stulnikov (Woipot)
 *  on 29.05.20 16:56
 *
 *  Copyright © 2019 Petr Baev. All rights reserved.
 *  Last modified 29.05.20 16:10
 */

package com.aqulasoft.fireman.mobile.ui.custom;

import android.content.Context;
import android.graphics.Color;

import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.Objects;

public class Dialogs {

    public static KProgressHUD noCancableLoading(Context context) {

        FiremanProgressIndicator indicator = new FiremanProgressIndicator(context);

        return KProgressHUD.create(Objects.requireNonNull(context))
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setBackgroundColor(Color.TRANSPARENT)
                .setCancellable(false)
                .setCustomView(indicator)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f);
    }
}
