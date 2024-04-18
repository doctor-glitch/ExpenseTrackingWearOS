package Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import Fragments.ExpenseFragment;
import Fragments.HomeFragment;
import Fragments.IncomeFragment;

public class ScreenPagerAdapter extends FragmentPagerAdapter {

    public ScreenPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new ExpenseFragment();
            case 2:
                return new IncomeFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}

