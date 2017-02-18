package gswslbx.ddgank.moudle.gank.io.search;

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
 * SearchListAdapter
 */

public class SearchListAdapter extends CommonAdapter4RecyclerView<GanHuo.Result> implements ListenerWithPosition.OnClickWithPositionListener<CommonHolder4RecyclerView> {

    public SearchListAdapter(Context context) {
        super(context, null, R.layout.item_search);
    }

    @Override
    public void convert(CommonHolder4RecyclerView holder, GanHuo.Result searchResult) {
        if (searchResult != null) {
            holder.setTextViewText(R.id.tv_item_title_search, searchResult.getDesc() == null ? "unknown" : searchResult.getDesc());
            holder.setTextViewText(R.id.tv_item_type_search, searchResult.getType() == null ? "unknown" : searchResult.getType());
            holder.setTextViewText(R.id.tv_item_publisher_search, searchResult.getWho() == null ? "unknown" : searchResult.getWho());
            holder.setTextViewText(R.id.tv_item_time_search, DateUtil.dateFormat(searchResult.getPublishedAt()));
            holder.setOnClickListener(this, R.id.ll_item_search);
        }
    }

    @Override
    public void onClick(View v, int position, CommonHolder4RecyclerView holder) {
        if (mData.get(position) == null) {
            return;
        }
        Uri uri = Uri.parse(mData.get(position).getUrl());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        mContext.startActivity(intent);
    }
}
