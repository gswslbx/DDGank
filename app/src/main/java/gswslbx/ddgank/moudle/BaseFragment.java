package gswslbx.ddgank.moudle;


import android.support.v4.app.Fragment;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.RefWatcher;

import gswslbx.ddgank.bean.App;

/**
 * Created by Gswslbx on 2017/2/18.
 */

public class BaseFragment extends Fragment {

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = App.getRefWatcher(getActivity());
        refWatcher.watch(this);
        Logger.d(this + "Destroy");
    }
}
