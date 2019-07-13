package app.atti.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import app.atti.Activities.Profile.Fragment.QNAFragment;
import app.atti.Activities.Profile.Fragment.RecommendFragment;

public class Profile_ViewpagerAdapter extends FragmentStatePagerAdapter {
    Bundle bundle;

    public Profile_ViewpagerAdapter(FragmentManager fm,String email) {
        super(fm);
        bundle = new Bundle();
        bundle.putString("fragment_email", email); // Key, Value
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:RecommendFragment fragment = new RecommendFragment();
                fragment.setArguments(bundle);
                return fragment;
            case 1:QNAFragment fragment1 = new QNAFragment();
                fragment1.setArguments(bundle);
                return fragment1;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
