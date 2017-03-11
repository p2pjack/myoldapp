package com.hacker.eaun.cigmanotes.ui.Note_UI;

import android.app.Activity;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.hacker.eaun.cigmanotes.R;
import com.hacker.eaun.cigmanotes.Data.DataBase.SQLiteDatabaseAdapter;
import com.sdsmdg.tastytoast.TastyToast;
import com.vstechlab.easyfonts.EasyFonts;
import java.util.Objects;


public class NoteActivity extends AppCompatActivity {

    public String TABLE = "MyNotes";
    public String NEW_ID;
    private EditText TITLE;
    private EditText MESSAGE;
    private Button ADD,UPDATE,DELETE,SHOW;
    private String Title,Message,NOTE_TITLE;
    private int SwitchMode;
    private SQLiteDatabaseAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        db = SQLiteDatabaseAdapter.getInstance(this);
        TITLE = (EditText)findViewById(R.id.editText_TITLE);
        TITLE.setTypeface(EasyFonts.caviarDreamsBold(this));
        TITLE.setTextSize(20);
        MESSAGE = (EditText) findViewById(R.id.editText_DESCRIPTION);
        MESSAGE.setTypeface(EasyFonts.caviarDreamsBold(this));
        MESSAGE.setTextSize(25);
        ADD = (Button)findViewById(R.id.button_add);
        UPDATE = (Button)findViewById(R.id.button_update);
        DELETE = (Button)findViewById(R.id.button_delete);
        SHOW =(Button)findViewById(R.id.button_show);

        GetMyList();
        GetTheClick();
        HideMeNow();
    }

    public void getMyIntent(){
        Bundle args = getIntent().getExtras();
        int lGET_ID = args.getInt("MY_ID",0);
        NEW_ID = String.valueOf(lGET_ID);
    }

    public void GetMyList(){
        RecyclerView recList  = (RecyclerView) findViewById(R.id.rv3);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        MyNoteAdapter lCa = new MyNoteAdapter(this,db.getAllNotes(TABLE));
        recList.setAdapter(lCa);
        HideMeNow();
    }


    private void GetTheClick(){

        ADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                if (SwitchMode == 0){
                    NewAdd();
                } else if (SwitchMode == 1){
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
    private void getText(){
        Title = TITLE.getText().toString();
        Message = MESSAGE.getText().toString();
    }
    // Hide the keyboard
    private void HideMeNow(){
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Activity.INPUT_METHOD_SERVICE);
//Hide:
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
    // CRUD for database
    private void NewDelete(){
        db.Delete(NEW_ID);
        TastyToast.makeText(this,"Data Deleted in to "+TABLE+
                "\n good to go",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);
        NEW_ID = "null";
    }

    private void NewAdd(){
        getText();
        db.insert(Title,Message);
        TastyToast.makeText(this,"Data Added in to "+TABLE+
                "\n good to go",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);
    }

    private void NewUpdate(){
        getText();
        db.Update(NEW_ID,Title,Message);
        Log.d("TAG update","id value is :"+NEW_ID);
        TastyToast.makeText(this,"Data Updated in to "+TABLE+
                "\n good to go",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);
        NEW_ID = "null";
    }

    private void TextBoxFill()
    {
        Cursor result = db.FillTheTextView(TABLE,NEW_ID);
        if (result.getCount() == 0)
        {
            ShowMessage("Error","Data not Found in " + TABLE);
            return;
        }
        while (result.moveToNext())
        {
            TITLE.setText(result.getString(1));
            MESSAGE.setText(result.getString(2));
        }

    }

    private void ClearTheInput()
    {
        TITLE.setText("");
        MESSAGE.setText("");
    }

    private void ShowMessage(String TITLE,String MESSAGE)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        builder.setCancelable(true);
        builder.setTitle(TITLE);
        builder.setMessage(MESSAGE);
        builder.setIcon(R.drawable.ic_thumb_up);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void ShowTappedData()
    {
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
        }else {
            TastyToast.makeText(this,"Nothing to show here \n Now Move Along !!",
                    TastyToast.LENGTH_SHORT,TastyToast.WARNING);
        }

        NEW_ID = "null";
    }
}
