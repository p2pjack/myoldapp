package com.hacker.eaun.cigmanotes.ui.Calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.hacker.eaun.cigmanotes.R;
import com.sdsmdg.tastytoast.TastyToast;
import com.vstechlab.easyfonts.EasyFonts;

import java.util.Objects;

public class CalculatorActivity extends AppCompatActivity {

    /*
       Set the buttons
    */

    private Button mReceiving_Space;
    private Button mFloor_Space;
    private TextView mOUTPUT;
    private EditText mLOADS;
    private EditText mTIME;
    private EditText mPALLET;
    private EditText mFLOOR_STACKED;
    private EditText mFLOOR_PALLET;
    private double mloads,mtime,mpallet,mOutputDouble,pallet_num ,
            pallet_stacked,pallet_length,pallet_width,urate,mFORKLIFT_WIDTH,
    RECEIVING_PALLET_WIDTH,RECEIVING_PALLET_LENGTH;
    private Spinner mPALLET_SIZE,mFORKLIFT,mRATE_OF_USE,mRECEIVING_PALLET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        mReceiving_Space = (Button)findViewById(R.id.button_RECEIVING);
        mFloor_Space =(Button)findViewById(R.id.button_Floor);

        mLOADS = (EditText)findViewById(R.id.editText_LOADS);
        mTIME = (EditText)findViewById(R.id.editText_TIME);
        mPALLET = (EditText)findViewById(R.id.editText_PALLETS);
        mFLOOR_STACKED = (EditText)findViewById(R.id.editText_stacked_high);
        mFLOOR_PALLET = (EditText)findViewById(R.id.editText_floor_depth);

        mOUTPUT = (TextView)findViewById(R.id.textview_output);
        mOUTPUT.setTypeface(EasyFonts.caviarDreamsBold(this));

        mRECEIVING_PALLET = (Spinner) findViewById(R.id.spinner_receiving);
        mPALLET_SIZE = (Spinner) findViewById(R.id.spinner_floor);
        mFORKLIFT = (Spinner) findViewById(R.id.spinner_forklift);
        mRATE_OF_USE = (Spinner)findViewById(R.id.spinner_rate);

