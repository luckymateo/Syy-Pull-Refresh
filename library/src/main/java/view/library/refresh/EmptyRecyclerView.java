package view.library.refresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;


/**
 * EmptyRecyclerView
 * Created by SmileyCloud on 16/1/14.
 */
public class EmptyRecyclerView extends RecyclerView {

    private View emptyView;

    public EmptyRecyclerView(Context context) {
        super(context);
    }

    public EmptyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    void checkIfEmpty() {

        if (emptyView == null || getAdapter() == null) {
            return;
        }
        if (getAdapter().getItemCount() == 0) {
            emptyView.setVisibility(View.VISIBLE);
            EmptyRecyclerView.this.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            EmptyRecyclerView.this.setVisibility(View.VISIBLE);
        }

    }

    final AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            checkIfEmpty();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            checkIfEmpty();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            super.onItemRangeChanged(positionStart, itemCount, payload);
            checkIfEmpty();
        }

    };

    @Override
    public void setAdapter(Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }
        checkIfEmpty();
    }

    public void setEmptyView(int resource) {
        View emptyView;
        ViewParent viewParent = getParent();
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        if (viewParent instanceof LinearLayout) {
            emptyView = layoutInflater.inflate(resource, null);
            LinearLayout parentLayout = (LinearLayout) viewParent;
            parentLayout.addView(emptyView, 1, params);
        } else {
            emptyView = layoutInflater.inflate(resource, (ViewGroup) viewParent);
        }
        this.emptyView = emptyView;
    }

    public void setEmptyView(View emptyView) {
        ViewParent viewParent = getParent();
        if (viewParent instanceof LinearLayout) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            LinearLayout parentLayout = (LinearLayout) viewParent;
            parentLayout.addView(emptyView, 1, params);
        }
        this.emptyView = emptyView;
    }

    public View getEmptyView() {
        return emptyView;
    }

    public void showEmptyView() {
        emptyView.setVisibility(View.VISIBLE);
        EmptyRecyclerView.this.setVisibility(View.GONE);
    }

    public void hideEmptyView() {
        emptyView.setVisibility(View.GONE);
        EmptyRecyclerView.this.setVisibility(View.VISIBLE);
    }
}