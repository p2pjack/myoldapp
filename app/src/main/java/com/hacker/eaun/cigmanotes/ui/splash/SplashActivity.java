package com.hacker.eaun.cigmanotes.ui.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hacker.eaun.cigmanotes.Data.DataBase.SQLiteDatabaseAdapter;
import com.hacker.eaun.cigmanotes.R;
import com.hacker.eaun.cigmanotes.core.MainActivity;

import me.wangyuwei.particleview.ParticleView;

public class SplashActivity extends AppCompatActivity {
    private static String TAG = "Splash Intro";
    SQLiteDatabaseAdapter db;
    private ParticleView mPv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        db = SQLiteDatabaseAdapter.getInstance(this);
        LoadCvsOnce();
        LoadSplashOnce();
    }

    private void LoadSplashOnce() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("bol_key_first", false)) {

            ani1();
            StartAnimations();
            ani2();
        } else if (prefs.getBoolean("bol_key_first", true)) {
            GoToMain();
        }
    }

    private void LoadCvsOnce() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("bol_key_cvs", false)) {
            db.CSV();
        } else if (prefs.getBoolean("bol_key_cvs", true)) {
            GoToMain();
        }
    }

    private void GoToMain() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        Log.d(TAG, "Intro Finished");
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("bol_key_first", true);
        editor.putBoolean("bol_key_cvs", true);
        editor.apply();
    }


    /**
     * Make intent to move to MainActivity
     */

    private void ani1(){
        mPv1 = (ParticleView) findViewById(R.id.pv1);
        mPv1.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPv1.startAnim();
            }
        }, 800);
    }

    private void ani2(){
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                GoToMain();
            }
        },3700);
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        RelativeLayout l=(RelativeLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.splash);
        iv.clearAnimation();
        iv.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.push_up_out);
        anim.reset();
        TextView ivv = (TextView) findViewById(R.id.MyName);
        ivv.clearAnimation();
        ivv.startAnimation(anim);
    }
}
