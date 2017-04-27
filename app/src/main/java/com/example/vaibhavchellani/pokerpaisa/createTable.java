package com.example.vaibhavchellani.pokerpaisa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vaibhavchellani on 4/25/17.
 */

public class createTable extends AppCompatActivity {
    @BindView(R.id.startGameButton)
    Button startGameButton;
    @BindView(R.id.chipsEditText)
    EditText chipsEditText;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_table_layout);
        ButterKnife.bind(this);
        startGameButton.setEnabled(false);
        SharedPreferences msharedpreferences= PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor=msharedpreferences.edit();
        final String name=msharedpreferences.getString("name"," ");

        chipsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                startGameButton.setEnabled(true);
            }
        });

        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int userchips=Integer.parseInt(chipsEditText.getText().toString());
                DatabaseReference mdatabase;
                mdatabase= FirebaseDatabase.getInstance().getReference();
                String tablekey = mdatabase.child("table").push().getKey();
                user muser=new user(name,userchips);
                String userkey = mdatabase.child("table").child("user").push().getKey();
                mdatabase.child(tablekey).child(userkey).setValue(muser);
                mdatabase.child(tablekey).child("inital-coins").setValue(userchips);
                mdatabase.child(tablekey).child("pot").setValue(0);
                Toast.makeText(createTable.this, "Successfully created Table "+name, Toast.LENGTH_SHORT).show();
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                editor.putString("table_link",tablekey);
                editor.apply();
                startActivity(i);

            }
        });





    }
}
