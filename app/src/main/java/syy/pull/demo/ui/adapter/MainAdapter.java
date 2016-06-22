package syy.pull.demo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import syy.pull.demo.R;

/**
 * MainAdapter
 * Created by smileCloud on 16/6/22.
 */
public class MainAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<String> mDatas;

    public MainAdapter(Context context) {
        this.mContext = context;
        this.mDatas = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item, parent, false);
        return new VHItem(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VHItem) {
            VHItem vhItem = (VHItem) holder;
            vhItem.mTvItem.setText(mDatas.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void addAll(List<String> data) {
        int position = mDatas.size();
        mDatas.addAll(data);
        notifyItemRangeChanged(position, mDatas.size());
    }

    public void swapData(List<String> data) {
        mDatas.clear();
        mDatas.addAll(data);
        notifyDataSetChanged();
    }

    class VHItem extends RecyclerView.ViewHolder {

        TextView mTvItem;

        public VHItem(View view) {
            super(view);
            mTvItem = (TextView) view.findViewById(R.id.tv_item);
        }
    }
}
