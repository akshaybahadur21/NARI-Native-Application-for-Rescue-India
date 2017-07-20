package com.example.sarthak_tyagi.nari;
import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Sarthak on 21-04-2017.
 */

public class SendMessage extends AsyncTask<String , Void, Void>{
    String rMsg;
    TextView textView2;
    private Exception exception;
    PanicButton panicButton;
    public String transfer(String x)
    {
        String temp = x;
        return temp;
    }

    @Override
    public Void doInBackground(String... params){
        // StringBuffer buf = new StringBuffer();
        int count=0;
        try{
            try{
                while(count<10) {
                    //Socket socket=new Socket("192.168.43.33",8888);
                    Socket socket = new Socket("192.168.2.3", 7777);
               /* PrintWriter outToServer=new PrintWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream()));
                outToServer.println(params[0]);
                outToServer.flush();
                */

                    System.out.println("Here");
                    InputStream in = socket.getInputStream();
                    byte data[] = new byte[2];
                    in.read(data);
                    System.out.println(data);
                    rMsg = new String(data);
                    transfer(rMsg);
                    System.out.println(rMsg.length());
                    System.out.println(rMsg);
                    count ++;
                    if(count ==9)
                    {
                        in.close();
                        socket.close();
                    }
                }





                //outToServer.close();

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        catch (Exception e){
            this.exception=e;
            return null;
        }

        return null;
    }

}
