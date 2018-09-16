package com.example.rakesh.palleuniversity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    EditText editText1,editText2,editText3,editText4,editText5,editText6;
    Button button;
    Intent intent;
    MyDatabase myDatabase;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);
        editText5 = findViewById(R.id.editText5);
        editText6 = findViewById(R.id.editText6);

        button = findViewById(R.id.buttonS2);

        intent = getIntent();
        editText1.setText(intent.getStringExtra("name"));
        editText2.setText(intent.getStringExtra("no"));
        editText3.setText(intent.getStringExtra("mail"));

        myDatabase = new MyDatabase(this);
        myDatabase.open();
        cursor = myDatabase.readEmp();

    }//onCreate ends here

    /*---------------------------------------------------------------------------*/

    public void saved(View view) {
        String name = editText1.getText().toString().trim();
        String mobile = editText2.getText().toString().trim();
        String email = editText3.getText().toString().trim();

        if (name.isEmpty()){
            editText1.setError("Please enter name");
        }
        if (mobile.isEmpty()|| mobile.length()!=10){
            editText2.setError("Please enter mobile no.");
        }
        if (email.isEmpty()||!email.contains("@gmail.com")){
            editText3.setError("Please enter email");
        }

        if (!name.isEmpty()
                && !mobile.isEmpty()&& mobile.length()==10
                && !email.isEmpty()&& email.contains("@gmail.com")){
            myDatabase.insertEmp(name,mobile,email);
            startActivity(new Intent(this,DetailsActivity.class));finish();return;
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,DetailsActivity.class));finish();return;
    }

    public void date(View view) {
        final Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        editText6.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void update(View view) {
        String name = editText1.getText().toString().trim();
        String mobile = editText2.getText().toString().trim();
        String email = editText3.getText().toString().trim();

        if (name.isEmpty()){
            editText1.setError("Please enter name");
        }
        if (mobile.isEmpty()|| mobile.length()!=10){
            editText2.setError("Please enter mobile no.");
        }
        if (email.isEmpty()||!email.contains("@gmail.com")){
            editText3.setError("Please enter email");
        }

        if (!name.isEmpty()
                && !mobile.isEmpty()&& mobile.length()==10
                && !email.isEmpty()&& email.contains("@gmail.com")){

            cursor.moveToPosition(intent.getIntExtra("getAdapterPosition",0));
            int id = cursor.getInt(0);
            myDatabase.updateName(id,name,mobile,email);
            cursor = myDatabase.readEmp();
            Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,DetailsActivity.class));finish();return;
        }
    }





}
