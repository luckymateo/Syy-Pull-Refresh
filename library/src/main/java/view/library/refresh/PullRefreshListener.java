package view.library.refresh;


/**
 * PullRefreshListener
 * Created by smileCloud on 16/6/20.
 *
 * @param <T>pull up To Refresh Data
 * @param <R>pull down To Load More Data
 */
public interface PullRefreshListener<T, R> extends SwipeToLoadListener {
    void onRefreshData(T data);

    void onLoadMoreData(R data);

    void onCompleteRefresh(String action);

    void onReload();
}
