package syy.pull.demo.mvp;

import java.util.List;

import view.library.refresh.PullRefreshFailed;
import view.library.refresh.PullRefreshListener;

/**
 * MainView
 * Created by smileCloud on 16/6/22.
 */
public interface MainView extends PullRefreshListener<List<String>, List<String>>,PullRefreshFailed {

}
