/*
 *  Owlsight OwlSightProgressIndicator
 *  Created by frostik0409@gmail.com
 *  Kirill Stulnikov (Woipot)
 *  on 29.05.20 16:56
 *
 *  Copyright © 2019 Petr Baev. All rights reserved.
 *  Last modified 29.05.20 16:10
 */

package com.aqulasoft.fireman.mobile.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import androidx.core.content.res.ResourcesCompat;

import com.aqulasoft.fireman.mobile.R;

public class FiremanProgressIndicator extends ProgressBar {

    private int mCenterColor = ResourcesCompat.getColor(getResources(), R.color.colorWindowBackground, null);

    public FiremanProgressIndicator(Context context) {
        super(context);
        setIntermediate();
    }

    public FiremanProgressIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttrs(attrs);
    }

    public FiremanProgressIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttrs(attrs);
    }

    public FiremanProgressIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        parseAttrs(attrs);
    }

    public void setColor(int color) {
        mCenterColor = color;
    }


    private void parseAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs,
                R.styleable.FiremanProgressIndicator, 0, 0);
        setIntermediate();
        try {
            setColor(a.getColor(R.styleable.FiremanProgressIndicator_color, mCenterColor));
        } finally {
            a.recycle();
        }
    }

    private void setIntermediate() {
        setIndeterminateDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_progress_intermediate, null));
    }
}
