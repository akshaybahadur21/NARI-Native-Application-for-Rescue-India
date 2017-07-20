package com.example.sarthak_tyagi.nari;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SubscriberRegistration extends AppCompatActivity {
    TextView name;
    TextView number;
    EditText nameSubscriber;
    EditText numberSubscriber;
    Button submit;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    void WritetoFirebaseDB(String name,String number,String token)
    {
        DatabaseReference myref = database.getReference();
        myref.child("Subscriber").child(number).child("name").setValue(name);
        myref.child("Subscriber").child(number).child("token").setValue(token);
        myref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                Toast.makeText(SubscriberRegistration.this, "Number added to firebase", Toast.LENGTH_SHORT).show();
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

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriber_registration);

        name = (TextView)findViewById(R.id.textView4);
        number = (TextView)findViewById(R.id.textView5);
        nameSubscriber = (EditText)findViewById(R.id.editText4);
        numberSubscriber = (EditText)findViewById(R.id.editText5);
        submit = (Button)findViewById(R.id.button5);
        submit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                FirebaseIDService fbID = new FirebaseIDService();
                fbID.onTokenRefresh();
                String token = fbID.refreshedToken;
                WritetoFirebaseDB(nameSubscriber.getText().toString(),numberSubscriber.getText().toString(),token);
            }
        });

    }
}
