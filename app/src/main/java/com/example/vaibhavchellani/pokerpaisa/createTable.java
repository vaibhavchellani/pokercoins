package com.example.vaibhavchellani.pokerpaisa;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                    Intent i=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);

            }
        });





    }
}
