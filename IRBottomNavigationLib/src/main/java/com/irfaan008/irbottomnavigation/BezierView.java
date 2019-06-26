/*
 * Space Navigation library for Android
 * Copyright (c) 2016 Arman Chatikyan (https://github.com/armcha/Space-Navigation-View).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.irfaan008.irbottomnavigation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.content.ContextCompat;
import android.widget.RelativeLayout;

@SuppressLint("ViewConstructor")
class BezierView extends RelativeLayout {

    private Paint paint;

    private Paint strokePaint;

    private Path path;

    private int bezierWidth, bezierHeight, totalWidth, firstPoint, secondPoint;

    private int backgroundColor;

    private Context context;

    private boolean isLinear=false;


    BezierView(Context context, int backgroundColor) {
        super(context);
        this.context = context;
        this.backgroundColor = backgroundColor;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        path = new Path();
        paint.setStrokeWidth(0);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        strokePaint.setStrokeWidth(3);
        strokePaint.setAntiAlias(true);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setColor(Color.GRAY);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setBackgroundColor(ContextCompat.getColor(context, R.color.space_transparent));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        totalWidth = w;
        int halfBar = (totalWidth / 2);
        firstPoint = halfBar - (bezierWidth / 2);
        secondPoint = firstPoint + bezierWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        /**
         * Set paint color to fill view
         */
        paint.setColor(backgroundColor);

        /**
         * Reset path before drawing
         */
        path.reset();

        /**
         * Start point for drawing
         */
        path.moveTo(0, bezierHeight);


        if(!isLinear){
            float quarterBezier = (float) (bezierWidth / 4) + firstPoint;
            float halfBezier = (float) (bezierWidth / 2) + firstPoint;
            float secondBezier = (float) ((bezierWidth / 4) * 3) + firstPoint;

            path.lineTo((float) firstPoint, bezierHeight);
            path.cubicTo(quarterBezier, bezierHeight, quarterBezier, 5, halfBezier, 5);
            path.cubicTo(secondBezier, 5, secondBezier, bezierHeight, secondPoint, bezierHeight);
            path.lineTo((float) totalWidth, bezierHeight);
        }


        /**
         * Draw our bezier view
         */
        canvas.drawPath(path, paint);
        canvas.drawPath(path, strokePaint);
    }

    /**
     * Build bezier view with given width and height
     *
     * @param bezierWidth  Given width
     * @param bezierHeight Given height
     * @param isLinear True, if curves are not needed
     */
    void build(int bezierWidth, int bezierHeight,boolean isLinear) {
        this.bezierWidth = bezierWidth;
        this.bezierHeight = bezierHeight;
        this.isLinear=isLinear;
    }

    /**
     * Change bezier view background color
     *
     * @param backgroundColor Target color
     */
    void changeBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        invalidate();
    }

    void changeOutlineColor(int outlineColor) {
        this.strokePaint.setColor(outlineColor);
        invalidate();
    }
}

