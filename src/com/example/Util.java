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
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: marshall
 * Date: 1/3/12
 * Time: 10:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class Util {
    public static String getTodaysPrompt(){
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
            Log.e("log_tag", "Error in http connection " + e.toString());
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
            return jArray.getJSONObject(0).getString("text");
        }catch(JSONException e){
            Log.e("log_tag", "Error parsing JSON "+e.toString());
        }
        return "";
    }


    public static JSONArray getUserResponses(int id){
        ArrayList<NameValuePair> datum = new ArrayList<NameValuePair>();
        datum.add(new BasicNameValuePair("user_id", Integer.toString(id)));

        InputStream is = null;
        String result = "";
        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://quoraday.pewpewlasers.com/getUserResponses.php");
            httppost.setEntity(new UrlEncodedFormEntity(datum));

            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        } catch(Exception e){
            Log.e("log_tag", "Error in http connection " + e.toString());
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
            return jArray;
        }catch(JSONException e){
            Log.e("log_tag", "Error parsing JSON "+e.toString());
        }
        return null;
    }
}
