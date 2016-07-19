package syy.pull.demo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import syy.pull.demo.R;
import syy.pull.demo.mvp.MainPresenter;
import syy.pull.demo.mvp.MainPresenterImpl;
import syy.pull.demo.mvp.MainView;
import syy.pull.demo.ui.adapter.MainAdapter;
import view.library.refresh.ClassicAllRecyclerView;

public class MainActivity extends AppCompatActivity implements MainView {

    @Bind(R.id.pvl_layout)
    ClassicAllRecyclerView mPvlLayout;
    private MainAdapter mAdapter;
    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initRecyclerViewLayout();
        initPresenter();
    }

    private void initRecyclerViewLayout() {
        mPvlLayout.setPullRefreshListener(this);
        mPvlLayout.setLoadAnimationListener(this);
        View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_view, null);
        mPvlLayout.setEmptyView(emptyView);
        mAdapter = new MainAdapter(this);
        mPvlLayout.setAdapter(mAdapter);
    }

    private void initPresenter() {
        mPresenter = new MainPresenterImpl(this);
    }

    @Override
    public void onLoadMore() {
        mPresenter.onLoadMore();
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    @Override
    public void onCompleteRefresh(final String action) {
        mPvlLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPvlLayout.onCompleteRefresh(action);//1秒的等待时间
            }
        }, 1000);
    }

    @Override
    public void onLoadMoreData(List<String> data) {
        mAdapter.addAll(data);
    }

    @Override
    public void onRefreshData(List<String> data) {
        mAdapter.swapData(data);
    }

    @Override
    public void onAnimationReload() {
        mPresenter.onRefresh();
    }

    @Override
    public void onAnimationLoadRequest() {
        mPresenter.onRefresh();
    }

}
