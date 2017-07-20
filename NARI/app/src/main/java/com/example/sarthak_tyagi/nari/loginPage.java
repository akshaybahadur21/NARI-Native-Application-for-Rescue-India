package com.example.sarthak_tyagi.nari;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.iid.InstanceID;
import com.google.firebase.database.*;
import com.google.firebase.iid.FirebaseInstanceId;


public class loginPage extends AppCompatActivity
{
    Button submit;
    TextView number,ec1,ec2;
    EditText num,enum1,enum2;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    void insertDatatoFirebase(String number,String ec1,String ec2,String token)
    {
        DatabaseReference myref = database.getReference();
        myref.child("users").child(token).child("emergencyid1").setValue(ec1);
        myref.child("users").child(token).child("emergencyid2").setValue(ec2);
        myref.child("users").child(token).child("number").setValue(number);
        myref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                Toast.makeText(loginPage.this, "Number added to firebase", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_login_page);
        submit = (Button)findViewById(R.id.button2);
        number = (TextView)findViewById(R.id.textView2);
        num = (EditText)findViewById(R.id.editText2);
        ec1 = (TextView) findViewById(R.id.textView);
        enum1 = (EditText) findViewById(R.id.editText);
        ec2 = (TextView) findViewById(R.id.textView3);
        enum2 = (EditText) findViewById(R.id.editText3);

        //getting the current location of the user in longitude and lattitude//



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseIDService fbID = new FirebaseIDService();
                fbID.onTokenRefresh();
                String token = fbID.refreshedToken;
                insertDatatoFirebase(num.getText().toString(),enum1.getText().toString(),enum2.getText().toString(),token);
                saveLogin(token);

                Intent myIntent= new Intent(loginPage.this,PanicButton.class);
                loginPage.this.startActivity(myIntent);
            }
        });






    }

    private void saveLogin(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("prefs", 1);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.putBoolean("login", true);
        editor.commit();
    }


}
