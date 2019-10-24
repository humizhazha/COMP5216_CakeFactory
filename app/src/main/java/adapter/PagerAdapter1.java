package adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import au.edu.sydney.comp5216.cakefactory.TabFragment1;
import au.edu.sydney.comp5216.cakefactory.TabFragment2;
import au.edu.sydney.comp5216.cakefactory.TabFragment3;

public class PagerAdapter1 extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter1(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabFragment1 tab1 = new TabFragment1();
                return tab1;
            case 1:
                TabFragment2 tab2 = new TabFragment2();
                return tab2;
            case 2:
                TabFragment3 tab3 = new TabFragment3();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}