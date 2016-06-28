package view.library.refresh;


/**
 * IPullRefreshListenerI
 * Created by smileCloud on 16/6/20.
 *
 * @param <T>pull up To Refresh Data
 * @param <R>pull down To Load More Data
 */
public interface IPullRefreshListenerI<T, R> extends ISwipeToLoadListener {
    void onRefreshData(T data);

    void onLoadMoreData(R data);

    void onCompleteRefresh(String action);

    void onReload();
}
