package com.substarry.customviewattribute;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 何凌 on 2016/7/14.
 */
public class CustomAttrView extends View {

    private String[] mStrings = new String[4];
    private Paint mPaint;
    private int mDefaultWidth = 0;
    private int mDefaultHeight = 0;
    private int mFontHeight;
    public CustomAttrView(Context context) {
        this(context, null);
    }

    public CustomAttrView(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
        this(context, attrs, R.attr.CustomAttrStyle);
    }

    public CustomAttrView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomAttr, defStyleAttr, R.style.DefaultCustomAttrViewStyle);
        String attr1 = a.getString(R.styleable.CustomAttr_attr_1);
        String attr2 = a.getString(R.styleable.CustomAttr_attr_2);
        String attr3 = a.getString(R.styleable.CustomAttr_attr_3);
        String attr4 = a.getString(R.styleable.CustomAttr_attr_4);
        a.recycle();

        mStrings[0] = new StringBuilder().append("attr 1: ")
                .append(attr1).toString() ;

        mStrings[1] = new StringBuilder().append("attr 2: ")
                .append(attr2).toString() ;

        mStrings[2] = new StringBuilder().append("attr 3: ")
                .append(attr3).toString() ;

        mStrings[3] = new StringBuilder().append("attr 4: ")
                .append(attr4).toString() ;

        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(40);
        mPaint.setAntiAlias(true);

        Rect bounds = new Rect();
        for(String string : mStrings){
            mPaint.getTextBounds(string, 0, string.length(), bounds);
            if(bounds.width() > mDefaultWidth){
                mDefaultWidth = bounds.width();
            }
        }

        Paint.FontMetrics fm = mPaint.getFontMetrics();
        mFontHeight = (int) (Math.ceil(fm.descent - fm.top) + 2);

        mDefaultHeight = mFontHeight * 4;


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if(widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(mDefaultWidth, mDefaultHeight);
        }
        else if(widthSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(mDefaultWidth, heightSpecSize);
        }
        else if(heightSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSpecSize, mDefaultHeight);
        }
        else{
            setMeasuredDimension(widthSpecSize, heightSpecSize);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i = 0; i < mStrings.length; i ++){
            canvas.drawText(mStrings[i], 0, mFontHeight * (i+1), mPaint);
        }


    }
}
