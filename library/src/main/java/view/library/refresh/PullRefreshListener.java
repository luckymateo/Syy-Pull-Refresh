package view.library.refresh;


import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;

/**
 * PullRefreshListener
 * Created by smileCloud on 16/6/20.
 *
 * @param <T>pull up To Refresh Data
 * @param <R>pull down To Load More Data
 */
public interface PullRefreshListener<T, R> extends OnRefreshListener, OnLoadMoreListener {
    void onRefreshData(T data);

    void onLoadMoreData(R data);

    void onCompleteRefresh(String action);
}
