package com.CS5520.sleepforest;

import android.os.Bundle;

import com.CS5520.sleepforest.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;

import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements OnRegisterSuccessListener {

   // private ScreenReceiver screenReceiver;

    private AppBarConfiguration mAppBarConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

//        screenReceiver = new ScreenReceiver();
//        IntentFilter screenStatusIF = new IntentFilter();
//        screenStatusIF.addAction(Intent.ACTION_SCREEN_ON);
//        screenStatusIF.addAction(Intent.ACTION_SCREEN_OFF);
//        registerReceiver(screenReceiver, screenStatusIF);

//        ToolsFragment tools = (ToolsFragment) getSupportFragmentManager().findFragmentById(R.id.nav_tools);
//        tools.setOnRegisterSuccessListener(this);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

//    @Override
//    public void onAttachFragment(Fragment fragment) {
//        if(fragment instanceof ToolsFragment){
//           ToolsFragment toolsFragment= (ToolsFragment) fragment;
//           toolsFragment.setOnRegisterSuccessListener(this);
//
//
//        }
//
//    }
    @Override
    public void onRegisterSuccess(Calendar time) {
        HomeFragment home = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.nav_home);
        if (home != null){
        home.setBedtime(time);
    }
       else{
           HomeFragment newHome = new HomeFragment();
           newHome.setBedtime(time);

           FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

           // Replace whatever is in the fragment_container view with this fragment,
           // and add the transaction to the back stack so the user can navigate back
           transaction
                   .setCustomAnimations(R.anim.nav_default_enter_anim,
                           R.anim.nav_default_exit_anim)
                   .replace(R.id.nav_host_fragment, newHome)
                   .hide(newHome);
           transaction.addToBackStack(null);

           // Commit the transaction
           transaction.commit();

        }

       }


    }
