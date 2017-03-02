package gswslbx.ddgank.moudle.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gswslbx on 2017/2/16.
 * 通用的Adapter
 */
public abstract class CommonAdapter4RecyclerView<T> extends RecyclerView.Adapter {
    public Context mContext;
    public List<T> mData;
    private int layoutId;
    private View mView;

    public CommonAdapter4RecyclerView(Context context, List<T> data, int layoutId) {
        this.mContext = context;
        this.mData = data == null ? (List<T>) new ArrayList<>() : data;
        this.layoutId = layoutId;
    }

    /**
     *加载子布局
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(layoutId, parent, false);
        return new CommonHolder4RecyclerView(mView);
    }

    /**
     *绑定子布局数据
     *
     * @param holder
     * @param position
     */
 @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CommonHolder4RecyclerView commonHolder = (CommonHolder4RecyclerView) holder;
        commonHolder.position = position;
        convert(commonHolder, mData.get(position));
    }

    /**
     *获得子布局的数目
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return (mData != null) ? mData.size() : 0;
    }

    /**
     *抽象的适配器的填充方法
     *
     * @param holder ViewHolder
     * @param t item的内容
     */
    public abstract void convert(CommonHolder4RecyclerView holder, T t);

}
