package view.library.refresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import butterknife.ButterKnife;
import view.library.R;

/**
 * PullRecyclerViewLayout
 * Created by smileCloud on 16/6/21.
 */
public class PullRecyclerViewLayout extends FrameLayout {

    ImageView mIvLoading;
    RecyclerView mSwipeTarget;
    SwipeToLoadLayout mSwipeToLoadLayout;
    FrameLayout mFlLoading;

    private AnimationDrawable mAnimDrawable;
    private boolean animation;
    private boolean autoRefresh;

    public PullRecyclerViewLayout(Context context) {
        this(context, null);
    }

    public PullRecyclerViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullRecyclerViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PullRecyclerViewLayout);
        animation = a.getBoolean(R.styleable.PullRecyclerViewLayout_loadAnimation, true);
        autoRefresh = a.getBoolean(R.styleable.PullRecyclerViewLayout_autoRefresh, true);
        a.recycle();
        setupView(context);
    }

    private void setupView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_pull_recyclerview, this);
        mIvLoading = (ImageView) findViewById(R.id.iv_loading);
        mSwipeTarget = (RecyclerView) findViewById(R.id.swipe_target);
        mSwipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        mFlLoading = (FrameLayout) findViewById(R.id.fl_loading);
        ButterKnife.bind(this);
        initLoadAnimation();
    }

    private void initLoadAnimation() {
        mSwipeToLoadLayout.setRefreshing(autoRefresh);
        mAnimDrawable = (AnimationDrawable) mIvLoading.getBackground();
        if (animation) {
            mAnimDrawable.start();
        } else {
            onLoadComplete();
        }
    }

    private void onLoadComplete() {
        mFlLoading.setBackgroundColor(Color.parseColor("#FFFFFF"));
        mIvLoading.setVisibility(View.GONE);
        mSwipeToLoadLayout.setVisibility(View.VISIBLE);
        mAnimDrawable.stop();
    }

    public void setPullListener(PullRefreshListener listener) {
        mSwipeToLoadLayout.setOnRefreshListener(listener);
        mSwipeToLoadLayout.setOnLoadMoreListener(listener);
    }

    public void setRefreshingComplete(boolean refreshingComplete) {
        onLoadComplete();
        mSwipeToLoadLayout.setRefreshing(refreshingComplete);
    }

    public void setLoadingMoreComplete(boolean loadingMoreComplete) {
        onLoadComplete();
        mSwipeToLoadLayout.setLoadingMore(loadingMoreComplete);
    }

    public RecyclerView initRecyclerView() {
        mSwipeTarget.setLayoutManager(new LinearLayoutManager(getContext()));
        mSwipeTarget.setHasFixedSize(true);
        mSwipeTarget.setItemAnimator(new DefaultItemAnimator());
        return mSwipeTarget;
    }
}
