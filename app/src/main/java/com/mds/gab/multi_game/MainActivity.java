package com.mds.gab.multi_game;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mds.gab.multi_game.component.ViewPagerDisabling;
import com.mds.gab.multi_game.fragment.BeginGameFragment;
import com.mds.gab.multi_game.fragment.DragnDropFragment;
import com.mds.gab.multi_game.fragment.IpacGameFragment;
import com.mds.gab.multi_game.fragment.SettingsFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener {

    public ViewPagerDisabling viewPager;
    private TabLayout tabLayout;
    private int currentPage;
    private ArrayList<Fragment> fragments = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.activity_main_view_page);
        tabLayout = findViewById(R.id.activity_main_tab_layout);

        tabLayout.addOnTabSelectedListener(this);
        viewPager.addOnPageChangeListener(this);

        Bundle bundle = new Bundle();
        bundle.putString("name", getString(R.string.dragndrop));
        BeginGameFragment dragndropFragment = new BeginGameFragment();
        dragndropFragment.setArguments(bundle);
        fragments.add(dragndropFragment);

        bundle = new Bundle();
        bundle.putString("name", getString(R.string.swipe));
        BeginGameFragment swipeFragment = new BeginGameFragment();
        swipeFragment.setArguments(bundle);
        fragments.add(swipeFragment);

        bundle = new Bundle();
        bundle.putString("name", getString(R.string.fast_tap));
        BeginGameFragment tapFragment = new BeginGameFragment();
        tapFragment.setArguments(bundle);
        fragments.add(tapFragment);

        bundle = new Bundle();
        bundle.putString("name", getString(R.string.ipac_game));
        BeginGameFragment ipacFragment = new BeginGameFragment();
        ipacFragment.setArguments(bundle);
        fragments.add(ipacFragment);

        fragments.add(new SettingsFragment());

        FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                currentPage = i;
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                switch(position){
                    case 0:
                        return "DragnDrop";
                    case 1:
                        return "Swipe";
                    case 2:
                        return "FastTap";
                    case 3:
                        return "IpacGame";
                    case 4:
                        return "Settings";
                    default:
                        return "";
                }
            }
        };

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void onTabSelected(TabLayout.Tab tab){
        this.viewPager.setPagingEnabled(true);
        for(Fragment fragment : getSupportFragmentManager().getFragments()){
            for(Fragment subFragment : fragment.getChildFragmentManager().getFragments()) {
                if (subFragment.getTag() != null && subFragment.getTag().equals(getString(R.string.fast_tap))) {
                    subFragment.getFragmentManager().popBackStack();
                } else if (subFragment.getTag() != null && subFragment.getTag().equals(getString(R.string.swipe))) {
                    subFragment.getFragmentManager().popBackStack();
                } else if (subFragment.getTag() != null && subFragment.getTag().equals(getString(R.string.dragndrop))){
                    subFragment.getFragmentManager().popBackStack();
                } else if (subFragment.getTag() != null && subFragment.getTag().equals(getString(R.string.ipac_game))){
                    subFragment.getFragmentManager().popBackStack();
                }
            }
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
