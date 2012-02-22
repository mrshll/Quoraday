package com.example;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: marshall
 * Date: 1/3/12
 * Time: 9:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class JournalActivity extends ListActivity{

    private static final int DELETE_ID = Menu.FIRST + 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.journal);
        fillData();
        registerForContextMenu(getListView());
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, R.string.delete);
    }

    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case DELETE_ID:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                System.out.println(info.id);
                //Util.deleteEntry(info.id);
                fillData();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void fillData() {
        // Get all of the entries from the database and create the item list
        JSONArray jArray = Util.getUserEntries(1);
        ArrayList<Entry> entries = new ArrayList<Entry>(10);

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();

        //First add today's question
        Entry today = new Entry();
        today.date = dateFormat.format(date).toString();
        today.question = Util.getTodaysPrompt();
        
        String userEntryToday = Util.getUserEntryToday();
        today.text = (userEntryToday=="")?userEntryToday:"Add Response";

        
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
                if(i==0 && today.date != n.date){
                    entries.add(today);
                }
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
        //Intent i = new Intent(this, NoteEdit.class);
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

