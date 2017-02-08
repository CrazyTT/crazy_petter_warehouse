package com.bjdv.lib.utils.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bjdv.lib.utils.R;
import com.bjdv.lib.utils.util.AppUtils;
import com.bjdv.lib.utils.util.DensityUtils;


/**
 * Title:
 * Desc:
 * Created by PHOON-THINK on 2015/5/21.
 * Copyright (c) 2015 DATANG BJDV
 */
public class AutoListView extends ListView implements OnScrollListener {

    private static final String TAG = "AutoListView";

    private final static int RELEASE_To_REFRESH = 0;
    private final static int PULL_To_REFRESH = 1;
    private final static int REFRESHING = 2;
    private final static int DONE = 3;
    private final static int LOADING = 4;

    private final static int RATIO = 6;

    private LayoutInflater inflater;

    private LinearLayout headView;

    private TextView tipsTextview;
    private TextView lastUpdatedTextView;
    private ImageView arrowImageView;
    private ProgressBar progressBar;

    private RotateAnimation animation;
    private RotateAnimation reverseAnimation;

    private boolean isRecored;

    private int headContentHeight;

    private int startY;
    private int firstItemIndex;

    private int state;

    private boolean isReversed;
    private OnRefreshListener refreshListener;
    private boolean isRefreshable;
    private boolean isLoading, isRefrshing;
    public boolean isLoadCompleted;
    private View footer;
    private TextView more;
    private ProgressBar loading;
    private Context mContext;

    public AutoListView(Context context) {
        super(context);
        init(context);
    }

    public AutoListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        setCacheColorHint(context.getResources().getColor(android.R.color.transparent));
        inflater = LayoutInflater.from(context);

        headView = (LinearLayout) inflater.inflate(R.layout.pull_to_refresh_header, null);

        arrowImageView = (ImageView) headView.findViewById(R.id.head_arrowImageView);
        progressBar = (ProgressBar) headView.findViewById(R.id.head_progressBar);
        tipsTextview = (TextView) headView.findViewById(R.id.head_tipsTextView);
        lastUpdatedTextView = (TextView) headView.findViewById(R.id.head_lastUpdatedTextView);

        // 底部布局
        footer = inflater.inflate(R.layout.pull_to_refresh_footer, null);
        more = (TextView) footer.findViewById(R.id.more);
        loading = (ProgressBar) footer.findViewById(R.id.loading);

        measureView(headView);
        headContentHeight = headView.getMeasuredHeight();
        headView.setPadding(0, -1 * headContentHeight, 0, 0);
        headView.invalidate();

        addHeaderView(headView, null, false);
        addFooterView(footer, null, false);
        setOnScrollListener(this);

        animation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(250);
        animation.setFillAfter(true);

        reverseAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        reverseAnimation.setInterpolator(new LinearInterpolator());
        reverseAnimation.setDuration(200);
        reverseAnimation.setFillAfter(true);

