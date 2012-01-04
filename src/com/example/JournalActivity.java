package com.example;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import org.json.JSONArray;
import org.json.JSONException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: marshall
 * Date: 1/3/12
 * Time: 9:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class JournalActivity extends ListActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.journal);
        fillData();
    }

    private void fillData() {
        // Get all of the entries from the database and create the item list
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        JSONArray jArray = Util.getUserResponses(1);
        ArrayAdapter<Entry> entries = new ArrayAdapter<Entry>(this, R.layout.notes_row);

        //Loop though my JSONArray
        for(Integer i=0; i< jArray.length(); i++){
            try{
                //Get My JSONObject and grab the String Value that I want.
                Entry n = new Entry();
                String date_str = jArray.getJSONObject(i).getString("q_date");

                try{
                    n.date = (Date)formatter.parse(date_str);
                }catch(ParseException e){
                    Log.e("log_tag", "Parsing the date failed: " + e.toString());
                }
                n.question = jArray.getJSONObject(i).getString("q_text");
                n.text = jArray.getJSONObject(i).getString("e_text");
                n.votes = jArray.getJSONObject(i).getInt("e_votes");

                //Add the string to the list
                entries.add(n);
            }catch(JSONException e){
                Log.e("log_tag", "Parsing the JSON failed: " + e.toString());
            }
        }
        //Add my Adapter via the setListAdapter function.
        setListAdapter(entries);

        //Display the listView
        ListView lv = getListView();
    }
}
