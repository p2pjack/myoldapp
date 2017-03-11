package com.hacker.eaun.cigmanotes.adapters;


/**
 * Created by Eaun-Ballinger on 28/07/2016.
 * Help Control and modify the Sqlite database
 * Cigma Functions / WMS
 */

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.os.Process;
import android.util.Log;
import com.hacker.eaun.cigmanotes.R;
import com.hacker.eaun.cigmanotes.passthrough.MyNotesGS;
import com.hacker.eaun.cigmanotes.passthrough.NoteGS;
import com.opencsv.CSVWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class SQLiteDatabaseAdapter extends SQLiteOpenHelper {

    @SuppressLint("StaticFieldLeak")
    private static SQLiteDatabaseAdapter mInstance;

    // public Values for database
    private String COL_1 = "_id";
    private String COL_2 = "Actions";
    private String COL_3 = "Title";
    private String COL_4 = "Message";
    private String COL_5 = "Code";
    private String COL_6 = "Supplier";
    private SQLiteDatabase WritableDatabase;

    // private static values
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_CIGMA = "Cigma";
    private static final String TABLE_WMS = "WMS";
    private static final String TABLE_SUPPLIERS = "Suppliers";
    private final String CREATE_TABLE_ONE = "CREATE TABLE " + TABLE_CIGMA + "( " + COL_1 +
            " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_2 + " TEXT ," + COL_3 + " TEXT," +
            COL_4 + " TEXT)";
    private final String CREATE_TABLE_TWO = "CREATE TABLE " + TABLE_WMS + "( " + COL_1 +
            " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_2 + " TEXT ," + COL_3 + " TEXT," + COL_4 +
            " TEXT)";
    private final String CREATE_TABLE_THREE = "CREATE TABLE " + TABLE_SUPPLIERS + "(" + COL_1 +
            " INTEGER," + COL_6 + " TEXT ," + COL_5 +
            " TEXT ,Planner TEXT,Phone TEXT,Type TEXT,Country TEXT,Parts TEXT)";
    private Context context;
    private String TAG = "DB_Adapter";
    private boolean databaseCreated = false;

    // Plug a leak use getInstance(this); to get state true / false
    public static synchronized SQLiteDatabaseAdapter getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new SQLiteDatabaseAdapter(ctx.getApplicationContext());
        }
        return mInstance;
    }


    private SQLiteDatabaseAdapter(Context context) {
        super(context, "Notes.db", null, DATABASE_VERSION);
        this.context = context;
        Log.d(TAG, "Result : " + databaseCreated);
        WritableDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(CREATE_TABLE_ONE);
            db.execSQL(CREATE_TABLE_TWO);
            db.execSQL(CREATE_TABLE_THREE);
            String lCREATE_TABLE_FOUR = "CREATE TABLE MyNotes " +
                    "( _id INTEGER PRIMARY KEY AUTOINCREMENT, Title TEXT, Notes TEXT)";
            db.execSQL(lCREATE_TABLE_FOUR);
            databaseCreated = true;

        } catch (Exception e) {
            databaseCreated = false;
            Log.d(TAG, "Failed error " + e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CIGMA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUPPLIERS);
        db.execSQL("DROP TABLE IF EXISTS MyNotes");
        onCreate(db);
    }

    /**
     * Start Modifying Database (CRUD)
     */

    // ADD
    public void insert(String TITLE, String MESSAGE) {
        new DatabaseInsert(TITLE, MESSAGE).start();
    }

    private class DatabaseInsert extends Thread {
        String NOTE;
        String MESSAGE;

        DatabaseInsert(String note, String message) {
            this.NOTE = note;
            this.MESSAGE = message;
        }

        @Override
        public void run() {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            String[] args = {NOTE, MESSAGE};

            WritableDatabase.execSQL("INSERT OR REPLACE INTO MyNotes(Title,Notes)VALUES(?,?)",args);
        }
    }

    // UPDATE
    public void Update(String id, String title, String message) {
        new DatabaseUpdate(id, title, message).start();
    }

    private class DatabaseUpdate extends Thread {
        String id;
        String title;
        String message;

        DatabaseUpdate(String nID, String nTITLE, String nMESSAGE) {
            this.id = nID;
            this.title = nTITLE;
            this.message = nMESSAGE;
        }

        @Override
        public void run() {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            String[] args = {id};
            ContentValues row = new ContentValues();
            row.put("Title", title);
            row.put("Notes", message);
            WritableDatabase.update("MyNotes", row, "_id = ?", args);
        }
    }

    // DELETE
    public void Delete(String id) {
        new DatabaseDelete(id).start();
    }

    private class DatabaseDelete extends Thread {
        String id;

        DatabaseDelete(String NewId) {
            this.id = NewId;
        }

        @Override
        public void run() {
            String[] args = {id};
            WritableDatabase.delete("MyNotes", "_id = ?", args);
        }
    }

    public void DeleteAll(String TABLE){new DeleteAll(TABLE).start();
    }

    private class DeleteAll extends Thread{
        String table;
        DeleteAll(String delete){this.table = delete;}
                @Override
        public void run() {
                    Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                  //  super.run();
            WritableDatabase.delete(table,null,null);
            WritableDatabase.execSQL("vacuum");
        }
    }

