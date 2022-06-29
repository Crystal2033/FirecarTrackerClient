/*
 *  Owlsight BaseActivity
 *  Created by frostik0409@gmail.com
 *  Kirill Stulnikov (Woipot)
 *  on 02.06.21 11:49
 *
 *  Copyright © 2019 Petr Baev. All rights reserved.
 *  Last modified 02.06.21 11:28
 */

package com.aqulasoft.fireman.mobile.ui.base;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.aqulasoft.fireman.mobile.base.utils.enums.ToastType;
import com.aqulasoft.fireman.mobile.ui.custom.Dialogs;
import com.aqulasoft.fireman.mobile.ui.other.dialog.accept.AcceptCallbackListener;
import com.aqulasoft.fireman.mobile.ui.other.dialog.accept.AcceptDialog;
import com.kaopiz.kprogresshud.KProgressHUD;

import moxy.MvpAppCompatActivity;

public abstract class BaseActivity extends MvpAppCompatActivity implements ActivityBaseView {
    private KProgressHUD mLazyLoader;

    @Override
    protected void onPause() {
        super.onPause();

        if (mLazyLoader != null) {
            mLazyLoader.dismiss();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    //                          Activity base view implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void ShowInternetAccessSnake(boolean show) {
    }

    @CallSuper
    @Override
    public void showNoCancelableLoading(boolean show) {
        if (mLazyLoader == null && show)
            mLazyLoader = Dialogs.noCancableLoading(this);

        if (show)
            mLazyLoader.show();
        else if (mLazyLoader != null)
            mLazyLoader.dismiss();
    }

    @CallSuper
    @Override
    public void closeScreenWithResult(int resultCode, @Nullable String msg) {
        setResult(resultCode);
        closeScreen(msg);
    }

    @Override
    public void showAcceptDialog(String content, @Nullable AcceptCallbackListener listener) {
        AcceptDialog acceptDialog = new AcceptDialog(content, listener);

        acceptDialog.show(getSupportFragmentManager(), AcceptDialog.TAG);
    }

    @Override
    public void showAcceptDialog(@StringRes int content, @Nullable AcceptCallbackListener listener) {
        showAcceptDialog(getString(content), listener);
    }


    @CallSuper
    @Override
    public void closeScreen(@Nullable String msg) {
        if (msg != null) showToast(msg, ToastType.DEFAULT);
        showNoCancelableLoading(false);
        finish();
    }

    @Override
    public void showToast(@NonNull String msg, ToastType type) {
        ToastType.toastUtil(msg, type);
    }

    @Override
    public void showToast(@StringRes int msg, ToastType type) {
        showToast(getString(msg), type);
    }
}
