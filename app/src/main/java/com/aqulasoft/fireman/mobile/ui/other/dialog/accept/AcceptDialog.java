/*
 *  Owlsight AcceptDialog
 *  Created by frostik0409@gmail.com
 *  Kirill Stulnikov (Woipot)
 *  on 11.02.21 1:37
 *
 *  Copyright © 2019 Petr Baev. All rights reserved.
 *  Last modified 11.02.21 1:36
 */

package com.aqulasoft.fireman.mobile.ui.other.dialog.accept;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aqulasoft.fireman.mobile.App;
import com.aqulasoft.fireman.mobile.R;
import com.aqulasoft.fireman.mobile.databinding.DialogAcceptBinding;
import com.aqulasoft.fireman.mobile.ui.base.BaseDialogFragment;

public class AcceptDialog extends BaseDialogFragment<DialogAcceptBinding> {

    public static final String TAG = "OWLSIGHT_ACCEPT_DIALOG";

    private final AcceptCallbackListener mListener;
    private final String mContent;
    private boolean mIsInfo;

    public AcceptDialog(String content, @Nullable AcceptCallbackListener listener) {
        mContent = content;
        mListener = listener;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.acceptDialogContent.setText(mContent);
        if (mIsInfo) {
            mBinding.acceptDialogButtonYes.setVisibility(View.GONE);
            mBinding.acceptDialogButtonNo.setText(R.string.ok);
        }

        mBinding.acceptDialogButtonYes.setOnClickListener(this::onYesClicked);
        mBinding.acceptDialogButtonNo.setOnClickListener(this::onNoClicked);
    }

    public void makeInfoDialog() {
        mIsInfo = true;
    }

    ///////////////////////////////////////////////////////////////////////////
    //                          butter knife
    ///////////////////////////////////////////////////////////////////////////

    public void onYesClicked(View view) {
        onButtonClicked(DialogResults.YES);
    }

    public void onNoClicked(View view) {
        onButtonClicked(DialogResults.NO);
    }

    ///////////////////////////////////////////////////////////////////////////
    //                          private
    ///////////////////////////////////////////////////////////////////////////

    private void notifyListener(DialogResults result) {
        if (mListener != null)
            mListener.onAlertResult(result);
    }

    private void onButtonClicked(DialogResults result) {
        notifyListener(result);

        Log.d(App.DEBUG_TAG, "onViewClicked in Accept dialog: ");
        dismiss();
    }

    ///////////////////////////////////////////////////////////////////////////
    //                          abstract
    ///////////////////////////////////////////////////////////////////////////

    @Override
    protected Class<DialogAcceptBinding> getBindingClass() {
        return DialogAcceptBinding.class;
    }

    ///////////////////////////////////////////////////////////////////////////
    //                          enums
    ///////////////////////////////////////////////////////////////////////////

    public enum DialogResults {
        YES,
        NO
    }

}
