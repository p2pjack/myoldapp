package com.hacker.eaun.cigmanotes.ui.Tools_UI;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hacker.eaun.cigmanotes.Data.DataBase.SQLiteDatabaseAdapter;
import com.hacker.eaun.cigmanotes.R;
import com.hacker.eaun.cigmanotes.Utils.FileChooser;
import com.hacker.eaun.cigmanotes.ui.Calculator.CalculatorActivity;
import com.sdsmdg.tastytoast.TastyToast;
import com.vstechlab.easyfonts.EasyFonts;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ToolsFragment extends Fragment {

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private SQLiteDatabaseAdapter db;
    private Button mGET_CVS, mEXPORT_CVS, mCALCULATOR, mLAUNCH_CALCULATOR;
    private String mTABLE;
    private String mfilename;
    private RadioButton mWMS, mCIGMA, mSUPPLIERS;
    private int mROWS = 3;
    private Context mContext;
    private View mView;

    public ToolsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mRootView = inflater.inflate(R.layout.fragment_tools, container, false);

        db = SQLiteDatabaseAdapter.getInstance(mContext);
        mGET_CVS = (Button) mRootView.findViewById(R.id.Get_Csv);
        mGET_CVS.setTypeface(EasyFonts.caviarDreamsBold(mContext));
        mEXPORT_CVS = (Button) mRootView.findViewById(R.id.button_Export_csv);
        mEXPORT_CVS.setTypeface(EasyFonts.caviarDreamsBold(mContext));
        mCALCULATOR = (Button) mRootView.findViewById(R.id.button_calaulator);
        mCALCULATOR.setTypeface(EasyFonts.caviarDreamsBold(mContext));
        mLAUNCH_CALCULATOR = (Button) mRootView.findViewById(R.id.button_launch_calculator);
        mLAUNCH_CALCULATOR.setTypeface(EasyFonts.caviarDreamsBold(mContext));
        mWMS = (RadioButton) mRootView.findViewById(R.id.radioButton_WMS);
        mWMS.setTypeface(EasyFonts.caviarDreamsBold(mContext));
        mCIGMA = (RadioButton) mRootView.findViewById(R.id.radioButton_CIGMA);
        mCIGMA.setTypeface(EasyFonts.caviarDreamsBold(mContext));
        mSUPPLIERS = (RadioButton) mRootView.findViewById(R.id.radioButton_SUPPLIERS);
        mSUPPLIERS.setTypeface(EasyFonts.caviarDreamsBold(mContext));
        TextView lONE = (TextView) mRootView.findViewById(R.id.textView_tools_import_csv);
        lONE.setTypeface(EasyFonts.caviarDreamsBold(mContext));
        TextView lTWO = (TextView) mRootView.findViewById(R.id.textView_tools_export_cvs);
        lTWO.setTypeface(EasyFonts.caviarDreamsBold(mContext));
        TextView lCalculator = (TextView) mRootView.findViewById(R.id.textView_calculator);
        lCalculator.setTypeface(EasyFonts.caviarDreamsBold(mContext));

        GetTheClicks();
        return mRootView;
    }

    private void GetTheClicks() {
        mGET_CVS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                Get_Read();
                TastyToast.makeText(mContext, "File imported",
                        TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
            }
        });
        mEXPORT_CVS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                Get_Write();
                TastyToast.makeText(mContext, "Saved",
                        TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
            }
        });
        mWMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                mTABLE = "WMS";
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
                TastyToast.makeText(mContext, "Clicked",
                        TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                Intent lIntent = new Intent(mContext, CalculatorActivity.class);
                startActivity(lIntent);
                getActivity().finish();
            }
        });
        mLAUNCH_CALCULATOR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                GET_CALCULATOR();
            }
        });
    }

    private void processFile() {
        FileChooser lFileChooser = new FileChooser(getActivity());
        lFileChooser.setFileListener(new FileChooser.FileSelectedListener() {
            @Override
            public void fileSelected(final File file) {
                // ....do something with the file
                mfilename = file.getAbsolutePath();
                Log.d("File", mfilename + " " + mTABLE);
                // then actually do something in another module
                db.DeleteAll(mTABLE);
                db.CSVReader(mfilename, mTABLE, mROWS);
            }
        });
// Set up and filter my extension I am looking for
        lFileChooser.setExtension(".csv");
        lFileChooser.showDialog();
    }


    private void Get_Read() {
        int hasWriteContactsPermission = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hasWriteContactsPermission = mContext.checkSelfPermission
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
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        }
        processFile();
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(mContext)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void Get_Write() {
        int hasWriteContactsPermission = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hasWriteContactsPermission = mContext.checkSelfPermission
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
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        }
        db.exportDB();
    }

    private void GET_CALCULATOR() {
        ArrayList<HashMap<String, Object>> items = new ArrayList<>();
        PackageManager pm;
        pm = mContext.getPackageManager();
        List<PackageInfo> packs = pm.getInstalledPackages(0);
        for (PackageInfo pi : packs) {
            if (pi.packageName.toLowerCase().contains("calcul")) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("appName", pi.applicationInfo.loadLabel(pm));
                map.put("packageName", pi.packageName);
                items.add(map);
            }
        }

        if (items.size() >= 1) {
            String packageName = (String) items.get(0).get("packageName");
            Intent i = pm.getLaunchIntentForPackage(packageName);
            if (i != null)
                startActivity(i);
        } else {
            TastyToast.makeText(mContext, " No Calculator found !!!!!! ", TastyToast.LENGTH_SHORT, TastyToast.WARNING);
        }
    }

}
