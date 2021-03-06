package com.neurospeech.hypercube.ui;


import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;

import java.util.HashMap;

/**
 * Created by  on 12/4/2015.
 */
public class AnimatedCircleDrawable extends AnimationDrawable {

    private int bg;
    private int color;
    private int size;

    private static HashMap<String,CircleDrawable> cache =
            new HashMap<>();

    public AnimatedCircleDrawable(int color, float strokeWidth, int bg, int size) {
        super();

        for(int i=0;i<36;i++){

            String key = String.valueOf(i*10) + String.valueOf(color) + String.valueOf(strokeWidth)
                    + String.valueOf(bg) + String.valueOf(size);

            CircleDrawable cd = cache.get(key);
            if(cd==null){
                cd = new CircleDrawable(i*10,color,strokeWidth,bg,size);
                cache.put(key,cd);
            }
            addFrame(cd,10);
        }

        this.size = size;
    }

    @Override
    public int getIntrinsicWidth() {
        return size;
    }

    @Override
    public int getIntrinsicHeight() {
        return size;
    }

    public static class CircleDrawable extends Drawable{

        private int angle;
        private int size;
        Paint stroke;
        Paint background;

        public CircleDrawable(
                int angle,
                int color,
                float strokeWidth,
                int bg,
                int size) {
            super();

            this.size = size;

            stroke = new Paint();
            stroke.setStyle(Paint.Style.STROKE);
            stroke.setStrokeWidth(strokeWidth);
            stroke.setAntiAlias(true);
            stroke.setColor(color);

            background = new Paint();
            background.setColor(bg);
            background.setStyle(Paint.Style.FILL);

            this.angle = angle;
        }

        @Override
        public void draw(Canvas canvas) {
            //int s = canvas.save();

            RectF rect = new RectF(0,0,canvas.getWidth(),canvas.getHeight());
            float left = (rect.width()-size)/2;
            float top = (rect.height()-size)/2;
            RectF arcRect = new RectF(left,top,left + size,top + size);

            // draw background...
            canvas.drawRect(rect,background);

            canvas.drawArc(arcRect,angle,270,false,stroke);

            //canvas.restoreToCount(s);



        }

        @Override
        public void setAlpha(int alpha) {

        }

        @Override
        public void setColorFilter(ColorFilter colorFilter) {

        }

        @Override
        public int getOpacity() {
            return 0;
        }
    }
}
