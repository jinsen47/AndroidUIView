package me.drakeet.library;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by drakeet on 4/2/15.
 */
public class UIBaseButton extends Button {

    public UIBaseButton(Context context) {
        super(context);
    }

    public UIBaseButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UIBaseButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public UIBaseButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


}
