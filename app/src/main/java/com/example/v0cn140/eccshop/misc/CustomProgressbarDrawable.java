package com.example.v0cn140.eccshop.misc;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

public class CustomProgressbarDrawable extends Drawable {

    private ImageDownloadListener mListener;

    public CustomProgressbarDrawable(ImageDownloadListener listener) {
        mListener = listener;
    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }

    @Override
    protected boolean onLevelChange(int level) {
        int progress = (int) ((level / 10000.0) * 100);
        if (mListener != null) {
            mListener.onUpdate(progress);
        }
        return super.onLevelChange(level);
    }
}