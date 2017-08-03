package com.cascade.animationview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int frameDuration = 100;
        int[] animationDrawableResources = new int[]{
                R.drawable.img_sequence_01,
                R.drawable.img_sequence_02,
                R.drawable.img_sequence_03,
                R.drawable.img_sequence_04,
                R.drawable.img_sequence_05,
                R.drawable.img_sequence_06,
                R.drawable.img_sequence_07,
        };

        AnimationDrawable animationDrawable = new AnimationDrawable();
        for (int animationDrawableResource : animationDrawableResources)
            animationDrawable.addFrame(ContextCompat.getDrawable(this, animationDrawableResource), frameDuration);

        AnimateView animateView = findViewById(R.id.av_animation);
        animateView.setAnimationDrawables(animationDrawableResources, false);
        animateView.start(frameDuration);

        ImageView imageView = findViewById(R.id.iv_animation);
        imageView.setImageDrawable(animationDrawable);
        animationDrawable.start();
    }
}