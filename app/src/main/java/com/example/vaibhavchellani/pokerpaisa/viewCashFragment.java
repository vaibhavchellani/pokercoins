package com.example.vaibhavchellani.pokerpaisa;

import android.os.Bundle;

import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by vaibhavchellani on 4/24/17.
 */

public class viewCashFragment extends Fragment {
    public static Fragment newInstance(){
        viewCashFragment mviewCashFragment=new viewCashFragment();
        return mviewCashFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_cash_fragment,container,false);
        return  view;
    }
}
