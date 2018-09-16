package com.example.rakesh.palleuniversity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MailActivity extends AppCompatActivity {
    TextView textView;
    EditText editText1,editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);

        textView = findViewById(R.id.textView);

        Intent intent = getIntent();
        ArrayList<String> mails = intent.getStringArrayListExtra("student");
        textView.setText(mails.toString().replace("[","").replace("]",""));
    }

    public void send(View view) {

        String to = textView.getText().toString().trim();
        String[] recipients = to.split(",");
        String subject = editText1.getText().toString().trim();
        String content = editText2.getText().toString().trim();

        if (subject.isEmpty()){
            editText1.setError("Please write subject");
        }

        if (content.isEmpty()){
            editText2.setError("Please write mail");
        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,recipients );
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setType("message/rfc822");

        if (!content.isEmpty() && !subject.isEmpty()){
            if (!isConnected(MailActivity.this)) buildDialogInternet(MailActivity.this).show();
            if (isConnected(MailActivity.this)){
                startActivity(Intent.createChooser(intent,"choose an email client"));
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,DetailsActivity.class));finish();return;
    }

    public boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else
            return false;
    }
    public AlertDialog.Builder buildDialogInternet(Context c) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("Please connect to the Internet");
        builder.setPositiveButton("Exit", null);
        builder.setNeutralButton("cancel",null);
        return builder;
    }


}
