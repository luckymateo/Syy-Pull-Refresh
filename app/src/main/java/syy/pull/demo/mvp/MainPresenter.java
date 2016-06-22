package syy.pull.demo.mvp;

/**
 * Created by smileCloud on 16/6/22.
 */
public interface MainPresenter {
    void onLoadMore();

    void onRefresh();

    void onFailedComplete();
}
