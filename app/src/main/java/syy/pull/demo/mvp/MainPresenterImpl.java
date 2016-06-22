package syy.pull.demo.mvp;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import view.library.refresh.ClassicConstant;

/**
 * MainPresenterImpl
 * Created by smileCloud on 16/6/22.
 */
public class MainPresenterImpl implements MainPresenter {

    private MainView view;
    private int page = 1;
    private List<String> data;

    public MainPresenterImpl(MainView view) {
        this.view = view;
        this.data = new ArrayList<>();
    }

    @Override
    public void onLoadMore() {
        page++;
        loadData();
        view.onLoadMoreData(data);
        view.onCompleteRefresh(ClassicConstant.loadMoreComplete);
    }

    @Override
    public void onRefresh() {
        page = 1;
        loadData();
        if (onContinue()) {
            view.onRefreshData(data);
            view.onCompleteRefresh(ClassicConstant.refreshComplete);
            return;
        }
        onFailedComplete();
    }

    @Override
    public void onFailedComplete() {
        view.onCompleteRefresh(ClassicConstant.onFailedComplete);
    }

    private void loadData() {
        data.clear();
        for (int i = 0; i < 10; i++) {
            data.add(i + "a" + page);
        }
    }

    private boolean onContinue() {
        Random random = new Random();
        int i = random.nextInt(10);
        return i == 3 || i == 6 || i == 9;
    }
}
