package com.example.vaibhavchellani.pokerpaisa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

/*
    @BindView(R.id.message) TextView mTextMessage;
*/


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    addviewCashFragment();
                    return true;
                case R.id.navigation_dashboard:
                    addgameplayFragment();
/*
                    mTextMessage.setText(R.string.title_dashboard);
*/
                    return true;
                case R.id.navigation_notifications:
                    addpokerhandsFragment();
/*
                    mTextMessage.setText(R.string.title_notifications);
*/
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }
    private void addviewCashFragment(){
        Fragment fg = viewCashFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.layout,fg).commit();
    }
    private void addgameplayFragment(){
        Fragment fg = gamePlayFragement.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.layout,fg).commit();
    }
    private void addpokerhandsFragment(){
        Fragment fg = showPokerhands.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.layout,fg).commit();
    }


}
