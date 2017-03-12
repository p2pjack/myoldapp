package com.hacker.eaun.cigmanotes.ui.Wms_UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hacker.eaun.cigmanotes.R;
import com.hacker.eaun.cigmanotes.core.MainApplication;

public class WmsFragment extends Fragment {
    private MainApplication mContext;
    private View mView;


    public WmsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = new MainApplication(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mRootView = inflater.inflate(R.layout.fragment_wms, container, false);


        return mRootView;
    }

}
