package com.hacker.eaun.cigmanotes.common;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hacker.eaun.cigmanotes.ui.Cigma_UI.CigmaFragment;
import com.hacker.eaun.cigmanotes.ui.Note_UI.NoteFragment;
import com.hacker.eaun.cigmanotes.ui.Search_UI.SearchFragment;
import com.hacker.eaun.cigmanotes.ui.Tools_UI.ToolsFragment;
import com.hacker.eaun.cigmanotes.ui.Wms_UI.WmsFragment;


/**
 * Created by Eaun-Ballinger on 25/02/2017.
 *
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private String tabtitles[] = new String[]{"WMS", "CIGMA", "NOTES", "SEARCH", "TOOLS"};
    private int MAX_PAGES = 5;
    private Context mContext;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment selectedFragment = null;
        switch (position) {
            case 0:
                selectedFragment = new CigmaFragment();
                break;
            case 1:
                selectedFragment = new WmsFragment();
                break;
            case 2:
                selectedFragment = new NoteFragment();
                break;
            case 3:
                selectedFragment = new SearchFragment();
                break;
            case 4:
                selectedFragment = new ToolsFragment();
                break;
            default:
                selectedFragment = new CigmaFragment();
                break;
        }
        return selectedFragment;
    }

    @Override
    public int getCount() {
        return MAX_PAGES;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabtitles[position];
    }
}
