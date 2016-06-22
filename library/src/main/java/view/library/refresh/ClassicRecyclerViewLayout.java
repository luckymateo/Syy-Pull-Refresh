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

import view.library.R;

/**
 * PullRecyclerViewLayout
 * Created by smileCloud on 16/6/21.
 */
public class ClassicRecyclerViewLayout extends FrameLayout implements View.OnClickListener {

    private ImageView mIvLoading;
    private RecyclerView mSwipeTarget;
    private SwipeToLoadLayout mSwipeToLoadLayout;
    private FrameLayout mFlLoading;

    private AnimationDrawable mAnimDrawable;
    private boolean animation;
    private boolean autoRefresh;
    private PullRefreshListener refreshFailed;

    public void setRefreshFailed(PullRefreshListener refreshFailed) {
        this.refreshFailed = refreshFailed;
    }

    public ClassicRecyclerViewLayout(Context context) {
        this(context, null);
    }

    public ClassicRecyclerViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClassicRecyclerViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ClassicRecyclerViewLayout);
        animation = a.getBoolean(R.styleable.ClassicRecyclerViewLayout_loadAnimation, true);
        autoRefresh = a.getBoolean(R.styleable.ClassicRecyclerViewLayout_autoRefresh, true);
        a.recycle();
        setupView(context);
    }

    private void setupView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_pull_recyclerview, this);
        mIvLoading = (ImageView) findViewById(R.id.iv_loading);
        mIvLoading.setOnClickListener(this);
        mSwipeTarget = (RecyclerView) findViewById(R.id.swipe_target);
        mSwipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        mFlLoading = (FrameLayout) findViewById(R.id.fl_loading);
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
        if (mIvLoading.getVisibility() == GONE) {
            return;
        }
        mFlLoading.setBackgroundColor(Color.parseColor("#FFFFFF"));
        mIvLoading.setVisibility(View.GONE);
        mSwipeToLoadLayout.setVisibility(View.VISIBLE);
        mAnimDrawable.stop();
    }

    private void setRefreshingComplete(boolean refreshingComplete) {
        onLoadComplete();
        mSwipeToLoadLayout.setRefreshing(refreshingComplete);
    }

    private void setLoadingMoreComplete(boolean loadingMoreComplete) {
        onLoadComplete();
        mSwipeToLoadLayout.setLoadingMore(loadingMoreComplete);
    }

    public RecyclerView initRecyclerView() {
        mSwipeTarget.setLayoutManager(new LinearLayoutManager(getContext()));
        mSwipeTarget.setHasFixedSize(true);
        mSwipeTarget.setItemAnimator(new DefaultItemAnimator());
        return mSwipeTarget;
    }

    public void setPullListener(PullRefreshListener listener) {
        mSwipeToLoadLayout.setOnRefreshListener(listener);
        mSwipeToLoadLayout.setOnLoadMoreListener(listener);
    }

    public void onCompleteRefresh(String action) {
        switch (action) {
            case ClassicConstant.loadMoreComplete:
                setLoadingMoreComplete(false);
                break;
            case ClassicConstant.refreshComplete:
                setRefreshingComplete(false);
                break;
            case ClassicConstant.onFailedComplete:
                mSwipeToLoadLayout.setRefreshing(false);
                if (mIvLoading.getVisibility() != GONE) {
                    mIvLoading.setBackgroundDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        int itemId = v.getId();
        if (itemId == R.id.iv_loading) {
            if (refreshFailed == null) {
                return;
            }
            mIvLoading.setBackgroundDrawable(getResources().getDrawable(R.drawable.loading_animation));
            mAnimDrawable = (AnimationDrawable) mIvLoading.getBackground();
            mAnimDrawable.start();
            refreshFailed.onReload();
        }
    }
}