        state = DONE;
        isRefreshable = false;
    }

    public void onScroll(AbsListView view, int firstVisiableItem, int visibleItemCount,
                         int totalItemCount) {
        firstItemIndex = firstVisiableItem;
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        isNeedLoad(view, scrollState);
    }

    private void isNeedLoad(AbsListView view, int scrollState) {
        try {
            if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && view.getLastVisiblePosition() == view.getPositionForView(footer) && !isLoading && !isLoadCompleted && !isRefrshing) {
                onLoad();
            }
        } catch (Exception e) {
        }
    }

    public boolean onTouchEvent(MotionEvent event) {

        if (isRefreshable) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (firstItemIndex == 0 && !isRecored && !isLoading) {
                        isRecored = true;
                        startY = (int) event.getY();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (state == PULL_To_REFRESH) {
                        state = DONE;
                        changeHeaderViewByState();
                    } else if (state == RELEASE_To_REFRESH) {
                        state = REFRESHING;
                        changeHeaderViewByState();
                        onRefresh();
                    }
                    isRecored = false;
                    isReversed = false;
                    break;

                case MotionEvent.ACTION_MOVE:
                    handleMove(event);
                    break;
            }
        }

        return super.onTouchEvent(event);
    }

    private void handleMove(MotionEvent event) {

        int tempY = (int) event.getY();
        if (!isRecored && firstItemIndex == 0) {
            isRecored = true;
            startY = tempY;
        }
        int distance = (tempY - startY) / 3;
        if (state != REFRESHING && isRecored) {
            switch (state) {
                case LOADING:
                case DONE:
                    if (distance > 0) {
                        state = PULL_To_REFRESH;
                        changeHeaderViewByState();
                    }
                    break;
                case PULL_To_REFRESH:
                    refreshHeader(distance);
                    setSelection(0);
                    if ((distance < headContentHeight) && distance > 0) {
                        state = PULL_To_REFRESH;
                        changeHeaderViewByState();
                    } else if (distance >= headContentHeight) {
                        state = RELEASE_To_REFRESH;
                        changeHeaderViewByState();
                    } else if (distance <= 0) {
                        state = DONE;
                        changeHeaderViewByState();
                    }
                    break;
                case RELEASE_To_REFRESH:
                    refreshHeader(distance);
                    setSelection(0);
                    if ((distance < headContentHeight) && distance > 0) {
                        isReversed = true;
                        state = PULL_To_REFRESH;
                        changeHeaderViewByState();
                    } else if (tempY - startY <= 0) {
                        state = DONE;
                        changeHeaderViewByState();
                    }
                    break;
            }

        }


    }

    private void refreshHeader(int distance) {
        int padding = distance - headContentHeight;
        if (padding > 0) padding = padding;
        headView.setPadding(0, padding, 0, 0);
    }

    private void changeFooterView() {
        if (isLoading) {
            loading.setVisibility(View.VISIBLE);
            more.setVisibility(View.VISIBLE);
            more.setText("正在加载...");
        }
        if (isLoadCompleted) {
            loading.setVisibility(View.GONE);
            more.setText("已经全部加载完毕");
            more.setVisibility(View.VISIBLE);
        } else {
            more.setVisibility(View.GONE);
        }
    }

    public void hideLoading() {
        loading.setVisibility(View.GONE);
    }

    private void changeHeaderViewByState() {
        switch (state) {
            case RELEASE_To_REFRESH:
                arrowImageView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                tipsTextview.setVisibility(View.VISIBLE);
                lastUpdatedTextView.setVisibility(View.VISIBLE);

                arrowImageView.clearAnimation();
                arrowImageView.startAnimation(animation);
                tipsTextview.setText("松开刷新");
                break;
            case PULL_To_REFRESH:
                progressBar.setVisibility(View.GONE);
                tipsTextview.setVisibility(View.VISIBLE);
                lastUpdatedTextView.setVisibility(View.VISIBLE);
                if (isReversed) {
                    isReversed = false;
                    arrowImageView.clearAnimation();
                    arrowImageView.startAnimation(reverseAnimation);
                    tipsTextview.setText("下拉刷新");
                } else {
                    tipsTextview.setText("下拉刷新");
                }
                break;

            case REFRESHING:
                headView.setPadding(0, 0, 0, 0);
                progressBar.setVisibility(View.VISIBLE);
                arrowImageView.clearAnimation();
                arrowImageView.setVisibility(View.GONE);
                tipsTextview.setText("正在刷新...");
                lastUpdatedTextView.setVisibility(View.VISIBLE);
                break;
            case DONE:
                headView.setPadding(0, -1 * headContentHeight, 0, 0);
                progressBar.setVisibility(View.GONE);
                arrowImageView.clearAnimation();
                tipsTextview.setText("下拉刷新");
                lastUpdatedTextView.setVisibility(View.GONE);

                break;
        }
    }

    public void setOnRefreshListener(OnRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
        isRefreshable = true;
    }

    public interface OnRefreshListener {
        public void onRefresh();

        public void onLoad();
    }

    public void onRefreshComplete() {
        this.isLoadCompleted = false;
        state = DONE;
        isRefrshing = false;
        lastUpdatedTextView.setText("最近更新:" + AppUtils.getCurrentTime());
        changeHeaderViewByState();
        changeFooterView();
    }

    public void onLoadComplete(boolean isLoadCompleted) {
        this.isLoadCompleted = isLoadCompleted;
        isLoading = false;
        changeFooterView();
    }

    public void onRefresh() {
        loadAnmation();
        /*isRefrshing=true;
        state = REFRESHING;
        changeHeaderViewByState();
        if (refreshListener != null) {
            refreshListener.onRefresh();
        }*/
    }

    private void loadAnmation() {
        Animation translateAnimation = new TranslateAnimation(0, 0, 0, DensityUtils.dip2px(mContext, 5));
        translateAnimation.setInterpolator(new OvershootInterpolator());
        translateAnimation.setDuration(100);
        startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isRefrshing = true;
                state = REFRESHING;
                changeHeaderViewByState();
                if (refreshListener != null) {
                    refreshListener.onRefresh();
                }
            }
        });
    }

    public void onLoad() {
        isLoading = true;
        changeFooterView();
        if (refreshListener != null) {
            refreshListener.onLoad();
        }
    }


    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    public void setAdapter(BaseAdapter adapter) {
        lastUpdatedTextView.setText("最近更新:" + AppUtils.getCurrentTime());
        super.setAdapter(adapter);
    }

}
