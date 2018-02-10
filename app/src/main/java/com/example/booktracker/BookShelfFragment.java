package com.example.booktracker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class BookShelfFragment extends Fragment {

    private TabLayout tabs;
    private ViewPager viewPager;

    public BookShelfFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_shelf, container, false);

        tabs = (TabLayout) view.findViewById(R.id.bookshelf_tabLayout);
        viewPager = (ViewPager) view.findViewById(R.id.bookshelf_viewPager);

        PagerAdapter pagerAdapter = new PagerAdapter(getFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        //link the tabs with the viewpager
        tabs.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        return view;
    }

    class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            PagerFragment pagerFragment = new PagerFragment();
            return pagerFragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return getString(R.string.tab_reading);
            } else if (position == 1) {
                return getString(R.string.tab_toRead);
            } else if (position == 2) {
                return getString(R.string.tab_read);
            }
            return "Tab";
        }
    }

}
