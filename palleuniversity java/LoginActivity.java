package com.example.rakesh.palleuniversity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    ImageView imageView1,imageView2,imageView3;
    TextView textView;
    Button button1,button2,button3,button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sp = getSharedPreferences("EmployeeCredentials", Context.MODE_PRIVATE);
        final String sName = sp.getString("name",null);

        imageView1 = findViewById(R.id.imageview1);
        imageView2 = findViewById(R.id.imageview2);
        imageView3 = findViewById(R.id.imageview3);
        textView = findViewById(R.id.textView1);
        textView.append(sName);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.right);
        button1.startAnimation(animation1);

        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.left);
        button2.startAnimation(animation2);

        Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.right);
        button3.startAnimation(animation3);

        Animation animation4 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.left);
        button4.startAnimation(animation4);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,DetailsActivity.class));
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SearchActivity.class));
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ChangePasswordActivity.class));
            }
        });

    }//onCreate ends here
    /*---------------------------------------------------------------------------*/

    public void onBackPressed(){
        buildDialogExit(LoginActivity.this).show();
    }
    public AlertDialog.Builder buildDialogExit(Context c) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("Do you want to Log-out?");
        builder.setMessage("");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(LoginActivity.this,MainActivity.class));finish();return;
            }
        });
        builder.setNeutralButton("Cancel",null);
        return builder;
    }
    /*---------------------------------------------------------------------------*/


}
