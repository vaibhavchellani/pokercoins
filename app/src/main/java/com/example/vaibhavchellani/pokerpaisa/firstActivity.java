package com.example.vaibhavchellani.pokerpaisa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vaibhavchellani on 4/24/17.
 */

public class firstActivity extends AppCompatActivity {
    @BindView(R.id.nameEditText)  EditText nameEditText;
    @BindView(R.id.createTableButton) Button createTableButton;
    @BindView(R.id.joinTableButton) Button joinTableButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);
        ButterKnife.bind(this);
        createTableButton.setEnabled(false);
        joinTableButton.setEnabled(false);
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                createTableButton.setEnabled(true);
                joinTableButton.setEnabled(true);
            }
        });
        createTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),createTable.class);
                startActivity(intent);
            }
        });

    }

}
