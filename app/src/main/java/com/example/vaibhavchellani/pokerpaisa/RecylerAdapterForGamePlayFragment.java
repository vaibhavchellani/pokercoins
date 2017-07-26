package com.example.vaibhavchellani.pokerpaisa;

/**
 * Created by vaibhavchellani on 4/28/17.
 */
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class RecylerAdapterForGamePlayFragment extends RecyclerView.Adapter<RecylerAdapterForGamePlayFragment.MyViewHolder> {
    private ArrayList<String> messages;
    SharedPreferences msharedprefs;
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView messageTextView,potTextView;
        public MyViewHolder(View itemView) {
            super(itemView);
            messageTextView=(TextView) itemView.findViewById(R.id.mainTextViewRow);
            potTextView=(TextView)itemView.findViewById(R.id.potValueTextView);
            msharedprefs= PreferenceManager.getDefaultSharedPreferences(itemView.getContext());

        }
    }

    public RecylerAdapterForGamePlayFragment(ArrayList<String> messages) {
        this.messages = messages;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.gamefragment_row,parent,false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        String newmessage=messages.get(position);
        holder.messageTextView.setText(newmessage);

        final String tablelink =msharedprefs.getString("table_link","NO LINK YET ");
        String userKey=msharedprefs.getString("user_key","null");
        DatabaseReference mdatabseReference= FirebaseDatabase.getInstance().getReference().child(tablelink).child(userKey).child("userCoins");
        mdatabseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int userChips=dataSnapshot.getValue(Integer.class);
                holder.potTextView.setText(Integer.toString(userChips));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("inside adapter",databaseError.toException().toString());
            }
        });

    }


    @Override
    public int getItemCount() {
        return messages.size();
    }

}
