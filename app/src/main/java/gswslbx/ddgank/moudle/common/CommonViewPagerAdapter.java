package gswslbx.ddgank.moudle.common;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gswslbx on 2017/2/16.
 */

public class CommonViewPagerAdapter extends FragmentPagerAdapter {
    private String[] title;
    private List<Fragment> mFragments = new ArrayList<>();

    /**
     *
     * @param fm
     * @param titles
     */
    public CommonViewPagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        title = titles;
    }

    /**
     *
     * @param fragment
     */
    public void addFragment(Fragment fragment) {
        mFragments.add(fragment);
    }

    /**
     *
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    /**
     *
     * @return
     */
    @Override
    public int getCount() {
        return mFragments.size();
    }

    /**
     *
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

}
