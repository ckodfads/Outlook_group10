package vn.edu.usth.weather;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class HomeFragmentAdapter extends FragmentPagerAdapter {
    private final int PAGE_COUNT = 3;
    private String[] titles = new String[]{"Hanoi", "Paris", "Toulouse"};

    public HomeFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return new WeatherAndForecastFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}