//    public Cursor FillTextView(String TABLE, String ID){
//     Cursor cursor = new Cursor(new FillTheView(TABLE,ID).start());
//
//        return cursor;
//    }
//
//    private class FillTheView extends Thread{
//        String table;
//        String id;
//        FillTheView(String Table,String Id){this.table = Table;this.id = Id;}
//
//        @Override
//        public void run() {
//            WritableDatabase.rawQuery("SELECT * FROM " + table + " WHERE _id =" + id, null);
//        }
//    }

    public Cursor FillTheTextView(String TABLE, String ID) {
        return WritableDatabase.rawQuery("SELECT * FROM " + TABLE + " WHERE _id =" + ID, null);
    }
    // Pre fill the database
    public void CSV(){
        new WriteCSVtoDatabase().start();
    }

    private class WriteCSVtoDatabase extends Thread {

        @Override
        public void run() {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

            if (databaseCreated) {
                InputStream inStream = context.getResources().openRawResource(R.raw.cigma);
                BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
                String line;
                WritableDatabase.beginTransaction();

                try {
                    while ((line = buffer.readLine()) != null) {
                        String[] columns = line.split(",");
                        if (columns.length != 3) {
                            Log.d(TAG+" Suppliers", "Skipping Bad CSV Row" + columns[0]);
                            continue;
                        }
                        ContentValues cv = new ContentValues(3);
                        cv.put(COL_2, columns[0].trim());
                        cv.put(COL_3, columns[1].trim());
                        cv.put(COL_4, columns[2].trim());
                        WritableDatabase.insert(TABLE_CIGMA, null, cv);


                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                WritableDatabase.setTransactionSuccessful();
                WritableDatabase.endTransaction();

                InputStream inStreamCigma = context.getResources().openRawResource(R.raw.suppliers);
                BufferedReader bufferCigma =
                        new BufferedReader(new InputStreamReader(inStreamCigma));
                String lineCigma;
                WritableDatabase.beginTransaction();

                try {
                    while ((lineCigma = bufferCigma.readLine()) != null) {
                        String[] columns = lineCigma.split(",");
                        if (columns.length != 8) {
                            Log.d(TAG+" Cigma", "Skipping Bad CSV Row" + columns[0]);
                            continue;
                        }
                        ContentValues cv = new ContentValues(8);
                        cv.put(COL_1, columns[0].trim());
                        cv.put(COL_6, columns[1].trim());
                        cv.put(COL_5, columns[2].trim());
                        cv.put("Planner",columns[3].trim());
                        cv.put("Phone",columns[4].trim());
                        cv.put("Country",columns[5].trim());
                        cv.put("Type",columns[6].trim());
                        cv.put("Parts",columns[7].trim());

                        WritableDatabase.insert(TABLE_SUPPLIERS, null, cv);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                WritableDatabase.setTransactionSuccessful();
                WritableDatabase.endTransaction();

                InputStream inStreamWMS = context.getResources().openRawResource(R.raw.wms);
                BufferedReader bufferWMS = new BufferedReader(new InputStreamReader(inStreamWMS));
                String lineWMS;
                WritableDatabase.beginTransaction();

                try {
                    while ((lineWMS = bufferWMS.readLine()) != null) {
                        String[] columns = lineWMS.split(",");
                        if (columns.length != 3) {
                            Log.d(TAG + " WMS", "Skipping Bad CSV Row" + columns[0]);
                            continue;
                        }
                        ContentValues cv = new ContentValues(3);
                        cv.put(COL_2, columns[0].trim());
                        cv.put(COL_3, columns[1].trim());
                        cv.put(COL_4, columns[2].trim());
                        WritableDatabase.insert(TABLE_WMS, null, cv);


                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                WritableDatabase.setTransactionSuccessful();
                WritableDatabase.endTransaction();

            }
            //db.close();
            Log.d(TAG,"Result : " + databaseCreated);
        }
    }

    // Getting All Cigma WMS Notes
    public List<MyNotesGS> getAllContacts(String TABLE) {

        List<MyNotesGS> NoteList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE;
        Cursor cursor = WritableDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MyNotesGS notes = new MyNotesGS();
                notes.setid(cursor.getString(0));
                notes.setAction(cursor.getString(1));
                notes.setTitle(cursor.getString(2));
                notes.setMessage(cursor.getString(3));
                // Adding contact to list
                NoteList.add(notes);
            } while (cursor.moveToNext());
            cursor.close();
        }
        // return list
        return NoteList;
    }


    // Getting single
    public List<MyNotesGS> getSupplierCode(String SEARCH,String ROW) {
        List<MyNotesGS> Notes = new ArrayList<>();
        Cursor cursor = WritableDatabase.rawQuery("SELECT * FROM Suppliers WHERE "+ROW+" LIKE " +
                "'%" + SEARCH + "%'", null);
        if (cursor.moveToFirst()) {
            do {
                MyNotesGS notes = new MyNotesGS();
                notes.setid(cursor.getString(0));
                notes.setCode(cursor.getString(1));
                notes.setSupplier(cursor.getString(2));
                notes.setPlanner(cursor.getString(3));
                notes.setPhone(cursor.getString(4));
                notes.setCountry(cursor.getString(5));
                notes.setType(cursor.getString(6));
                notes.setParts(cursor.getString(7));
                // Adding contact to list
                Notes.add(notes);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return  Notes;
    }

    // Getting All Notes
    public List<NoteGS> getAllNotes(String TABLE) {
        List<NoteGS> NoteList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE;
        Cursor cursor = WritableDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                NoteGS notes = new NoteGS();
                notes.setID(Integer.parseInt(cursor.getString(0)));
                notes.setTITLE(cursor.getString(1));
                notes.setNOTE(cursor.getString(2));
                // Adding contact to list
                NoteList.add(notes);
            } while (cursor.moveToNext());
            cursor.close();
        }
        // return Note list
        return NoteList;
    }

    public void CSVReader(String file,String TABLE,int row){
        new CSVReader(file,TABLE,row).start();
    }

    private class CSVReader extends Thread{

         String Get_Table;
         String Get_File;
         int Get_ROW;

        CSVReader(String file,String TABLE,int row){
            this.Get_File = file;
            this.Get_Table = TABLE;
            this.Get_ROW = row;
        }

        @Override
        public void run() {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            InputStream inStream = null;
            try {

                inStream = new FileInputStream(Get_File);
            } catch (FileNotFoundException pE) {
                pE.printStackTrace();
            }
            assert (inStream != null ? inStream : null) != null;
            BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
            String line;
            WritableDatabase.beginTransaction();
            if (Get_ROW  == 3) {
                try {
                    while ((line = buffer.readLine()) != null) {
                        String[] columns = line.split(",");
                        if (columns.length != 3) {
                            Log.d(TAG + Get_Table, " Skipping Bad CSV Row" + columns[0]);
                            continue;
                        }
                        ContentValues cv = new ContentValues(3);
                        cv.put(COL_2, columns[0].trim());
                        cv.put(COL_3, columns[1].trim());
                        cv.put(COL_4, columns[2].trim());
                        WritableDatabase.insert(Get_Table, null, cv);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (Get_ROW  == 8){
                try {
                    while ((line = buffer.readLine()) != null) {
                        String[] columns = line.split(",");
                        if (columns.length != 8) {
                            Log.d(TAG + Get_Table, " Skipping Bad CSV Row" + columns[0]);
                            continue;
                        }
                        ContentValues cv = new ContentValues(8);
                        cv.put(COL_1, columns[0].trim());
                        cv.put(COL_6, columns[1].trim());
                        cv.put(COL_5, columns[2].trim());
                        cv.put("Planner",columns[3].trim());
                        cv.put("Phone",columns[4].trim());
                        cv.put("Country",columns[5].trim());
                        cv.put("Type",columns[6].trim());
                        cv.put("Parts",columns[7].trim());
                        WritableDatabase.insert(Get_Table, null, cv);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            WritableDatabase.setTransactionSuccessful();
            WritableDatabase.endTransaction();
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void exportDB() {
        File directoryDownload =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File exportDir = new File (directoryDownload, "CVS EXPORT");
        if (!exportDir.exists())
        {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "MyNoTes.csv");
        try
        {
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            Cursor curCSV = WritableDatabase.rawQuery("SELECT * FROM MyNotes",null);
            csvWrite.writeNext(curCSV.getColumnNames());
            while(curCSV.moveToNext())
            {
                //Which column you want to export
                String arrStr[] ={curCSV.getString(0),curCSV.getString(1), curCSV.getString(2)};
                csvWrite.writeNext(arrStr);
            }
            csvWrite.close();
            curCSV.close();
        }
        catch(Exception sqlEx)
        {
            Log.e("Database ", sqlEx.getMessage(), sqlEx);
        }
    }
}