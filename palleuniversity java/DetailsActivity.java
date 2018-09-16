package com.example.rakesh.palleuniversity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class DetailsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    MyAdapter myAdapter;
    MyDatabase myDatabase;
    Cursor cursor;
    Intent intent;
    ArrayList<String> mails = new ArrayList <>();
    int eno = -1;
    EditText editText1;

    public void nameGo(View view) {
        String name = editText1.getText().toString();
        myDatabase.search(name);
    }


    public class MyAdapter extends RecyclerView.Adapter <MyAdapter.ViewHolder> {
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.row, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            cursor.moveToPosition(position);
            holder.textView2.setText(cursor.getString(1));
            holder.textView3.setText(cursor.getString(2));
            holder.textView4.setText(cursor.getString(3));
        }

        @Override
        public int getItemCount() {
            return cursor.getCount();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            CheckBox checkBox;
            LinearLayout linearLayout;
            public TextView textView2, textView3, textView4;

            ViewHolder(final View itemView) {
                super(itemView);

                checkBox = itemView.findViewById(R.id.checkbox);
                linearLayout = itemView.findViewById(R.id.linearlayout);
                textView2 = itemView.findViewById(R.id.textView2);
                textView3 = itemView.findViewById(R.id.textView3);
                textView4 = itemView.findViewById(R.id.textView4);

                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkBox.isChecked()){
                            cursor.moveToPosition(getAdapterPosition());
                            String email = cursor.getString(3);
                            mails.add(email);
                        }else{
                            cursor.moveToPosition(getAdapterPosition());
                            String email = cursor.getString(3);
                            mails.remove(email);
                        }
                    }
                });


                linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        cursor.moveToPosition(getAdapterPosition());
                        String name = cursor.getString(1);
                        AlertDialog dialog = new AlertDialog.Builder(DetailsActivity.this)
                                .setTitle("Delete " + name + " ?")
                                .setMessage("You can delete or update")
                                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(DetailsActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                                        cursor.moveToPosition(getAdapterPosition());
                                        eno = cursor.getInt(0);
                                        myDatabase.deleteEmp(eno);
                                        cursor = myDatabase.readEmp();
                                        myAdapter.notifyDataSetChanged();
                                    }
                                })
                                .setNegativeButton("update", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        cursor.moveToPosition(getAdapterPosition());
                                        eno = cursor.getInt(0);
                                        intent = new Intent(DetailsActivity.this,AddActivity.class);
                                        intent.putExtra("getAdapterPosition",getAdapterPosition());
                                        intent.putExtra("name",cursor.getString(1));
                                        intent.putExtra("no",cursor.getString(2));
                                        intent.putExtra("mail",cursor.getString(3));
                                        startActivity(intent);finish();
                                    }
                                })
                                .setNeutralButton("cancel", null)
                                .create();
                        dialog.show();
                        return true;
                    }
                });

            }
        }
    }//adapter ends here

    /*---------------------------------------------------------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        editText1 = findViewById(R.id.editText1);

        recyclerView = findViewById(R.id.recyclerview);

        myDatabase = new MyDatabase(this);
        myDatabase.open();
        cursor = myDatabase.readEmp();

        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        myAdapter = new MyAdapter();
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(myAdapter);

    }//onCreate ends here

    /*---------------------------------------------------------------------------*/

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.addtask:
                startActivity(new Intent(this, AddActivity.class));
                finish();
                return true;

            case R.id.help:
                buildDialogHelp(this).show();
                return true;

            case R.id.mail:
                if (mails==null||mails.isEmpty()){
                    Toast.makeText(this, "Please select atleast one", Toast.LENGTH_SHORT).show();
                }
                if (mails!=null && !mails.isEmpty()){
                   intent = new Intent(DetailsActivity.this,MailActivity.class);
                   intent.putExtra("student",mails);
                   startActivity(intent);finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*---------------------------------------------------------------------------*/

    public AlertDialog.Builder buildDialogHelp(Context c) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("Options");
        builder.setMessage("1.Press long to delete or update\n" +
                "2.Press add icon to add new\n"+
                "3.Press check box to send mail\n"+
                "4.Press mail icon to send Email");
        builder.setPositiveButton("Got it !", null);
        return builder;
    }




}