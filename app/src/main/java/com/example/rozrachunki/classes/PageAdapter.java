package com.example.rozrachunki.classes;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.rozrachunki.FragmentMembers;
import com.example.rozrachunki.FragmentPayment;

public class PageAdapter extends FragmentPagerAdapter {
    private int numoftabs;

    public PageAdapter(@NonNull FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numoftabs = numOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                return new FragmentPayment();
            case 1:
                return new FragmentMembers();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numoftabs;
    }

    public int getItemPosition(@NonNull Object object){
        return POSITION_NONE;
    }
}
