package com.hacker.eaun.cigmanotes.ui.Cigma_UI;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hacker.eaun.cigmanotes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CigmaFragment extends Fragment {

    private Context mContext;
    private View mView;


    public CigmaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mRootView = inflater.inflate(R.layout.fragment_cigma, container, false);


        return mRootView;
    }

}
