package com.example;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;

/**
 * Created by IntelliJ IDEA.
 * User: marshall
 * Date: 12/21/11
 * Time: 5:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class JournalActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InputStream is = null; 
        String result = "";
        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            URI uri = new URI("http://www.quoraday.pewpewlasers.com/getTodaysPrompt.php");
            request.setURI(uri);
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        } catch(Exception e){
            Log.e("log_tag", "Error in http connection "+e.toString());
        }
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"),8);
            StringBuilder sb = new StringBuilder(); 
            String line = null;
            while ((line = reader.readLine()) != null){
                sb.append(line + "\n");
            }
            is.close();
            
            result=sb.toString();
        } catch(Exception e){
            Log.e("log_tag", "Error converting result "+e.toString());
        }

        //parse json data
        try{
            JSONArray jArray = new JSONArray(result);
            TextView textview = new TextView(this);
            textview.setText(jArray.getJSONObject(0).getString("text"));
            setContentView(textview);
        }catch(JSONException e){
            Log.e("log_tag", "Error parsing JSON "+e.toString());
        }
    }
}