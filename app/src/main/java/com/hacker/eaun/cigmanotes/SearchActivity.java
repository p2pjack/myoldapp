package com.hacker.eaun.cigmanotes;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;


import com.hacker.eaun.cigmanotes.adapters.SearchAdapterRecyclerView;
import com.hacker.eaun.cigmanotes.adapters.SQLiteDatabaseAdapter;

public class SearchActivity extends AppCompatActivity  {

    private SQLiteDatabaseAdapter db;
    private EditText SUPPLIER;
    private EditText CODE;
    public String SEARCH,ROW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        db = SQLiteDatabaseAdapter.getInstance(this);

        SUPPLIER = (EditText) findViewById(R.id.editText_supplier);
        CODE = (EditText) findViewById(R.id.editText_code);
        Button supplier = (Button) findViewById(R.id.button_search);
        Button code = (Button) findViewById(R.id.button_code);

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
    }

    public void GetMyList(){
        RecyclerView recList  = (RecyclerView) findViewById(R.id.rv2);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        SearchAdapterRecyclerView lCa = new SearchAdapterRecyclerView(db.getSupplierCode(SEARCH, ROW));
        recList.setAdapter(lCa);
    }

//        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
////Hide:
//        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
////Show
//        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);

    public void HideMeNow(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
//Hide:
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    private void clear(){
        SUPPLIER.setText("");
            CODE.setText("");
    }
}