        pallet_receiving();
        pallet();
        forklift();
        Usage_Rate();
        GetTheClick();
    }

    private void pallet_receiving(){
        ArrayAdapter lPALLET_ADAPTER = ArrayAdapter.createFromResource(
                this, R.array.pallet_size, R.layout.spinner_item);
        lPALLET_ADAPTER.setDropDownViewResource(R.layout.spinner_dropdown_item);
       mRECEIVING_PALLET.setAdapter(lPALLET_ADAPTER);
    }

    private void pallet(){
        ArrayAdapter lPALLET_ADAPTER = ArrayAdapter.createFromResource(
                this, R.array.pallet_size, R.layout.spinner_item);
        lPALLET_ADAPTER.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mPALLET_SIZE.setAdapter(lPALLET_ADAPTER);
    }

    private void forklift(){
        ArrayAdapter lRATE_ADAPTER = ArrayAdapter.createFromResource(
                this, R.array.rate_of_use, R.layout.spinner_item);
        lRATE_ADAPTER.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mRATE_OF_USE.setAdapter(lRATE_ADAPTER);
    }

    private void Usage_Rate(){
        ArrayAdapter lFORKLIFT_ADAPTER = ArrayAdapter.createFromResource(
                this, R.array.forklift_type, R.layout.spinner_item);
        lFORKLIFT_ADAPTER.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mFORKLIFT.setAdapter(lFORKLIFT_ADAPTER);
    }

    private void  GetTheClick(){

        mReceiving_Space.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {

                    ReceivingSpace();
                MessageToast("Clicked Receiving");
            }
        });

        mFloor_Space.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                FloorSpace();
                MessageToast("Clicked Floor");
            }
        });
    }

    private void GetTheNumbers(){
        try {
            if (Objects.equals(mLOADS.getText().toString(), "")){
                mloads = 0.0;
            }else {
                mloads = Double.parseDouble(mLOADS.getText().toString());
            }
            if (Objects.equals(mTIME.getText().toString(), "")){
                mtime = 0.0;
            }else {
                mtime = Double.parseDouble(mTIME.getText().toString());
            }
            if (Objects.equals(mPALLET.getText().toString(),"")){
                mpallet = 0.0;
            }else {
                mpallet = Double.parseDouble(mPALLET.getText().toString());
            }
            if (Objects.equals(mFLOOR_STACKED.getText().toString(),"")){
                pallet_stacked = 0.0;
            }else {
                pallet_stacked = Double.parseDouble(mFLOOR_STACKED.getText().toString());
            }
            if (Objects.equals(mFLOOR_PALLET.getText().toString(),"")){
                pallet_num = 0.0;
            }else {
                pallet_num = Double.parseDouble(mFLOOR_PALLET.getText().toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void MessageToast(String message){
        TastyToast.makeText(this,message,TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);
    }

    private void ReceivingSpace(){
        GetTheNumbers();spin_val_pallet_receiving();
        Double WIDTH,LENGTH;
        WIDTH = RECEIVING_PALLET_WIDTH;
        LENGTH = RECEIVING_PALLET_LENGTH;
        String Result;
        mOutputDouble = Math.ceil((mloads*mtime/60)/7.5)*(mpallet*(WIDTH*LENGTH));
        Result =String.valueOf(mOutputDouble)+" Square Meters";
        mOUTPUT.setText(Result);
    }

    private void FloorSpace(){
        spin_val_pallet();spin_val_forklift();spin_val_rate();
        GetTheNumbers();
        String Result;Double NEW_WIDTH_VALUE = 0.0;
        if (mFORKLIFT_WIDTH < pallet_width){
            NEW_WIDTH_VALUE = pallet_width;}
        if (mFORKLIFT_WIDTH > pallet_width){
            NEW_WIDTH_VALUE = mFORKLIFT_WIDTH;}

        mOutputDouble =Math.ceil(((pallet_num/pallet_stacked)*
                (pallet_length*NEW_WIDTH_VALUE))/urate);
        Result = String.valueOf(mOutputDouble)+" Square Meters";
        mOUTPUT.setText(Result);
    }

    private void spin_val_pallet_receiving(){
        switch (String.valueOf(mRECEIVING_PALLET.getSelectedItem())){
            case "EUR, EUR 1 : 800 x 1200 W x L":RECEIVING_PALLET_WIDTH = 0.8;
                RECEIVING_PALLET_LENGTH = 1.2;break;
            case "Euro 2 : 1200 x 1000 W x L":RECEIVING_PALLET_WIDTH = 1.2;
                RECEIVING_PALLET_LENGTH = 1;break;
            case "Euro 3 : 1000 x 1200 W x L":RECEIVING_PALLET_WIDTH = 1;
                RECEIVING_PALLET_LENGTH = 1.2;break;
            case "Euro 6 : 800 x 600 W x L":RECEIVING_PALLET_WIDTH = 0.8;
                RECEIVING_PALLET_LENGTH = 0.6;break;
        }
    }

    private void spin_val_pallet(){
        switch (String.valueOf(mPALLET_SIZE.getSelectedItem())){
            case "EUR, EUR 1 : 800 x 1200 W x L":pallet_width = 31.50;pallet_length = 47.24;break;
            case "Euro 2 : 1200 x 1000 W x L":pallet_width = 47.24;pallet_length = 39.37;break;
            case "Euro 3 : 1000 x 1200 W x L":pallet_width = 39.37;pallet_length = 47.24;break;
            case "Euro 6 : 800 x 600 W x L":pallet_width = 31.50;pallet_length = 23.62;break;
        }
    }

    private void spin_val_forklift(){
        switch (String.valueOf(mFORKLIFT.getSelectedItem())){
            case "No FLT Value":mFORKLIFT_WIDTH = 0.0;break;
            case "Counter Balance Toyota Width 1110 mm 4 Ton":mFORKLIFT_WIDTH = 43.70;break;
            case "Counter Balance Toyota Width 1065 mm 2 Ton":mFORKLIFT_WIDTH = 41.92;break;
            case "Counter Balance Toyota Width 945 mm 1.5 Ton":mFORKLIFT_WIDTH =37.20;break;
            case "Reach Truck Toyota Width 1270 mm":mFORKLIFT_WIDTH = 50.0;break;
            case "Bendi Truck Width 1480 mm":mFORKLIFT_WIDTH = 58.26;break;
            case "Aisle Master 1400 mm 2 Ton":mFORKLIFT_WIDTH = 55.11;break;
        }
    }

    private void spin_val_rate(){

        switch (String.valueOf(mRATE_OF_USE.getSelectedItem())){
            case "5%":urate = 5.0;break;
            case "10%":urate = 10.0;break;
            case "20%":urate = 20.0;break;
            case "40%":urate = 40.0;break;
            case "60%":urate = 60.0;break;
            case "80%":urate = 80.0;break;
            case "90%":urate = 90.0;break;
        }
    }
}
