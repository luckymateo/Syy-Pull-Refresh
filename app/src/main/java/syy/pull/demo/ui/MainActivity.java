package syy.pull.demo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import syy.pull.demo.R;
import syy.pull.demo.mvp.MainPresenter;
import syy.pull.demo.mvp.MainPresenterImpl;
import syy.pull.demo.mvp.MainView;
import syy.pull.demo.ui.adapter.MainAdapter;
import view.library.refresh.ClassicRecyclerViewLayout;

public class MainActivity extends AppCompatActivity implements MainView {

    @Bind(R.id.pvl_layout)
    ClassicRecyclerViewLayout mPvlLayout;
    private MainAdapter mAdapter;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initRecyclerView();
        initPresenter();
    }

    private void initRecyclerView() {
        mPvlLayout.setPullListener(this);
        mPvlLayout.setRefreshFailed(this);
        RecyclerView mRecyclerView = mPvlLayout.initRecyclerView();
        mAdapter = new MainAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initPresenter() {
        presenter = new MainPresenterImpl(this);
    }

    @Override
    public void onLoadMore() {
        presenter.onLoadMore();
    }

    @Override
    public void onRefresh() {
        presenter.onRefresh();
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
    public void onRefreshFailed() {
        presenter.onRefresh();
    }
}
