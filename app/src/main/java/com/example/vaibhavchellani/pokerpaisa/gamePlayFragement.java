package com.example.vaibhavchellani.pokerpaisa;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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


    private ArrayList<String> messages=new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;
    private RecylerAdapterForGamePlayFragment madapter;
    private DatabaseReference mdatareference;
    @BindView(R.id.tableLinkEditText) EditText tableLinkEditText;
    @BindView(R.id.betBiutton) Button betButton;
    @BindView(R.id.takeButton) Button takeButton;
    @BindView(R.id.TextRecylerView )  RecyclerView mRecyclerView;
    @BindView(R.id.seekBar) SeekBar mseekbar;
    @BindView(R.id.potTextView) TextView potValueTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_play_fragment,container,false);
        ButterKnife.bind(this,view);

        SharedPreferences msharedprefs= PreferenceManager.getDefaultSharedPreferences(getContext());
        final String tablelink =msharedprefs.getString("table_link","NO LINK YET ");
        tableLinkEditText.setText(tablelink);
        final String user_key=msharedprefs.getString("user_key","null");
        mdatareference= FirebaseDatabase.getInstance().getReference();
        madapter=new RecylerAdapterForGamePlayFragment(messages);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(madapter);

        mdatareference.child(tablelink).child("messages").child("messages").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String newMessage = dataSnapshot.getValue(String.class);
                messages.add(newMessage);
                madapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        final DatabaseReference mdatabaseReference=FirebaseDatabase.getInstance().getReference().child(tablelink).child(user_key).child("userCoins");
        mdatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final int coins ;
                coins =dataSnapshot.getValue(Integer.class);
                mseekbar.setMax(coins);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.toException().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        final DatabaseReference potReference=FirebaseDatabase.getInstance().getReference().child(tablelink).child("pot");
        potReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                potValueTextView.setText(Integer.toString(dataSnapshot.getValue(Integer.class)));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        final int[] seekbarProgress = new int[1];
        mseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                betButton.setText("BET "+Integer.toString(mseekbar.getProgress()) +" ?");
                seekbarProgress[0] =progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        final DatabaseReference usercoinsReference=FirebaseDatabase.getInstance().getReference().child(tablelink).child(user_key).child("userCoins");
        betButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usercoinsReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int existingUserCoins=dataSnapshot.getValue(Integer.class);
                        int newCoins=existingUserCoins-seekbarProgress[0];
                        usercoinsReference.setValue(newCoins);

                        potReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                int potValue=dataSnapshot.getValue(Integer.class);
                                potReference.setValue(potValue+seekbarProgress[0]);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });




        takeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                potReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final int potValue=dataSnapshot.getValue(Integer.class);
                        if(potValue==0){
                            Toast.makeText(getContext(), "Nothing To take from POT", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            potReference.setValue(0);
                            usercoinsReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    int userCoins=dataSnapshot.getValue(Integer.class);
                                    usercoinsReference.setValue(userCoins+potValue);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        return  view;
    }


}
