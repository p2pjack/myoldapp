package com.hacker.eaun.cigmanotes.ui.Cigma_UI;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hacker.eaun.cigmanotes.R;
import com.hacker.eaun.cigmanotes.core.MainApplication;

/**
 * A simple {@link Fragment} subclass.
 */
public class CigmaFragment extends Fragment {

    private MainApplication mContext = new MainApplication(getContext());
    private View mView;


    public CigmaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mRootView = inflater.inflate(R.layout.fragment_cigma, container, false);


        return mRootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
