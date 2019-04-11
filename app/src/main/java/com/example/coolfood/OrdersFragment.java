package com.example.coolfood;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.TableLayout;

import com.example.coolfood.adapter.SectionsPagerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends Fragment {


    private SectionsPagerAdapter sectionPagerAdapter;
    private ViewPager viewPager;

    public OrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        sectionPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        viewPager = view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        sectionsPagerAdapter.addFragment(new OrdersActiveFragment(), "Active");
        sectionsPagerAdapter.addFragment(new OrdersHistoryFragment(), "History");
        viewPager.setAdapter(sectionsPagerAdapter);
    }

}
