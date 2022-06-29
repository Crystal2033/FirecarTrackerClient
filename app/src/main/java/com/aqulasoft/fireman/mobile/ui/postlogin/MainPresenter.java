/*
 *  Owlsight MainPresenter
 *  Created by frostik0409@gmail.com
 *  Kirill Stulnikov (Woipot)
 *  on 02.06.21 11:49
 *
 *  Copyright © 2019 Petr Baev. All rights reserved.
 *  Last modified 02.06.21 11:49
 */

package com.aqulasoft.fireman.mobile.ui.postlogin;

import com.aqulasoft.fireman.mobile.ui.base.BasePresenter;

import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class MainPresenter extends BasePresenter<MainView> {

    @Override
    public void detachView(MainView view) {
        super.detachView(view);
        clearDisposable();
    }

    ///////////////////////////////////////////////////////////////////////////
    //                              api
    ///////////////////////////////////////////////////////////////////////////

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

}
