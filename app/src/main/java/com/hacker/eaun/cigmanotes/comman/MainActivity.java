package com.hacker.eaun.cigmanotes.comman;

/**
 * Created by Eaun-Ballinger on 28/07/2016.
 * Main Functions for the program
 * 4010 Lines of code 16/09/16
 */

/**
 * Tasty Toast by Rahul Yadav
 *
 * Copyright 2016

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 Copyright [2016] [Jeason Wong of copyright owner]

 Particle Animation

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hacker.eaun.cigmanotes.Data.DataBase.SQLiteDatabaseAdapter;
import com.hacker.eaun.cigmanotes.R;
import com.hacker.eaun.cigmanotes.Utils.prefs.AppPreferences;
import com.hacker.eaun.cigmanotes.ui.MyAdapter;
import com.hacker.eaun.cigmanotes.ui.Note_UI.NoteActivity;
import com.hacker.eaun.cigmanotes.ui.Search_UI.SearchActivity;
import com.hacker.eaun.cigmanotes.ui.Tools_UI.ToolsActivity;
import com.sdsmdg.tastytoast.TastyToast;

import shortbread.Shortbread;
import shortbread.Shortcut;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener
{
    public String TABLE = "Cigma";
    public MyAdapter ca;
    public RecyclerView recList;
    public TextView FLOAT,FLOAT1;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
    private SQLiteDatabaseAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Shortbread.create(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db =  SQLiteDatabaseAdapter.getInstance(this);
        FLOAT = (TextView)findViewById(R.id.float_text);
        FLOAT1 = (TextView)findViewById(R.id.float_text1);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab1 = (FloatingActionButton)findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)findViewById(R.id.fab2);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext()
                ,R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext()
                ,R.anim.rotate_backward);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        GetMyList();

        loadPreferences();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        mMyDataListener.mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        if (mMyDataListener.mGoogleApiClient!= null && mMyDataListener.mGoogleApiClient.isConnected())
//        {
//            mMyDataListener.mGoogleApiClient.disconnect();
//        }
    }

    public void GetMyList(){
        recList  = (RecyclerView) findViewById(R.id.rv);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        ca = new MyAdapter(db.getAllList(TABLE));
        recList.setAdapter(ca);
    }
    // Animation FAB
    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        switch (id){
            case R.id.fab:
                animateFAB();
                break;
            case R.id.fab1:
                TABLE = "WMS";
                GetMyList();
            TastyToast.makeText(this,"WMS Has Been Selected",
                    TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);

                animateFAB();
                break;
            case R.id.fab2:
                TABLE = "Cigma";
            TastyToast.makeText(this,"Cigma Has Been Selected",
                    TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);
                GetMyList();
                animateFAB();
                break;
        }
    }

    private void animateFAB(){

        if(isFabOpen)
        {
            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            FLOAT.startAnimation(fab_close);
            FLOAT1.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
        }
        else
        {
            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            FLOAT.startAnimation(fab_open);
            FLOAT1.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
        }
    }
    // End
    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            Intent intent = new Intent(this,AppPreferences.class);
            startActivity(intent);
            TastyToast.makeText(this,"Settings Selected",TastyToast.LENGTH_SHORT,TastyToast.INFO);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       if (id == R.id.nav_notes){
           OnNotesShortCut();
        }
        else if (id == R.id.nav_tools)
        {
            OnToolShortCut();
        }
        else if (id == R.id.nav_share)
        {

            Toast.makeText(this, "Share Pressed", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.nav_send)
        {
            Toast.makeText(this, "Send Pressed", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.nav_supplier_search)
        {
            OnSupplierShortCut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Shortcut(id = "Tools", icon = R.drawable.menu, shortLabel = "Tools items")
    public void OnToolShortCut() {
        Intent intent = new Intent(this, ToolsActivity.class);
        startActivity(intent);
        TastyToast.makeText(this, "Tools Selected",
                TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
    }

    @Shortcut(id = "Search", icon = R.drawable.menu, shortLabel = "Supplier Search")
    public void OnSupplierShortCut() {
        TABLE = "Suppliers";
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
        TastyToast.makeText(this, "Suppliers has ben selected",
                TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
    }

    @Shortcut(id = "Notes", icon = R.drawable.menu, shortLabel = "Add a Note")
    public void OnNotesShortCut() {
        Intent intent = new Intent(this, NoteActivity.class);
        startActivity(intent);

        TastyToast.makeText(this, "My Notes Has Been Selected",
                TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
    }

    private void loadPreferences(){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean dark = sharedPreferences.getBoolean("background_colour",false);

        LinearLayout main = (LinearLayout)findViewById(R.id.setbackground);
        if (dark){
            main.setBackgroundColor(Color.parseColor("#3c3f41"));
        }
        String sharedPreferencesString = sharedPreferences.getString("title","Cigma Notes");
        setTitle(sharedPreferencesString);
    }
}

