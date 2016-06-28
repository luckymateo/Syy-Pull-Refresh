package syy.pull.demo.mvp;

import java.util.List;

import view.library.refresh.IPullRefreshListenerI;

/**
 * MainView
 * Created by smileCloud on 16/6/22.
 */
public interface MainView extends IPullRefreshListenerI<List<String>, List<String>> {

}
