package cn.bingoogolapple.refreshlayout.demo.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * in BGARefreshLayout-Android.
 * by:chinaume@163.com
 * Created by moo on 16/4/16 12:58.
 */
public class StickWithChangeAdapter extends RecyclerView.Adapter {

    private List mData = new ArrayList();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VHolder(getInflater(android.R.layout.test_list_item, parent));
    }

    private View getInflater(@LayoutRes int layoutRes, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((VHolder) holder).init(position);
    }

    public void setmData(List mData) {
        this.mData = mData;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    List getData() {
        return mData;
    }


    public class VHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public VHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(android.R.id.text1);
        }

        public void init(int position) {
            String text = (String) mData.get(position);
            textView.setText(text);
        }
    }
}
