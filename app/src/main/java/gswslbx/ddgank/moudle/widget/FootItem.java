package gswslbx.ddgank.moudle.widget;

import android.view.View;
import android.view.ViewGroup;

/**
 * Footer item_common
 *
 * @author cjj on 2016/1/30.
 */
public abstract class FootItem {

    public CharSequence loadingText;
    public CharSequence endText;
    public CharSequence pullToLoadText;

    public abstract View onCreateView(ViewGroup parent);

    public abstract void onBindData(View view, int state);

}
