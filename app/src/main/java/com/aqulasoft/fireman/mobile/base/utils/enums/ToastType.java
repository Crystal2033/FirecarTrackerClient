/*
 *  Owlsight ToastType
 *  Created by frostik0409@gmail.com
 *  Kirill Stulnikov (Woipot)
 *  on 29.05.20 16:56
 *
 *  Copyright © 2019 Petr Baev. All rights reserved.
 *  Last modified 29.05.20 16:10
 */

package com.aqulasoft.fireman.mobile.base.utils.enums;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.aqulasoft.fireman.mobile.App;

import es.dmoral.toasty.Toasty;

public enum ToastType {
    INFO,
    ERROR,
    DEFAULT,
    SUCCESS,
    WARNING;

    public static void toastUtil(@NonNull String msg, ToastType type) {
        switch (type) {
            case INFO:
                Toasty.info(App.getInstance(), msg, Toast.LENGTH_LONG).show();
                break;
            case ERROR:
                Toasty.error(App.getInstance(), msg, Toast.LENGTH_LONG).show();
                break;
            case DEFAULT:
                Toasty.normal(App.getInstance(), msg, Toast.LENGTH_LONG).show();
                break;
            case WARNING:
                Toasty.warning(App.getInstance(), msg, Toast.LENGTH_LONG).show();
                break;
            case SUCCESS:
                Toasty.success(App.getInstance(), msg, Toast.LENGTH_LONG).show();
                break;
        }

    }
}
