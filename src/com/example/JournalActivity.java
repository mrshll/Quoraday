package com.example;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import org.json.JSONArray;
import org.json.JSONException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        JSONArray jArray = Util.getUserResponses(1);
        ArrayList<Entry> entries = new ArrayList<Entry>(10);

        //Loop though my JSONArray
        for(Integer i=0; i< Math.min(jArray.length(), 10); i++){
            try{
                //Get My JSONObject and grab the String Value that I want.
                Entry n = new Entry();
                
                n.date = jArray.getJSONObject(i).getString("q_date");
                n.question = jArray.getJSONObject(i).getString("q_text");
                n.text = jArray.getJSONObject(i).getString("e_text");
                n.votes = jArray.getJSONObject(i).getInt("e_votes");

                //Add the string to the list
                entries.add(n);
            }catch(JSONException e){
                Log.e("log_tag", "Parsing the JSON failed: " + e.toString());
            }
        }
        
        //format the objects into a list of strings for display
        ArrayAdapter<String> entryList = new ArrayAdapter<String>(this, R.layout.notes_row);
        for(Integer i=0; i < jArray.length(); i++){
            Entry e = entries.get(i);
            String o = e.date + "\t" + e.question;
            entryList.add(o);
        }
        //Add my Adapter via the setListAdapter function.
        setListAdapter(entryList);

        //Display the listView
        ListView lv = getListView();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(this, NoteEdit.class);
        /*
        i.putExtra(id);
        i.putExtra(NotesDbAdapter.KEY_TITLE, c.getString(
                c.getColumnIndexOrThrow(NotesDbAdapter.KEY_TITLE)));
        i.putExtra(NotesDbAdapter.KEY_BODY, c.getString(
                c.getColumnIndexOrThrow(NotesDbAdapter.KEY_BODY)));
        startActivityForResult(i, ACTIVITY_EDIT);
        */
    }
}

