package com.example.android.ambulanceapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by android on 24/12/15.
 */
public class BackgroundTask extends AsyncTask<String,Void,String> {
    AlertDialog alertDialog;

    Context ctx;
    static String user;
    static Vector u;



    BackgroundTask(Context ctx) {
        this.ctx = ctx;


    }

    protected void onPreExecute() {
        alertDialog=new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("Login Information");

    }



    protected void onProgressUpdate(Void... values)
    {

        super.onProgressUpdate(values);

    }

    protected void onPostExecute(String res)
    {
        if(res.equals("Registration Done"))
        {
            Toast.makeText(ctx,res,Toast.LENGTH_LONG).show();

//     }else if(res.equals("Fails")){
//        Toast.makeText(ctx,res,Toast.LENGTH_LONG).show();
        }
        else {
            user = res;
            u = new Vector();
            for(String s : user.split(" ")) {
                u.add(s);
                Log.d("VVVV", u.toString());

            }


        }
    }

    protected String doInBackground(String... params) {
        String reg_url = "http://192.168.2.8/android/Register.php";
        String login_url = "http://192.168.2.8/android/Login.php";
        String method=params[0];
        if (method.equals("register")) {
            String name = params[1];
            String user_name = params[2];
            String user_pass = params[3];
            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                        URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&" +
                        URLEncoder.encode("user_pass", "UTF-8") + "=" + URLEncoder.encode(user_pass, "UTF-8");


                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                return "Registration Done";


            } catch (MalformedInputException e) {
                e.printStackTrace();


            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        else if(method.equals("login"))
        {
            String login_name=params[1];
            String login_pass=params[2];

            try
            {
                URL url=new URL(login_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data=URLEncoder.encode("login_name","UTF-8")+"="+URLEncoder.encode(login_name,"UTF-8")+"&"+
                        URLEncoder.encode("login_pass","UTF-8")+"="+URLEncoder.encode(login_pass,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response="";
                String line="";
                while((line=bufferedReader.readLine())!=null)
                {

                    response += line;

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;


            }catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }


        }


        return "Failed";

    }
}