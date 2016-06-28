package view.library.refresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.view.ViewCompat;
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
public class ClassicNoPullDownRecyclerView extends FrameLayout implements View.OnClickListener, ISwipeToLoadListener {

    private ImageView mIvLoading;
    private RecyclerView mSwipeTarget;
    private SwipeToLoadLayout mSwipeToLoadLayout;
    private FrameLayout mFlLoading;

    private AnimationDrawable mAnimDrawable;
    private IPullRefreshListenerI mRefreshListener;
    private boolean animation;
    private boolean autoRefresh;

    public void setPullRefreshListener(IPullRefreshListenerI refreshListener) {
        this.mRefreshListener = refreshListener;
    }

    public ClassicNoPullDownRecyclerView(Context context) {
        this(context, null);
    }

    public ClassicNoPullDownRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClassicNoPullDownRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ClassicAllRecyclerView);
        animation = a.getBoolean(R.styleable.ClassicAllRecyclerView_loadAnimation, true);
        autoRefresh = a.getBoolean(R.styleable.ClassicAllRecyclerView_autoRefresh, true);
        a.recycle();
        setupView(context);
    }

    private void setupView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_no_pull_down_recyclerview, this);
        mIvLoading = (ImageView) findViewById(R.id.iv_loading);
        mSwipeTarget = (RecyclerView) findViewById(R.id.swipe_target);
        mSwipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        mFlLoading = (FrameLayout) findViewById(R.id.fl_loading);
        init();
    }

    private void init() {
        initAllListener();//下拉刷新，上拉加载，是否自动刷新的监听
        mIvLoading.setOnClickListener(this);//加载失败点击回调
        initRecyclerView();//初始化RecyclerView以及监听
        initLoadAnimation();//初始化动画
    }

    private void initAllListener() {
        mSwipeToLoadLayout.setOnRefreshListener(this);
        mSwipeToLoadLayout.setOnLoadMoreListener(this);
        mSwipeToLoadLayout.setRefreshing(autoRefresh);
    }

    private void initRecyclerView() {
        mSwipeTarget.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!ViewCompat.canScrollVertically(recyclerView, 1)) {
                        mSwipeToLoadLayout.setLoadingMore(true);
                    }
                }
            }
        });
        mSwipeTarget.setLayoutManager(new LinearLayoutManager(getContext()));
        mSwipeTarget.setHasFixedSize(true);
        mSwipeTarget.setItemAnimator(new DefaultItemAnimator());
    }

    private void initLoadAnimation() {
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

    public void setAdapter(RecyclerView.Adapter adapter) {
        mSwipeTarget.setAdapter(adapter);
    }

    public void onCompleteRefresh(String action) {
        mSwipeToLoadLayout.setRefreshing(false);
        mSwipeToLoadLayout.setLoadingMore(false);
        switch (action) {
            case ClassicConstant.refreshComplete:
                onLoadComplete();
                break;
            case ClassicConstant.loadMoreComplete:
                onLoadComplete();
                break;
            case ClassicConstant.onFailedComplete:
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
            if (mRefreshListener == null) {
                return;
            }
            mIvLoading.setBackgroundDrawable(getResources().getDrawable(R.drawable.loading_animation));
            mAnimDrawable = (AnimationDrawable) mIvLoading.getBackground();
            mAnimDrawable.start();
            mRefreshListener.onReload();
        }
    }

    @Override
    public void onLoadMore() {
        if (mRefreshListener == null) {
            return;
        }
        mRefreshListener.onLoadMore();
    }

    @Override
    public void onRefresh() {
        if (mRefreshListener == null) {
            return;
        }
        mRefreshListener.onRefresh();
    }

}
