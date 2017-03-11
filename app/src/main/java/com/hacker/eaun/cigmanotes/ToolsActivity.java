package com.hacker.eaun.cigmanotes;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hacker.eaun.cigmanotes.adapters.FileChooser;
import com.hacker.eaun.cigmanotes.adapters.SQLiteDatabaseAdapter;
import com.sdsmdg.tastytoast.TastyToast;
import com.vstechlab.easyfonts.EasyFonts;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ToolsActivity extends AppCompatActivity {

    private SQLiteDatabaseAdapter db;
    private Button mGET_CVS,mEXPORT_CVS,mCALCULATOR,mLAUNCH_CALCULATOR;
    private String mTABLE;
    private String mfilename;
    private RadioButton mWMS,mCIGMA,mSUPPLIERS;
    private int mROWS = 3;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);
        db =  SQLiteDatabaseAdapter.getInstance(this);
        mGET_CVS = (Button)findViewById(R.id.Get_Csv);
        mGET_CVS.setTypeface(EasyFonts.caviarDreamsBold(this));
        mEXPORT_CVS =(Button)findViewById(R.id.button_Export_csv);
        mEXPORT_CVS.setTypeface(EasyFonts.caviarDreamsBold(this));
        mCALCULATOR =(Button)findViewById(R.id.button_calaulator);
        mCALCULATOR.setTypeface(EasyFonts.caviarDreamsBold(this));
        mLAUNCH_CALCULATOR = (Button)findViewById(R.id.button_launch_calculator);
        mLAUNCH_CALCULATOR.setTypeface(EasyFonts.caviarDreamsBold(this));
        mWMS =(RadioButton)findViewById(R.id.radioButton_WMS);
        mWMS.setTypeface(EasyFonts.caviarDreamsBold(this));
        mCIGMA =(RadioButton)findViewById(R.id.radioButton_CIGMA);
        mCIGMA.setTypeface(EasyFonts.caviarDreamsBold(this));
        mSUPPLIERS = (RadioButton)findViewById(R.id.radioButton_SUPPLIERS);
        mSUPPLIERS.setTypeface(EasyFonts.caviarDreamsBold(this));
        TextView lONE = (TextView) findViewById(R.id.textView_tools_import_csv);
        lONE.setTypeface(EasyFonts.caviarDreamsBold(this));
        TextView lTWO = (TextView) findViewById(R.id.textView_tools_export_cvs);
        lTWO.setTypeface(EasyFonts.caviarDreamsBold(this));
        TextView lCalculator = (TextView) findViewById(R.id.textView_calculator);
        lCalculator.setTypeface(EasyFonts.caviarDreamsBold(this));

        GetTheClicks();
    }

    private void GetTheClicks(){
        mGET_CVS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                Get_Read();
                TastyToast.makeText(ToolsActivity.this,"File imported",
                        TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);
            }
        });
        mEXPORT_CVS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                Get_Write();
                TastyToast.makeText(ToolsActivity.this,"Saved",
                        TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);
            }
        });
        mWMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                mTABLE ="WMS";
                mROWS = 3;
            }
        });
        mCIGMA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                mTABLE = "Cigma";
                mROWS = 3;
            }
        });
        mSUPPLIERS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                mTABLE = "Suppliers";
                mROWS = 8;
            }
        });
        mCALCULATOR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                TastyToast.makeText(ToolsActivity.this,"Clicked",
                        TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);
                Intent lIntent = new Intent(ToolsActivity.this,CalculatorActivity.class);
                startActivity(lIntent);
                finish();
            }
        });
        mLAUNCH_CALCULATOR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                GET_CALCULATOR();
            }
        });
    }

    private void processFile(){
        FileChooser lFileChooser = new FileChooser(ToolsActivity.this);
        lFileChooser.setFileListener(new FileChooser.FileSelectedListener() {
            @Override
            public void fileSelected(final File file) {
                // ....do something with the file
                mfilename = file.getAbsolutePath();
                Log.d("File", mfilename +" "+ mTABLE);
                // then actually do something in another module
                    db.DeleteAll(mTABLE);
                    db.CSVReader(mfilename,mTABLE,mROWS);
            }
        });
// Set up and filter my extension I am looking for
        lFileChooser.setExtension(".csv");
        lFileChooser.showDialog();
    }


    private void Get_Read() {
        int hasWriteContactsPermission = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hasWriteContactsPermission = checkSelfPermission
                    (Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale
                        (Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showMessageOKCancel("You need to allow access to Contacts",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]
                                                {Manifest.permission.READ_EXTERNAL_STORAGE},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        }
        processFile();
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(ToolsActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void Get_Write() {
        int hasWriteContactsPermission = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hasWriteContactsPermission = checkSelfPermission
                    (Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale
                        (Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    showMessageOKCancel("You need to allow access to Contacts",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]
                                                {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        }
        db.exportDB();
    }

    private void GET_CALCULATOR(){
        ArrayList<HashMap<String,Object>> items = new ArrayList<>();
        PackageManager pm;
        pm = getPackageManager();
        List<PackageInfo> packs = pm.getInstalledPackages(0);
        for (PackageInfo pi : packs) {
            if(pi.packageName.toLowerCase().contains("calcul")){
                HashMap<String, Object> map = new HashMap<>();
                map.put("appName", pi.applicationInfo.loadLabel(pm));
                map.put("packageName", pi.packageName);
                items.add(map);
            }
        }

        if(items.size()>=1){
            String packageName = (String) items.get(0).get("packageName");
            Intent i = pm.getLaunchIntentForPackage(packageName);
            if (i != null)
                startActivity(i);
        }
        else{
            TastyToast.makeText(this," No Calculator found !!!!!! ",TastyToast.LENGTH_SHORT,TastyToast.WARNING);
        }
    }

}
