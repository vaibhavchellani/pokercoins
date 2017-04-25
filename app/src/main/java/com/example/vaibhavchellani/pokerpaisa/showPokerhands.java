package com.example.vaibhavchellani.pokerpaisa;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by vaibhavchellani on 4/24/17.
 */

public class showPokerhands extends Fragment {
    public static Fragment newInstance(){
        showPokerhands mshowPokerhands=new showPokerhands();
        return mshowPokerhands;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_pokerhands_fragment,container,false);

        return  view;
    }
}
