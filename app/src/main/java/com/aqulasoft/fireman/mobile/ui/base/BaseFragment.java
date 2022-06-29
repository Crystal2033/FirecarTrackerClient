/*
 *  Owlsight BaseFragment
 *  Created by frostik0409@gmail.com
 *  Kirill Stulnikov (Woipot)
 *  on 22.03.21 15:13
 *
 *  Copyright © 2019 Petr Baev. All rights reserved.
 *  Last modified 22.03.21 15:11
 */

package com.aqulasoft.fireman.mobile.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewbinding.ViewBinding;

import com.aqulasoft.fireman.mobile.base.utils.enums.ToastType;
import com.aqulasoft.fireman.mobile.ui.custom.Dialogs;
import com.aqulasoft.fireman.mobile.ui.other.dialog.accept.AcceptCallbackListener;
import com.aqulasoft.fireman.mobile.ui.other.dialog.accept.AcceptDialog;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import moxy.MvpAppCompatFragment;

public abstract class BaseFragment<T extends ViewBinding> extends MvpAppCompatFragment implements BaseView, IconsProvider {

    protected T mBinding;

    private KProgressHUD mLazyLoader;

    protected NavController mNavController;

    ///////////////////////////////////////////////////////////////////////////
    //                          Fragment
    ///////////////////////////////////////////////////////////////////////////

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            Method m = getBindingClass().getDeclaredMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
            mBinding = (T) m.invoke(null, getLayoutInflater(), container, false);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        assert mBinding != null;
        return mBinding.getRoot();
    }

    @Override
    public void onPause() {
        super.onPause();

        if(mLazyLoader != null) {
            mLazyLoader.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNavController = Navigation.findNavController(view);
    }

    ///////////////////////////////////////////////////////////////////////////
    //                          BaseView
    ///////////////////////////////////////////////////////////////////////////

    @Override
    @CallSuper
    public void showNoCancelableLoading(boolean show) {
        if (mLazyLoader == null && show)
            mLazyLoader = Dialogs.noCancableLoading(getContext());

        if (show && !mLazyLoader.isShowing())
            mLazyLoader.show();
        else if (mLazyLoader != null)
            mLazyLoader.dismiss();
    }

    @Override
    public void toPreviousScreen(@Nullable String msg) {
        if (msg != null) showToast(msg, ToastType.DEFAULT);
        showNoCancelableLoading(false);
        mNavController.popBackStack();
    }

    @Override
    public void toPreviousScreen(@StringRes int msg) {
        toPreviousScreen(getString(msg));
    }

    @Override
    public void closeScreen(@Nullable String msg) {
        if (msg != null) showToast(msg, ToastType.DEFAULT);
        showNoCancelableLoading(false);
        requireActivity().finish();
    }

    @Override
    public void showToast(@NonNull String msg, ToastType type) {
        ToastType.toastUtil(msg.trim(), type);
    }

    @Override
    public void showToast(@StringRes int msg, ToastType type) {
        showToast(getString(msg), type);
    }

    @Override
    public void showAcceptDialog(String content, @Nullable AcceptCallbackListener listener) {
        AcceptDialog acceptDialog = new AcceptDialog(content, listener);

        if (isAdded())
            acceptDialog.show(getParentFragmentManager(), AcceptDialog.TAG);
    }

    @Override
    public void showAcceptDialog(@StringRes int content, @Nullable AcceptCallbackListener listener) {
        showAcceptDialog(getString(content), listener);
    }

    @Override
    public void showInfoDialog(@StringRes int content) {
        AcceptDialog acceptDialog = new AcceptDialog(getString(content), null);
        acceptDialog.makeInfoDialog();

        if (isAdded())
            acceptDialog.show(getParentFragmentManager(), AcceptDialog.TAG);
    }


    ///////////////////////////////////////////////////////////////////////////
    //                          abstract
    ///////////////////////////////////////////////////////////////////////////

    abstract protected Class<T> getBindingClass();
}
