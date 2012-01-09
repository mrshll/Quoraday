package com.example;


import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Created by IntelliJ IDEA.
 * User: marshall
 * Date: 12/21/11
 * Time: 5:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class TodaysJournalActivity extends Activity {

    private TextView mTitleText;
    private EditText mBodyText;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_edit);
        setTitle(R.string.edit_note);

        mTitleText = (TextView) findViewById(R.id.edit_title);
        mBodyText = (EditText) findViewById(R.id.body);
        Button confirmButton = (Button) findViewById(R.id.confirm);
        mTitleText.setText(Util.getTodaysPrompt());

        confirmButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Util.createEntry(0, Util.getTodaysPromptID(), mBodyText.getText().toString());
            }
        });
    }
}