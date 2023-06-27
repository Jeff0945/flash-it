package com.coretech.flashit;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class CenterLayoutManager extends LinearLayoutManager {

    public CenterLayoutManager(Context context) {
        super(context);
    }

    public CenterLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public CenterLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void calculateItemDecorationsForChild(View child, Rect outRect) {
        super.calculateItemDecorationsForChild(child, outRect);
        int leftPadding = (getWidth() - child.getWidth()) / 2;
        outRect.left += leftPadding;
        outRect.right += leftPadding;
    }
}
