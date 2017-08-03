package com.cascade.animationview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;


import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sinansondas on 02/05/17.
 */

public class AnimateView extends View {
    private static final int DEFAULT_FRAME_RATE = 20;

    private boolean startOnAttached = true;
    private int targetWidth = 1;
    private int targetHeight = 1;
    private int currentDrawableIndex = 0;

    private int[] animationDrawableResources;
    private Timer timer;
    private Bitmap targetDrawableBitmap = null;

    public AnimateView(Context context) {
        super(context);
        initialize();
    }

    public AnimateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public AnimateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                targetWidth = getWidth();
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (startOnAttached)
            start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        stop();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (targetDrawableBitmap != null)
            canvas.drawBitmap(targetDrawableBitmap, 0, 0, null);

        getLayoutParams().height = targetHeight;
        requestLayout();
    }

    private void start() {
        start(DEFAULT_FRAME_RATE);
    }

    private void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void start(int duration) {
        if (animationDrawableResources != null && animationDrawableResources.length > 1) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (currentDrawableIndex == animationDrawableResources.length - 1)
                        currentDrawableIndex = -1;
                    currentDrawableIndex++;

                    Bitmap currentBitmap = BitmapFactory.decodeResource(getResources(), animationDrawableResources[currentDrawableIndex]);
                    int bitmapWidth = currentBitmap.getWidth();
                    int bitmapHeight = currentBitmap.getHeight();

                    double scale = bitmapWidth / (double) bitmapHeight;
                    targetHeight = (int) (targetWidth / scale);

                    if (targetHeight < 1)
                        targetHeight = 1;

                    if (targetWidth < 1)
                        targetWidth = 1;

                    targetDrawableBitmap = Bitmap.createScaledBitmap(currentBitmap, targetWidth, targetHeight, true);
                }
            }, 0, duration);
        }
    }

    public void setAnimationDrawables(int[] animationDrawableResources, boolean startOnAttached) {
        this.animationDrawableResources = animationDrawableResources;
        this.startOnAttached = startOnAttached;
    }
}