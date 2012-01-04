package com.example;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by IntelliJ IDEA.
 * User: marshall
 * Date: 12/21/11
 * Time: 5:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class DailyBestActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the Daily Best tab");
        setContentView(textview);
    }
}