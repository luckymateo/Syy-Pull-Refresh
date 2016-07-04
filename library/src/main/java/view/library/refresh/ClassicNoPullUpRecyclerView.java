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
public class ClassicNoPullUpRecyclerView extends FrameLayout implements View.OnClickListener, ISwipeToLoadListener {

    private ImageView mIvLoading;
    private RecyclerView mSwipeTarget;
    private SwipeToLoadLayout mSwipeToLoadLayout;
    private FrameLayout mFlLoading;

    private AnimationDrawable mAnimDrawable;
    private IPullRefreshListener mRefreshListener;
    private ILoadAnimationListener mAnimationListener;
    private boolean animation;
    private boolean autoRefresh;
    private boolean autoLoadMore;

    public void setPullRefreshListener(IPullRefreshListener refreshListener) {
        this.mRefreshListener = refreshListener;
    }

    public void setLoadAnimationListener(ILoadAnimationListener animationListener) {
        this.mAnimationListener = animationListener;
    }

    public ClassicNoPullUpRecyclerView(Context context) {
        this(context, null);
    }

    public ClassicNoPullUpRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClassicNoPullUpRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ClassicAllRecyclerView);
        animation = a.getBoolean(R.styleable.ClassicAllRecyclerView_loadAnimation, true);
        autoLoadMore = a.getBoolean(R.styleable.ClassicAllRecyclerView_autoLoadMore, true);
        autoRefresh = a.getBoolean(R.styleable.ClassicAllRecyclerView_autoRefresh, true);
        if (animation) {
            autoRefresh = false;
        }
        a.recycle();
        setupView(context);
    }

    private void setupView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_no_pull_up_recyclerview, this);
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
        mSwipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeToLoadLayout.setRefreshing(autoRefresh);
            }
        });
    }

    private void initRecyclerView() {
        if (autoLoadMore) {
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
        }
        mSwipeTarget.setLayoutManager(new LinearLayoutManager(getContext()));
        mSwipeTarget.setHasFixedSize(true);
        mSwipeTarget.setItemAnimator(new DefaultItemAnimator());
    }

    private void initLoadAnimation() {
        mAnimDrawable = (AnimationDrawable) mIvLoading.getBackground();
        if (animation) {
            mIvLoading.post(new Runnable() {
                @Override
                public void run() {
                    mAnimDrawable.start();
                    if (mAnimationListener == null) {
                        return;
                    }
                    mAnimationListener.onAnimationLoadRequest();
                }
            });
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

    public RecyclerView getRecyclerView() {
        return mSwipeTarget;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mSwipeTarget.setAdapter(adapter);
    }

    public void onCompleteRefresh(String action) {
        mSwipeToLoadLayout.setRefreshing(false);
        mSwipeToLoadLayout.setLoadingMore(false);
        if (!animation) {
            return;
        }
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
            if (mAnimationListener == null) {
                return;
            }
            mIvLoading.setBackgroundDrawable(getResources().getDrawable(R.drawable.loading_animation));
            mAnimDrawable = (AnimationDrawable) mIvLoading.getBackground();
            mAnimDrawable.start();
            mAnimationListener.onAnimationReload();
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
