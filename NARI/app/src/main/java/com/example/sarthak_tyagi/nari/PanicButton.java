package com.example.sarthak_tyagi.nari;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

public class PanicButton extends AppCompatActivity {
    Button panic;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String messageFrom;
    String Contact1;
    String Contact2;
    String emergencyContact1;
    String emergencyContact2;
    ArrayList<String> messageto;
    String location;
    Users currentUser;
    Subscriber currentSubscriber1;
    Subscriber currentSubscriber2;
    String AutoButton;
    private LocationManager locationManager;
    private LocationListener locationListener;
    GPSTracker gps;
    Double latitude, longitude;

    public void sendToServer(JSONObject data) {
        try {
            try {
                URL url = new URL("http://192.168.2.9:8080/json/data/post");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-type", "application/json");
                String input = (data.toString());//URLEncoder.encode,"UTF-8");
                Log.d("js", input);
                OutputStream os = conn.getOutputStream();
                os.write(input.getBytes());
                os.flush();

                if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                    throw new RuntimeException("Failed:Http error code:" + conn.getResponseCode());

                }
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String output;
                Toast.makeText(getApplicationContext(), "Connected to server", Toast.LENGTH_LONG);
                while ((output = br.readLine()) != null) {
                    System.out.println(output);
                }
                conn.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void CovertDataToJSON(String messageFrom, String location, String Contact1, String Contact2) {
        //messageto = new ArrayList<String>();
        //messageto.add(Contact1);
        //messageto.add(Contact2);
        JSONObject locdata = new JSONObject();
        try {
            locdata.put("from", messageFrom);
            locdata.put("to1", Contact1);
            locdata.put("to2", Contact2);
            locdata.put("location", location);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            sendToServer(locdata);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void buttonpress(Button btn)
    {
        if(new SendMessage().execute() != null);
        btn.performClick();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panic_button);

        //buttonpress();


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        gps=new GPSTracker(PanicButton.this);

        if(gps.canGetLocation()){
            latitude=gps.getLatitude();
            longitude=gps.getLongitude();
        }
        else{
            gps.showSettingsAlert();
        }



        panic = (Button)findViewById(R.id.button6);

        location = latitude.toString()+","+longitude.toString();
        currentUser = new Users();
        buttonpress(panic);
        panic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseIDService fbID = new FirebaseIDService();
                fbID.onTokenRefresh();
                String token = fbID.refreshedToken;
                messageFrom = token;
                DatabaseReference userReference = database.getInstance().getReference().child("users");


                //Now we want to search users by email. Here's the query for that.
                Query query = userReference.orderByKey().equalTo(token);

                //Now we are executing the search!
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // A snapshot is a representations of the data from the database
                        // We can traverse through multiple results (children of snapshots) like this.
                        for (DataSnapshot messageChild : dataSnapshot.getChildren()) {

                             currentUser =  messageChild.getValue(Users.class);
                            Log.d("en1",currentUser.getEmergencyid1());
                            Log.d("en2",currentUser.getEmergencyid2());
                            Contact1 = currentUser.getEmergencyid1();
                            Contact2 = currentUser.getEmergencyid2();

                        }

                        //CovertDataToJSON(messageFrom,token,location,Contact1,Contact2);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //Handle cancellations here
                    }
                });

                final DatabaseReference subscriberReference = database.getInstance().getReference().child("Subscriber");
                Query getToken1 = subscriberReference.equalTo(Contact1);
                getToken1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // A snapshot is a representations of the data from the database
                        // We can traverse through multiple results (children of snapshots) like this.
                        for (DataSnapshot messageChild : dataSnapshot.getChildren()) {

                            currentSubscriber1 =  messageChild.getValue(Subscriber.class);
                            Log.d("en1",currentSubscriber1.getname());
                            Log.d("en2",currentSubscriber1.getToken());
                            emergencyContact1 = currentSubscriber1.getToken();

                        }

                        //CovertDataToJSON(messageFrom,token,location,Contact1,Contact2);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //Handle cancellations here
                    }
                });

                Query getToken2 = subscriberReference.equalTo(Contact2);
                getToken2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // A snapshot is a representations of the data from the database
                        // We can traverse through multiple results (children of snapshots) like this.
                        for (DataSnapshot messageChild : dataSnapshot.getChildren()) {

                            currentSubscriber2 =  messageChild.getValue(Subscriber.class);
                            Log.d("en3",currentSubscriber2.getname());
                            Log.d("en4",currentSubscriber2.getToken());
                            emergencyContact2 = currentSubscriber2.getToken();
                            CovertDataToJSON(messageFrom,location,emergencyContact1,emergencyContact2);
                            break;

                        }



                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //Handle cancellations here
                    }
                });



            }

        });
        buttonpress(panic);
    }


}
