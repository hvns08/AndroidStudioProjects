package com.example.icarpark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Help extends AppCompatActivity {

    TextView tv;
    String question;
    String answer;
    static String PriceFromDb;
    private static final String apiurl="https://icarpark.000webhostapp.com/androidapp/faq.php";
    ListView lv;
    ArrayList<String> holder=new ArrayList<>();
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        fetchdata();

    }

    public void fetchdata()
    {

        lv = (ListView)findViewById(R.id.lv);
        class dbManager extends AsyncTask<String,Void,String>
        {

            protected void onPostExecute(String data)
            {
                try
                {
                    JSONArray ja = new JSONArray(data);
                    JSONObject jo = null;
                    holder.clear();

                    for (int i=0;i<ja.length();i++)
                    {
                        jo = ja.getJSONObject(i);
                        String question = jo.getString("question");
                        String answer = jo.getString("answer");
                        holder.add(question+ "\n" + answer);
                    }

                    ArrayAdapter<String> at = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,holder);
                    lv.setAdapter(at);

                }
                catch (Exception ex)
                {
                    Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG).show();
                }


//                PriceFromDb = data.substring(data.indexOf("Rs ") + 2 , data.indexOf("per"));
//                Toast.makeText(getApplicationContext(),PriceFromDb,Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... strings)
            {
                try
                {
                    URL url=new URL(strings[0]);
                    HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuffer data = new StringBuffer();
                    String line;

                    while ( (line=br.readLine())!=null)
                    {
                        data.append(line+"\n");
                    }

                    br.close();

                    return data.toString();


                }catch (Exception ex)
                {
                    return ex.getMessage();
                }

            }

        }


        dbManager obj = new dbManager();
        obj.execute(apiurl);

    }

}