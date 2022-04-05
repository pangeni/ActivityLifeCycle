package com.example.activitylifecyclestate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public static final int TEXT_REQUEST = 1;
    private ShopList items = new ShopList();
    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if ((savedInstanceState != null)&& (savedInstanceState.getSerializable("list")!= null)){
            HashMap<String, Integer> l = (HashMap<String, Integer>)savedInstanceState.getSerializable("list");
            TextView tv = findViewById(R.id.textView);
            tv.setText("");
            for (String k : l.keySet()) {
                String s = l.get(k).toString() + " " + k + "\n";
                tv.setText(tv.getText() + s);
                for(int i = 0; i<l.get(k); i++){
                    items.addItem(k);}
            }
        }
    }
//    Launch SecondActivity Handles the onClick for the "Send" button. Gets the value of the main EditText,
//    creates an intent, and launches the second activity with that intent.
//    The return intent from the second activity is onActivityResult().
    public void launchSecondActivity(View view) {

        Intent intent = new Intent(this, SecondActivity.class);

        startActivityForResult(intent, TEXT_REQUEST);
    }
//   onSaveInstanceState helps to store value when its rotate.
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable("list", items.getItems());
    }

//    onActivityResult Handles the data in the return intent from SecondActivity.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TEXT_REQUEST) {
            if (resultCode == RESULT_OK) {
                String item = data.getStringExtra(SecondActivity.EXTRA_MESSAGE);
                items.addItem(item);
            }
            drawView();
        }
    }

    public void drawView() {
        HashMap<String, Integer> l = items.getItems();
        TextView tv = findViewById(R.id.textView);
        tv.setText("");
        for (String k : l.keySet()) {
            String s = l.get(k).toString() + " " + k + "\n";
            tv.setText(tv.getText() + s);
        }
    }
}