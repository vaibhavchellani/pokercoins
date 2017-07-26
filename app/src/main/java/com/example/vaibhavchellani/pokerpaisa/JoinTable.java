package com.example.vaibhavchellani.pokerpaisa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vaibhavchellani on 4/25/17.
 */

public class JoinTable extends AppCompatActivity {
    @BindView(R.id.joinTableButton) Button joinTableButton;
    @BindView(R.id.enterLinkEditText) EditText jointablelinkedlist;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_table);
        ButterKnife.bind(this);
        joinTableButton.setEnabled(false);
        SharedPreferences msharedpreferences= PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor=msharedpreferences.edit();
        final String name = msharedpreferences.getString("name"," ");
        jointablelinkedlist.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                joinTableButton.setEnabled(true);
            }
        });
        joinTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String link=jointablelinkedlist.getText().toString();
                final DatabaseReference mdatabse= FirebaseDatabase.getInstance().getReference();
                mdatabse.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int userCoins=0;
                        if(dataSnapshot.hasChild(link)){
                            String tablekey = mdatabse.child("table").push().getKey();
                            String userkey = mdatabse.child("table").child("user").push().getKey();
                            try{
                                userCoins=dataSnapshot.child(link).child("inital-coins").getValue(Integer.class);
                                //to find value of intial coins
                            }
                                catch (Throwable e){
                                    Toast.makeText(JoinTable.this, "error"+ e, Toast.LENGTH_SHORT).show();
                                }
                            user muser=new user(name,userCoins);
                            mdatabse.child(link).child(userkey).setValue(muser);

                            //todo this creates replaces all exisitng messages
                            //error
                            ArrayList<String> newmessage=new ArrayList<String>();
                            newmessage.add(name+" has joined the table ");
                            messages newmesage=new messages(newmessage);
                            mdatabse.child(link).child("messages").setValue(newmesage);


                            Toast.makeText(JoinTable.this, " Succesfully joined Table "+name, Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(getApplicationContext(),MainActivity.class);
                            editor.putString("table_link",link);
                            editor.putString("user_key",userkey);
                            editor.apply();
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(JoinTable.this, " Table Not Found  "+name, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        //join table if node found else displa table not found
    }
}
