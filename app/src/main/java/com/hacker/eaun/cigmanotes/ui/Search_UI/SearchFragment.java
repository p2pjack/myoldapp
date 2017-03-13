package com.hacker.eaun.cigmanotes.ui.Search_UI;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.hacker.eaun.cigmanotes.Data.DataBase.SQLiteDatabaseAdapter;
import com.hacker.eaun.cigmanotes.R;
import com.hacker.eaun.cigmanotes.core.MainApplication;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    public String SEARCH, ROW;
    private SQLiteDatabaseAdapter db;
    private EditText SUPPLIER;
    private EditText CODE;
    private MainApplication mContext = new MainApplication(getContext());
    private View mView;

    public SearchFragment() {
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
        View mRootView = inflater.inflate(R.layout.fragment_search, container, false);
        db = SQLiteDatabaseAdapter.getInstance(mContext);

        SUPPLIER = (EditText) mRootView.findViewById(R.id.editText_supplier);
        CODE = (EditText) mRootView.findViewById(R.id.editText_code);
        Button supplier = (Button) mRootView.findViewById(R.id.button_search);
        Button code = (Button) mRootView.findViewById(R.id.button_code);

        supplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SEARCH = CODE.getText().toString();
                ROW = "Supplier";
                GetMyList();
                clear();
                HideMeNow();
            }
        });

        code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SEARCH = SUPPLIER.getText().toString();
                ROW = "Code";
                GetMyList();
                clear();
                HideMeNow();
            }
        });
        return mRootView;
    }

    public void GetMyList() {
        RecyclerView recList = (RecyclerView) mView.findViewById(R.id.rv2);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        SearchAdapterRecyclerView lCa = new SearchAdapterRecyclerView(db.getSupplierCode(SEARCH, ROW));
        recList.setAdapter(lCa);
    }

    public void HideMeNow() {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
//Hide:
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    private void clear() {
        SUPPLIER.setText("");
        CODE.setText("");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
