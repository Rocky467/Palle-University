package com.example.rakesh.palleuniversity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    EditText editText1,editText2,editText3,editText4;
    Button button1,button3,button4;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callBack;
    String verification;
    TextView textView1,textView2,textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        SharedPreferences sp = getSharedPreferences("EmployeeCredentials", Context.MODE_PRIVATE);
        final String sName = sp.getString("name",null);
        final String sPassword = sp.getString("password",null);

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);
        button1 = findViewById(R.id.button1);

        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

        editText3.setEnabled(false);editText3.setVisibility(View.INVISIBLE);
        editText4.setEnabled(false);editText4.setVisibility(View.INVISIBLE);
        button4.setEnabled(false);button4.setVisibility(View.INVISIBLE);

        textView1 = findViewById(R.id.textView1);textView1.setVisibility(View.INVISIBLE);
        textView2 = findViewById(R.id.textView2);textView2.setVisibility(View.INVISIBLE);
        textView3 = findViewById(R.id.textView3);textView3.setVisibility(View.INVISIBLE);

        try{
            if (sName.isEmpty()){
            }
        }catch (NullPointerException e){
            signUp();
        }

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText1.getText().toString().trim();
                String password = editText2.getText().toString().trim();

                try{
                    if (name.isEmpty()||!sName.equals(name)){
                        editText1.setError("Incorrect User Name");
                    }
                }catch (NullPointerException e){
                    editText1.setError("Incorrect User Name");
                }

                try{
                    if (password.isEmpty()||!sPassword.equals(password)){
                        editText2.setError("Incorrect Password");
                    }
                }catch (NullPointerException e){
                    editText2.setError("Incorrect Password");
                }

                try{
                    if (!name.isEmpty()&&!password.isEmpty()&&sName.equals(name)&&sPassword.equals(password)){
                        startActivity(new Intent(MainActivity.this,LoginActivity.class));finish();return;
                    }
                }catch (NullPointerException e){
                    editText1.setError("Incorrect User Name");
                    editText2.setError("Incorrect Password");
                }
                editText1.setText("");editText2.setText("");editText1.requestFocus();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText3.setEnabled(true);editText3.setVisibility(View.VISIBLE);
                editText4.setEnabled(true);editText4.setVisibility(View.VISIBLE);
                textView1.setVisibility(View.VISIBLE);
                button4.setEnabled(true);button4.setVisibility(View.VISIBLE);
            }
        });


        callBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signIn(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verification = s;
                editText4.setText("Verified");
            }
        };

    }//onCreate ends here
    /*---------------------------------------------------------------------------*/
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

    /*---------------------------------------------------------------------------*/

    public void signUp(){
        buildDialogSignUp(MainActivity.this).show();
    }
    public AlertDialog.Builder buildDialogSignUp(Context c) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("Un-registered User !");
        builder.setMessage("Please SignUp to Login");
        builder.setPositiveButton("SignUp", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(MainActivity.this,SignUpActivity.class));
                finish();
                return;

            }
        });
        builder.setNeutralButton("Cancel",null);
        return builder;
    }

    /*---------------------------------------------------------------------------*/

    public void clicked(View view) {
        SharedPreferences sp = getSharedPreferences("EmployeeCredentials", Context.MODE_PRIVATE);
        final String no = editText3.getText().toString().trim();
        final String mobNo = sp.getString("mobno",null);

        try{
            if (no.isEmpty()||!mobNo.equals(no)){
                editText3.setError("Incorrect mobileNo.");
            }
        }catch (NullPointerException e){
            editText3.setError("Incorrect mobileNo.");
        }
        try {
            if (!no.isEmpty()&& mobNo.equals(no)){
                if (!isConnected(MainActivity.this)) buildDialogInternet(MainActivity.this).show();

                if (isConnected(MainActivity.this)){
                    Toast.makeText(this, "sending OTP...Please wait...", Toast.LENGTH_SHORT).show();
                    if (verification!=null){
                        verifyCode();
                    }else {
                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                textView1.getText().toString()+no,
                                60,
                                TimeUnit.SECONDS,
                                this,
                                callBack);
                    }
                }
            }
        }catch (NullPointerException e){
            editText3.setError("Incorrect mobileNo.");
        }

        }

    private void verifyCode() {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verification,editText4.getText().toString());
        signIn(credential);
    }

    private void signIn(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isComplete()){
                    loggedIn();
                }
            }
        });
    }

    private void loggedIn() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            SharedPreferences sp = getSharedPreferences("EmployeeCredentials", Context.MODE_PRIVATE);
            final String sName = sp.getString("name",null);
            final String sPassword = sp.getString("password",null);
            textView2.setVisibility(View.VISIBLE);textView2.append(sName);
            textView3.setVisibility(View.VISIBLE);textView3.append(sPassword);
        }

    }

    /*---------------------------------------------------------------------------*/




}






