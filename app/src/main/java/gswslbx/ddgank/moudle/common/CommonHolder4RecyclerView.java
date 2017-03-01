package gswslbx.ddgank.moudle.common;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * 通用的Holder
 */
public class CommonHolder4RecyclerView extends RecyclerView.ViewHolder {

    public View mConvertView;
    public int position;
    private SparseArray<View> mViews;

    public CommonHolder4RecyclerView(View itemView) {
        super(itemView);
        this.mConvertView = itemView;
        this.mViews = new SparseArray<>();
    }


    /**
     * 得到item上的控件
     * @param viewId 控件的id
     * @return 对应的控件
     */
    public <T extends View> T getView(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }

        return (T) view;

    }

    /**
     *设置TextView的内容
     * @param textViewId
     * @param text
     * @return
     */
    public CommonHolder4RecyclerView setTextViewText(@IdRes int textViewId, String text) {
        TextView tv = getView(textViewId);
        if (!TextUtils.isEmpty(text)) {
            tv.setText(text);
        } else {
            tv.setText(" ");
        }
        return this;
    }

    /**
     *
     * @param context
     * @param imageViewId
     * @param imageUrl
     * @return
     */
    public CommonHolder4RecyclerView setImageViewImg(@NonNull Context context, @IdRes int imageViewId, String imageUrl) {
        ImageView imageView = getView(imageViewId);
        Glide.with(context).load(imageUrl).into(imageView);
        return this;
    }

    /**
     *
     * @param clickListener
     * @param viewIds
     * @return
     */
    public CommonHolder4RecyclerView setOnClickListener(ListenerWithPosition.OnClickWithPositionListener clickListener, @IdRes int... viewIds) {
        ListenerWithPosition listener = new ListenerWithPosition(position, this);
        listener.setOnClickListener(clickListener);
        for (int id : viewIds) {
            View v = getView(id);
            v.setOnClickListener(listener);
        }
        return this;
    }

}
