 package com.elmareos.economicstests;

 import android.database.Cursor;
 import android.database.SQLException;
 import android.database.sqlite.SQLiteDatabase;
 import android.os.Bundle;
 import android.support.v7.app.AppCompatActivity;
 import android.util.Log;
 import android.view.View;
 import android.widget.ArrayAdapter;
 import android.widget.ListView;
 import android.widget.RadioButton;

 import java.util.ArrayList;

 public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RadioButton rbTheme, rbStudy, rbCourse;
    ListView listView;
    DBHelper dbHelper;
    SQLiteDatabase db;

    final String LOG_TAG = "myLOGS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "Create main activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rbTheme = findViewById(R.id.rbTheme);
        rbCourse = findViewById(R.id.rbCource);
        rbStudy = findViewById(R.id.rbStudy);
        rbStudy.setOnClickListener(this);
        rbTheme.setOnClickListener(this);
        rbCourse.setOnClickListener(this);
        rbStudy.setChecked(true);
        listView = findViewById(R.id.listView);
        dbHelper = new DBHelper(this);
        db = dbHelper.getReadableDatabase();
        listView = findViewById(R.id.listView);
        onClick(rbStudy);
    }

     @Override
     public void onClick(View v) {
        ArrayList<String> list = new ArrayList<>();
        int index;
        ArrayAdapter adapter;
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM topics", null);
            cursor.moveToFirst();
            index = cursor.getColumnIndex("nameTopic");
            while (!cursor.isAfterLast()) {
                list.add(cursor.getString(index));
                cursor.moveToNext();
            }
            adapter  = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice  , list);
            listView.setAdapter(adapter);
            cursor.close();
        }catch(SQLException e){
            Log.d(LOG_TAG, e.getMessage());
        }
        switch (v.getId()){
            case R.id.rbStudy:
                listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                break;
            case R.id.rbTheme:
                listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                break;
            case R.id.rbCource:
                listView.setChoiceMode(ListView.CHOICE_MODE_NONE);
                break;
        }
     }
 }
