package com.hacker.eaun.cigmanotes.ui.splash;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import me.wangyuwei.particleview.ParticleView;

import com.hacker.eaun.cigmanotes.core.MainActivity;
import com.hacker.eaun.cigmanotes.R;
import com.hacker.eaun.cigmanotes.Data.DataBase.SQLiteDatabaseAdapter;

public class SplashActivity extends AppCompatActivity {

    private ParticleView mPv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SQLiteDatabaseAdapter db = SQLiteDatabaseAdapter.getInstance(this);
        db.CSV();
        ani1();
        StartAnimations();
        ani2();
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
                MoveToMain();
            }
        },3700);
    }
    private void MoveToMain(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
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
