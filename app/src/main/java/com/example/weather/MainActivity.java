package com.example.weather;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloadTask task=new downloadTask();
        task.execute("https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22");
    }


    public class downloadTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... Address) {

            String result="";
            URL url;
            HttpURLConnection urlConnection=null;

            try {

                url=new URL(Address[0]);
                urlConnection=(HttpURLConnection)url.openConnection();

                InputStream in=urlConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(in);

                int data=reader.read();

                while(data!=-1){

                    char current = (char)data;
                    result+=current;
                    data=reader.read();

                }
                return result;

            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {

                JSONObject jobj=new JSONObject(result);
                String weather= jobj.getString("weather");

                JSONArray arr=new JSONArray(weather);
                for (int i=0;i<arr.length();i++){


                    JSONObject part=arr.getJSONObject(i);
                    Log.i("main",part.getString("main"));
                    Log.i("description",part.getString("description"));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.i("content",result);
        }
    }
}
