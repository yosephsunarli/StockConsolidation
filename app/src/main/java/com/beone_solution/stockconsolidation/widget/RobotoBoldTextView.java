package com.beone_solution.stockconsolidation.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class RobotoBoldTextView extends android.support.v7.widget.AppCompatTextView {

    public RobotoBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont(getContext(), this);
    }

    public RobotoBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont(getContext(), this);
    }

    public RobotoBoldTextView(Context context) {
        super(context);
        setFont(getContext(), this);
    }

    private static void setFont(Context context, TextView tv) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "Roboto-Bold.ttf");
        tv.setTypeface(font);
    }
}
