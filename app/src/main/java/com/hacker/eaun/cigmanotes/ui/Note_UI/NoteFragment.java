package com.hacker.eaun.cigmanotes.ui.Note_UI;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.hacker.eaun.cigmanotes.Data.DataBase.SQLiteDatabaseAdapter;
import com.hacker.eaun.cigmanotes.R;
import com.hacker.eaun.cigmanotes.core.MainApplication;
import com.sdsmdg.tastytoast.TastyToast;
import com.vstechlab.easyfonts.EasyFonts;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteFragment extends Fragment {

    public String TABLE = "MyNotes";
    public String NEW_ID;
    private EditText TITLE;
    private EditText MESSAGE;
    private Button ADD, UPDATE, DELETE, SHOW;
    private String Title, Message, NOTE_TITLE;
    private int SwitchMode;
    private SQLiteDatabaseAdapter db;
    private MainApplication mContext = new MainApplication(getContext());
    private View mView;

    public NoteFragment() {
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
        View mRootVew = inflater.inflate(R.layout.fragment_note, container, false);
        db = SQLiteDatabaseAdapter.getInstance(mContext);
        TITLE = (EditText) mRootVew.findViewById(R.id.editText_TITLE);
        TITLE.setTypeface(EasyFonts.caviarDreamsBold(mContext));
        TITLE.setTextSize(20);
        MESSAGE = (EditText) mRootVew.findViewById(R.id.editText_DESCRIPTION);
        MESSAGE.setTypeface(EasyFonts.caviarDreamsBold(mContext));
        MESSAGE.setTextSize(25);
        ADD = (Button) mRootVew.findViewById(R.id.button_add);
        UPDATE = (Button) mRootVew.findViewById(R.id.button_update);
        DELETE = (Button) mRootVew.findViewById(R.id.button_delete);
        SHOW = (Button) mRootVew.findViewById(R.id.button_show);

        GetMyList();
        GetTheClick();
        HideMeNow();

        return mRootVew;
    }

    public void getMyIntent() {
        Bundle args = getActivity().getIntent().getExtras();
        int lGET_ID = args.getInt("MY_ID", 0);
        NEW_ID = String.valueOf(lGET_ID);
    }

    public void GetMyList() {
        RecyclerView recList = (RecyclerView) mView.findViewById(R.id.rv3);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        MyNoteAdapter lCa = new MyNoteAdapter(mContext, db.getAllNotes(TABLE));
        recList.setAdapter(lCa);
        HideMeNow();
    }


    private void GetTheClick() {

        ADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                if (SwitchMode == 0) {
                    NewAdd();
                } else if (SwitchMode == 1) {
                    //UpdateData();
                    NewUpdate();
                }
                ClearTheInput();
                GetMyList();
                SwitchMode = 0;
            }
        });

        UPDATE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                getMyIntent();
                TextBoxFill();
                HideMeNow();
                SwitchMode = 1;
            }
        });

        DELETE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                getMyIntent();
                NewDelete();
                ClearTheInput();
                GetMyList();
                HideMeNow();
            }
        });

        SHOW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                getMyIntent();
                ShowTappedData();
            }
        });
    }

    // Get Text box Data
    private void getText() {
        Title = TITLE.getText().toString();
        Message = MESSAGE.getText().toString();
    }

    // Hide the keyboard
    private void HideMeNow() {
        InputMethodManager imm = (InputMethodManager)
                mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
//Hide:
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    // CRUD for database
    private void NewDelete() {
        db.Delete(NEW_ID);
        TastyToast.makeText(mContext, "Data Deleted in to " + TABLE +
                "\n good to go", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
        NEW_ID = "null";
    }

    private void NewAdd() {
        getText();
        db.insert(Title, Message);
        TastyToast.makeText(mContext, "Data Added in to " + TABLE +
                "\n good to go", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
    }

    private void NewUpdate() {
        getText();
        db.Update(NEW_ID, Title, Message);
        Log.d("TAG update", "id value is :" + NEW_ID);
        TastyToast.makeText(mContext, "Data Updated in to " + TABLE +
                "\n good to go", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
        NEW_ID = "null";
    }

    private void TextBoxFill() {
        Cursor result = db.FillTheTextView(TABLE, NEW_ID);
        if (result.getCount() == 0) {
            ShowMessage("Error", "Data not Found in " + TABLE);
            return;
        }
        while (result.moveToNext()) {
            TITLE.setText(result.getString(1));
            MESSAGE.setText(result.getString(2));
        }

    }

    private void ClearTheInput() {
        TITLE.setText("");
        MESSAGE.setText("");
    }

    private void ShowMessage(String TITLE, String MESSAGE) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.MyDialogTheme);
        builder.setCancelable(true);
        builder.setTitle(TITLE);
        builder.setMessage(MESSAGE);
        builder.setIcon(R.drawable.ic_thumb_up);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void ShowTappedData() {
        if (!Objects.equals(NEW_ID, "null")) {
            Cursor result = db.FillTheTextView(TABLE, NEW_ID);
            StringBuilder buffer = new StringBuilder();
            if (result.getCount() == 0) {
                ShowMessage("Error", "Data not Found in " + TABLE);
                return;
            }
            while (result.moveToNext()) {
                NOTE_TITLE = result.getString(1);
                buffer.append("\n").append(result.getString(2)).append("\n\n");
            }
            ShowMessage("Details For : " + NOTE_TITLE, buffer.toString());
        } else {
            TastyToast.makeText(mContext, "Nothing to show here \n Now Move Along !!",
                    TastyToast.LENGTH_SHORT, TastyToast.WARNING);
        }

        NEW_ID = "null";
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
