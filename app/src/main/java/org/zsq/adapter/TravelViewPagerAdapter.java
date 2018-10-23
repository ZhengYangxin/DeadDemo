package org.zsq.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.qslll.library.ExpandingViewPagerAdapter;

import org.zsq.VO.Travel;
import org.zsq.fragment.TravelExpandingFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danke.
 */
public class TravelViewPagerAdapter extends ExpandingViewPagerAdapter {

    List<Travel> travels;

    public TravelViewPagerAdapter(FragmentManager fm) {
        super(fm);
        travels = new ArrayList<>();
    }

    public void addAll(List<Travel> travels){
        this.travels.addAll(travels);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        Travel travel = travels.get(position);
        return TravelExpandingFragment.newInstance(travel);
    }

    @Override
    public int getCount() {
        return travels.size();
    }

}
