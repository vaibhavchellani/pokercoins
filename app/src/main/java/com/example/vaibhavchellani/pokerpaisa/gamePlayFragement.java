package com.example.vaibhavchellani.pokerpaisa;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vaibhavchellani on 4/24/17.
 */

public class gamePlayFragement extends Fragment {
    public static Fragment newInstance(){
        gamePlayFragement mgameplayFragment=new gamePlayFragement();
        return mgameplayFragment;
    }
    @BindView(R.id.tableLinkEditText) EditText tableLinkEditText;
    @BindView(R.id.mainTextview) TextView mainTextview;
    @BindView(R.id.betBiutton) Button betButton;
    @BindView(R.id.takeButton) Button takeButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_play_fragment,container,false);
        ButterKnife.bind(this,view);

        SharedPreferences msharedprefs= PreferenceManager.getDefaultSharedPreferences(getContext());
        String tablelink =msharedprefs.getString("table_link","NO LINK YET ");
        tableLinkEditText.setText(tablelink);
        String maintext="";

        return  view;
    }


}
