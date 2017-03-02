package gswslbx.ddgank.moudle.gank.io.meizi;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import gswslbx.ddgank.R;
import gswslbx.ddgank.bean.GanHuo;
import gswslbx.ddgank.moudle.common.CommonAdapter4RecyclerView;
import gswslbx.ddgank.moudle.common.CommonHolder4RecyclerView;
import gswslbx.ddgank.moudle.common.ListenerWithPosition;

/**
 * Created by Gswslbx on 2017/2/16.
 */

public class MeiziListAdapter extends CommonAdapter4RecyclerView<GanHuo.Result> implements ListenerWithPosition.OnClickWithPositionListener<CommonHolder4RecyclerView> {

    public MeiziListAdapter(Context context) {
        super(context, null, R.layout.item_meizi);
    }

    @Override
    public void convert(CommonHolder4RecyclerView holder, GanHuo.Result gankResult) {
        if (gankResult != null) {
            holder.setImageViewImg(mContext, R.id.iv_meizi, gankResult.getUrl());
            holder.setOnClickListener(this, R.id.ll_item_meizi);
        }
    }

    @Override
    public void onClick(View v, int position, CommonHolder4RecyclerView holder) {
        String uri = mData.get(position).getUrl();
        //// TODO: 2017/2/16 图片详情显示
        Snackbar.make(v, uri, Snackbar.LENGTH_SHORT).show();
    }
}

