/*
 *  Owlsight BaseDialogFragment
 *  Created by frostik0409@gmail.com
 *  Kirill Stulnikov (Woipot)
 *  on 11.02.21 1:37
 *
 *  Copyright © 2019 Petr Baev. All rights reserved.
 *  Last modified 11.02.21 1:37
 */

package com.aqulasoft.fireman.mobile.ui.base;

import static android.widget.ListPopupWindow.WRAP_CONTENT;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowMetrics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.util.Pair;
import androidx.viewbinding.ViewBinding;

import com.aqulasoft.fireman.mobile.base.utils.enums.ToastType;
import com.aqulasoft.fireman.mobile.ui.custom.Dialogs;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import moxy.MvpAppCompatDialogFragment;

public abstract class BaseDialogFragment<T extends ViewBinding> extends MvpAppCompatDialogFragment implements BaseView {

    protected T mBinding;

    private KProgressHUD mLazyLoader;

    ///////////////////////////////////////////////////////////////////////////
    //                          fragment
    ///////////////////////////////////////////////////////////////////////////

    public static Pair<Integer, Integer> getScreenWidth(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowMetrics windowMetrics = activity.getWindowManager().getCurrentWindowMetrics();
            Insets insets = windowMetrics.getWindowInsets()
                    .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
            return new Pair<>(windowMetrics.getBounds().width() - insets.left - insets.right, windowMetrics.getBounds().height() - insets.top - insets.bottom);
        } else {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            return new Pair<>(displayMetrics.widthPixels, displayMetrics.heightPixels);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            Method m = getBindingClass().getDeclaredMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
            mBinding = (T) m.invoke(null, getLayoutInflater(), container, false);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return mBinding.getRoot();
    }

    ///////////////////////////////////////////////////////////////////////////
    //                          Base View
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void showNoCancelableLoading(boolean show) {
        if (mLazyLoader == null && show)
            mLazyLoader = Dialogs.noCancableLoading(getContext());

        if (show)
            mLazyLoader.show();
        else if (mLazyLoader != null)
            mLazyLoader.dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        resizeDialog();
    }

    ///////////////////////////////////////////////////////////////////////////
    //                          BaseView
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void closeScreen(@Nullable String msg) {
        if (msg != null) showToast(msg, ToastType.DEFAULT);
        showNoCancelableLoading(false);
        requireActivity().finish();
    }

    @Override
    public void showToast(@NonNull String msg, ToastType type) {
        ToastType.toastUtil(msg, type);
    }

    @Override
    public void showToast(@StringRes int msg, ToastType type) {
        showToast(getString(msg), type);
    }

    ///////////////////////////////////////////////////////////////////////////
    //                          abstract
    ///////////////////////////////////////////////////////////////////////////

    abstract protected Class<T> getBindingClass();

    ///////////////////////////////////////////////////////////////////////////
    //                          private
    ///////////////////////////////////////////////////////////////////////////

    /**
     * To resize the size of this dialog
     */
    private void resizeDialog() {
        try {
            Window window = getDialog().getWindow();

            Activity activity = getActivity();

            if (activity == null || window == null) return;

            Pair<Integer, Integer> bounds = getScreenWidth(activity);

            int maxSize = bounds.first;

            if (activity.getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT)
                maxSize = bounds.second;

            window.setLayout((int) (maxSize * 0.95), WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));// make tranparent around the popup
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

