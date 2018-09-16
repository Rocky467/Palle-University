package com.example.rakesh.palleuniversity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText editTextF1,editTextF2,editTextF3,editTextF4,editTextF5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        editTextF1 = findViewById(R.id.editTextF1);
        editTextF2 = findViewById(R.id.editTextF2);
        editTextF3 = findViewById(R.id.editTextF3);
        editTextF4 = findViewById(R.id.editTextF4);
        editTextF5 = findViewById(R.id.editTextF5);

    }

    public void save(View view){
        String mobno = editTextF1.getText().toString().trim();
        String email = editTextF3.getText().toString().trim();
        String password = editTextF4.getText().toString().trim();
        String repassword = editTextF5.getText().toString().trim();


        if (mobno.isEmpty()||mobno.length()!=10){
            editTextF1.setError("10 digit mobile no");
        }
        if (editTextF2.getText().toString().trim().isEmpty()){
            editTextF2.setError("Please enter Name");
        }

        if (email.isEmpty() || !email.contains("@gmail.com")){
            editTextF3.setError("Please enter Email");
        }

        if (password.isEmpty()){
            editTextF4.setError("Please enter Password");
        }

        if (repassword.isEmpty()||!password.equals(repassword)){
            editTextF5.setError("Please Re-enter Password correctly");
        }

        if (!mobno.isEmpty()&& mobno.length()==10
                &&!editTextF2.getText().toString().trim().isEmpty()
                &&!email.isEmpty() && email.contains("@gmail.com")
                &&!password.isEmpty()
                &&!repassword.isEmpty()&&password.equals(repassword)) {

            SharedPreferences sp = getSharedPreferences("EmployeeCredentials", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();

            editor.putString("mobno", editTextF1.getText().toString());
            editor.putString("name", editTextF2.getText().toString());
            editor.putString("email", editTextF3.getText().toString());
            editor.putString("password", editTextF4.getText().toString());
            editor.putString("confirmPassword", editTextF5.getText().toString());
            editor.apply();
            Toast.makeText(this, "Password changed !", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}
