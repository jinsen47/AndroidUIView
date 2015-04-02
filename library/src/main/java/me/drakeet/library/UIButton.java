/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 drakeet (http://drakeet.me)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package me.drakeet.library;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by drakeet on 3/27/15.
 */
public class UIButton extends Button {

    private int WIDTH;
    private int HEIGHT;
    private int COVER_ALPHA = 48;

    private int mPressedColor;
    private Paint mPaint;
    private Paint mBackgroundPaint;
    private int mShapeType;
    private int mRadius;

    public UIButton(Context context) {
        super(context);
    }

    public UIButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public UIButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public UIButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(final Context context, final AttributeSet attrs) {
        if (isInEditMode())
            return;
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UIButton);
        mPressedColor = typedArray.getColor(R.styleable.UIButton_color_pressed, getResources().getColor(R.color.color_pressed));
        COVER_ALPHA = typedArray.getInteger(R.styleable.UIButton_alpha_pressed, COVER_ALPHA);
        mShapeType = typedArray.getInt(R.styleable.UIButton_shape_type, 1);
        mRadius = typedArray.getDimensionPixelSize(R.styleable.UIButton_radius, getResources().getDimensionPixelSize(R.dimen.ui_radius));
        int unpressedColor = typedArray.getColor(R.styleable.UIButton_color_unpressed, getResources().getColor(R.color.transparent));
        typedArray.recycle();

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mPressedColor);
        mPaint.setAlpha(0);
        mPaint.setAntiAlias(true);

        mBackgroundPaint = new Paint(mPaint);
        mBackgroundPaint.setAlpha(Color.alpha(unpressedColor));
        mBackgroundPaint.setColor(unpressedColor);

        this.setWillNotDraw(false);
        this.setDrawingCacheEnabled(true);
        this.setClickable(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        WIDTH = w;
        HEIGHT = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mPaint == null || mBackgroundPaint == null) {
            super.onDraw(canvas);
            return;
        }
        if (mShapeType == 0){
            canvas.drawCircle(WIDTH/2, HEIGHT/2, WIDTH/2, mBackgroundPaint);
        }else{
            RectF rectF = new RectF();
            rectF.set(0, 0, WIDTH, HEIGHT);
            canvas.drawRoundRect(rectF, mRadius, mRadius, mBackgroundPaint);
        }
        super.onDraw(canvas);
        if (mShapeType == 0) {
            canvas.drawCircle(WIDTH/2, HEIGHT/2, WIDTH/2.1038f, mPaint);
        } else {
            RectF rectF = new RectF();
            rectF.set(0, 0, WIDTH, HEIGHT);
            canvas.drawRoundRect(rectF, mRadius, mRadius, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPaint.setAlpha(COVER_ALPHA);
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mPaint.setAlpha(0);
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

}
