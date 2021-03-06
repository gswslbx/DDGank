package gswslbx.ddgank.moudle.gank.io.ganks;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import gswslbx.ddgank.R;
import gswslbx.ddgank.bean.GanHuo;
import gswslbx.ddgank.utils.DateUtil;
import gswslbx.ddgank.moudle.common.CommonAdapter4RecyclerView;
import gswslbx.ddgank.moudle.common.CommonHolder4RecyclerView;
import gswslbx.ddgank.moudle.common.ListenerWithPosition;

/**
 * GanksListAdapter
 * Created by Gswslbx on 2017/2/16.
 */

public class GanksListAdapter extends CommonAdapter4RecyclerView<GanHuo.Result> implements ListenerWithPosition.OnClickWithPositionListener<CommonHolder4RecyclerView> {

    public GanksListAdapter(Context context) {
        super(context, null, R.layout.item_common);
    }

    /**
     *填充子布局数据
     *
     * @param holder 加载子布局
     * @param gankResult 填充内容
     */
    @Override
    public void convert(CommonHolder4RecyclerView holder, GanHuo.Result gankResult) {
        //// TODO: 2017/3/2 webView的实现

        if (gankResult != null) {
            holder.setTextViewText(R.id.tv_item_title, gankResult.getDesc() == null ? "unknown" : gankResult.getDesc());
            holder.setTextViewText(R.id.tv_item_publisher, gankResult.getWho() == null ? "unknown" : gankResult.getWho());
            holder.setTextViewText(R.id.tv_item_time, DateUtil.dateFormat(gankResult.getPublishedAt()));
            holder.setOnClickListener(this, R.id.ll_item_ganks);
        }
    }

    /**
     *监听item
     *
     * @param v
     * @param position
     * @param holder
     */
    @Override
    public void onClick(View v, int position, CommonHolder4RecyclerView holder) {
        Uri uri = Uri.parse(mData.get(position).getUrl());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        mContext.startActivity(intent);
    }
}
