package com.mamuncreates.bangladictionary;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText ediiitext;
    dataBaseHelper dataBaseHelper;
    ArrayList<String> wordlist;
    ArrayAdapter<String>adapter;
    ListView listView;
    LinearLayout linearLayout;
    TextView mainword,meaning,example,partsofspeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /////////////////actionbardesign///////
        if (getSupportActionBar()!=null) {
            getSupportActionBar().setTitle("Home");
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        ediiitext = findViewById(R.id.ediitext);
        listView = findViewById(R.id.listview);
        linearLayout = findViewById(R.id.linearlayout);
        mainword = findViewById(R.id.mainword);
        meaning = findViewById(R.id.meaning);
        example = findViewById(R.id.example);
        partsofspeech=findViewById(R.id.partsofspeech);
        dataBaseHelper = new dataBaseHelper(MainActivity.this);
        linearLayout.setVisibility(View.GONE);

        ediiitext.requestFocus();
        InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(ediiitext,InputMethodManager.SHOW_IMPLICIT);

        /*
        Cursor cursor=dataBaseHelper.getAllData();
        if (cursor!=null&&cursor.getCount()>0){
            while (cursor.moveToNext()){
                int id=cursor.getInt(0);
                String word=cursor.getString(1);
            }
        }
         */

        wordlist = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, wordlist);
        listView.setAdapter(adapter);


        ediiitext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    listView.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                   //searcwords(s.toString());
                } else {
                    listView.setVisibility(View.GONE);
                    //linearLayout.setVisibility(View.VISIBLE);
                    wordlist.clear();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                searcwords(s.toString());

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedWord = wordlist.get(position);
                Cursor cursor = dataBaseHelper.getWordDetails(selectedWord);
                if (cursor != null && cursor.moveToFirst()) {
                    String word = cursor.getString(1);
                    String mean = cursor.getString(2);
                    String ex = cursor.getString(4);
                    String parts=cursor.getString(3);

                    mainword.setText(word);
                    meaning.setText(mean);
                    example.setText(ex);
                    partsofspeech.setText("."+parts);

                    ediiitext.setText(word);
                    ediiitext.setSelection(ediiitext.getText().length());

                    listView.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                }}
        });

    }


    public void searcwords(String query){
        wordlist.clear();

        if (query.trim().isEmpty()){
            adapter.notifyDataSetChanged();
            return;
        }
        Cursor cursor=dataBaseHelper.searchword(query);
        if (cursor!=null&&cursor.getCount()>0){
            while (cursor.moveToNext()){
                int id=cursor.getInt(0);
                String word=cursor.getString(1);
                wordlist.add(word);
            }
            cursor.close();
        }
        adapter.notifyDataSetChanged();
    }


    /////////menu er code/////////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        if (id==R.id.privacy){

        } else if (id==R.id.about) {

        } else if (id==R.id.dev) {

        }


        return super.onOptionsItemSelected(item);
    }

    /////end menu code////////
}