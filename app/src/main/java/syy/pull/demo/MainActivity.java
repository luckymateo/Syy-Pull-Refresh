package syy.pull.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;
import syy.pull.R;
import view.library.refresh.PullRecyclerViewLayout;
import view.library.refresh.PullRefreshListener;

public class MainActivity extends AppCompatActivity implements PullRefreshListener {


    @Bind(R.id.pvl_layout)
    PullRecyclerViewLayout mPvlLayout;
    private RecyclerView mRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mRecycler = mPvlLayout.initRecyclerView();
        mPvlLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPvlLayout.setRefreshingComplete(false);
            }
        },1000);
    }

    @Override
    public void onLoadMore() {
        mPvlLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPvlLayout.setLoadingMoreComplete(false);
            }
        }, 1000);
    }

    @Override
    public void onRefresh() {
        mPvlLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPvlLayout.setRefreshingComplete(false);
            }
        }, 1000);
    }
}
