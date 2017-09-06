/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.chengguo.waterpullview;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

public class SwipeWaterRefreshLayout extends ViewGroup {

    private static final String LOG_TAG = SwipeRefreshLayout.class.getSimpleName();

    private PullView mPullView;

    OnRefreshListener mListener;
    boolean mRefreshing = false;

    private int mCircleViewIndex = -1;

    private static final float TOUCH_MOVE_MAX_Y = 600;
    private float mTouchMoveStartY = 0;

    public SwipeWaterRefreshLayout(Context context) {
        this(context, null);
    }

    public SwipeWaterRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        setWillNotDraw(false);
        //添加下拉刷新的View
        mPullView = new PullView(context);
        addView(mPullView);

        ViewCompat.setChildrenDrawingOrderEnabled(this, true);
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        if (mCircleViewIndex < 0) {
            return i;
        } else if (i == childCount - 1) {
            return mCircleViewIndex;
        } else if (i >= mCircleViewIndex) {
            return i + 1;
        } else {
            return i;
        }
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        mListener = listener;
    }

    public boolean isRefreshing() {
        return mRefreshing;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();
        if (getChildCount() == 0) {
            return;
        }
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mTouchMoveStartY = motionEvent.getY();
                return true;

            case MotionEvent.ACTION_MOVE:
                float y = motionEvent.getY();
                if (y > mTouchMoveStartY) {
                    float moveSize = y - mTouchMoveStartY;
                    float progress = moveSize >= TOUCH_MOVE_MAX_Y ? 1 : moveSize / TOUCH_MOVE_MAX_Y;
                    mPullView.setProgress(progress);
                }
                break;
            case MotionEvent.ACTION_UP:
                mPullView.release();
                break;
        }
        return false;
    }

    interface OnRefreshListener {
        void onRefresh();
    }
}
