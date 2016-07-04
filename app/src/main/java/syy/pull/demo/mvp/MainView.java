package syy.pull.demo.mvp;

import java.util.List;

import view.library.refresh.ILoadAnimationListener;
import view.library.refresh.IPullRefreshListener;

/**
 * MainView
 * Created by smileCloud on 16/6/22.
 */
public interface MainView extends IPullRefreshListener<List<String>, List<String>>,ILoadAnimationListener {

}
