/*
 *  Owlsight BaseView
 *  Created by frostik0409@gmail.com
 *  Kirill Stulnikov (Woipot)
 *  on 04.03.21 18:47
 *
 *  Copyright © 2019 Petr Baev. All rights reserved.
 *  Last modified 04.03.21 18:45
 */

package com.aqulasoft.fireman.mobile.ui.base;

import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.aqulasoft.fireman.mobile.base.utils.enums.ToastType;
import com.aqulasoft.fireman.mobile.ui.other.dialog.accept.AcceptCallbackListener;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;


@StateStrategyType(SkipStrategy.class)
public interface BaseView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    default void showLoading(boolean show) {
        //ignored
    }

    @StateStrategyType(AddToEndSingleStrategy.class)
    default void showLoading(boolean show, int contentID) {
        //ignored
    }

    @StateStrategyType(AddToEndSingleStrategy.class)
    default void showNoCancelableLoading(boolean show) {

    }

    @StateStrategyType(SkipStrategy.class)
    default void openScreen(int nextPage, Parcelable data) {
        //ignored
    }

    @StateStrategyType(SkipStrategy.class)
    default void showAcceptDialog(String content, @Nullable AcceptCallbackListener listener) {
        //ignored
    }

    @StateStrategyType(SkipStrategy.class)
    default void showAcceptDialog(@StringRes int content, @Nullable AcceptCallbackListener listener) {
        //ignored
    }


    @StateStrategyType(SkipStrategy.class)
    default void toPreviousScreen(@Nullable String msg) {

    }

    @StateStrategyType(SkipStrategy.class)
    default void toPreviousScreen(int msg) {

    }

    @StateStrategyType(SkipStrategy.class)
    default void closeScreen(@Nullable String msg) {

    }

    @StateStrategyType(SkipStrategy.class)
    default void showToast(String msg, ToastType type) {

    }

    @StateStrategyType(SkipStrategy.class)
    default void showToast(@StringRes int msg, ToastType type) {

    }

    @StateStrategyType(SkipStrategy.class)
    default void showInfoDialog(@StringRes int content) {

    }
}
