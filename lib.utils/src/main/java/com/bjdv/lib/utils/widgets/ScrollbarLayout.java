package com.bjdv.lib.utils.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.bjdv.lib.utils.R;


/**
 * Title: 自定义Layout<br>
 * Description:  实现带bar的跟随动画<br>
 * Date: 2015-03-09 <br>
 * Copyright (c) 2014 DATANG BJDV<br>
 * 
 * @author PHOON-THINK
 */

@SuppressLint("DrawAllocation")
public class ScrollbarLayout extends LinearLayout {
    private static final String TAG = "ScrollbarLayout";
    private final int childCount;
    private Paint mPaint; // 画指示符的paint
	
	private int mTop; // 指示符的top
	private int mLeft; // 指示符的left
	private int mWidth; // 指示符的width
	private int mHeight = 3; // 指示符的高度，固定了
	private int height = 0; //
	private int mColor; // 指示符的颜色
	private int mChildCount; // 子item的个数，用于计算指示符的宽度
	private float density;
    private int customCount;


    public ScrollbarLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
//		setBackgroundColor(Color.TRANSPARENT);  // 必须设置背景，否则onDraw不执行
		
		// 获取自定义属性 指示符的颜色
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Indicator, 0, 0);
		mColor = ta.getColor(R.styleable.Indicator_barcolor, 0X0000FF);
        childCount=ta.getInt(R.styleable.Indicator_childCount, 0);
		ta.recycle();
		density=context.getResources().getDisplayMetrics().density;
		// 初始化paint
		mPaint = new Paint();
		mPaint.setColor(mColor);
		mPaint.setAntiAlias(true);
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mChildCount = getChildCount();  // 获取子item的个数
        Log.e(TAG,"mChildCount="+mChildCount);
        if(childCount>0)mChildCount=childCount;
        Log.e(TAG, "mChildCount=" + mChildCount);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int childCount=getChildCount();
		mChildCount=0;
		for(int i=0;i<childCount;i++){
			if(getChildAt(i).getVisibility()== View.VISIBLE){
				mChildCount+=1;
			}
		}

		int width = getMeasuredWidth(); // 获取测量的总宽度
		int height = getMeasuredHeight(); // 重新定义一下测量的高度
		mWidth = width / mChildCount; // 指示符的宽度为总宽度/item的个数
		this.height=height;
		setMeasuredDimension(width, height);
	}
	
	/**
	 * 指示符滚动
	 * @param position 现在的位置
	 * @param offset  偏移量 0 ~ 1
	 */
	public void scroll(int position, float offset) {
		mLeft = (int) ((position + offset) * mWidth);
		invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// 圈出一个矩形
		Rect rect = new Rect((int) (mLeft+10*density), (int) (height-mHeight*density), (int) (mLeft + mWidth-10*density), (int) height);
		canvas.drawRect(rect, mPaint); // 绘制该矩形
		super.onDraw(canvas);
	}

    public int getCustomCount() {
        return customCount;
    }

    public void setCustomCount(int customCount) {
        this.customCount = customCount;
    }
}
