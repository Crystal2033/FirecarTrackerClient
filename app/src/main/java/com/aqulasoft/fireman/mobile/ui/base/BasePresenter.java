/*
 *  Owlsight BasePresenter
 *  Created by frostik0409@gmail.com
 *  Kirill Stulnikov (Woipot)
 *  on 17.02.2022, 15:47
 *
 *  Copyright © 2019 Petr Baev. All rights reserved.
 *  Last modified 17.02.2022, 1:50
 */

package com.aqulasoft.fireman.mobile.ui.base;

import android.util.Log;

import androidx.annotation.CallSuper;

import com.aqulasoft.fireman.mobile.App;
import com.aqulasoft.fireman.mobile.R;
import com.aqulasoft.fireman.mobile.base.utils.enums.ToastType;
import com.google.gson.JsonSyntaxException;

import java.net.HttpURLConnection;
import java.net.UnknownHostException;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import moxy.MvpPresenter;
import retrofit2.HttpException;

public abstract class BasePresenter<View extends BaseView> extends MvpPresenter<View> {

    private boolean mWasFirstConnection = false;
    private CompositeDisposable mLazyComposite;

    @Override
    public void attachView(View view) {
        super.attachView(view);
    }

    @Override
    public void detachView(View view) {
        super.detachView(view);
        if (mLazyComposite != null)
            mLazyComposite.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLazyComposite != null)
            mLazyComposite.clear();
    }

    ///////////////////////////////////////////////////////////////////////////
    //                          public methods
    ///////////////////////////////////////////////////////////////////////////


    /**
     * call super.onFirstInternetAttach() in override
     * Here you can make all Api calls without worrying about the availability of the Internet
     * <p>
     * called onAttachView if there is internet connection or after internet connection state changed
     */
    @CallSuper
    public void onFirstInternetAttach() {
        mWasFirstConnection = true;
    }

    @CallSuper
    public void addDisposable(Disposable disposable) {
        if (mLazyComposite == null)
            mLazyComposite = new CompositeDisposable();

        mLazyComposite.add(disposable);
    }

    @CallSuper
    public void clearDisposable() {
        if (mLazyComposite != null)
            mLazyComposite.clear();
    }

    ///////////////////////////////////////////////////////////////////////////
    //                          protected
    ///////////////////////////////////////////////////////////////////////////

    protected void showLoading() {
        getViewState().showLoading(true);
    }

    protected void hideLoading() {
        getViewState().showLoading(false);
    }

    protected void showError(Throwable throwable) {
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;

            switch (httpException.code()) {
                case HttpURLConnection.HTTP_FORBIDDEN:
                case HttpURLConnection.HTTP_UNAUTHORIZED:
                    getViewState().showToast(R.string.server_unauthorized, ToastType.ERROR);
                    break;

                case HttpURLConnection.HTTP_CLIENT_TIMEOUT:
                    getViewState().showToast(R.string.server_timeout, ToastType.ERROR);
                    break;

                case HttpURLConnection.HTTP_INTERNAL_ERROR:
                    getViewState().showToast(R.string.server_internal_error, ToastType.ERROR);
                    break;

            }
        } else if (throwable instanceof UnknownHostException) {
            getViewState().showToast(R.string.server_no_internet_connection_error, ToastType.ERROR);
        } else if (throwable instanceof JsonSyntaxException) {
            getViewState().showToast(R.string.server_response_not_supported, ToastType.ERROR);
        } else
            getViewState().showToast(R.string.server_unhandled_error, ToastType.ERROR);


        Log.e(App.DEBUG_TAG, "Base presenter - show error: " + throwable);
    }

    protected void showFailed() {
        getViewState().showToast(R.string.something_went_wrong, ToastType.ERROR);
    }

    protected void showFailed(String msg) {
        getViewState().showToast(msg, ToastType.ERROR);
    }

    protected void showFailed(int msgId) {
        getViewState().showToast(msgId, ToastType.ERROR);
    }
}
