package com.example.rakesh.palleuniversity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.sql.Time;

public class SearchActivity extends AppCompatActivity {
    EditText editText1,editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
    }

    public void nameGo(View view) {


    }

    public void numberGo(View view) {
    }
}
