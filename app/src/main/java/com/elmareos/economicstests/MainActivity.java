 package com.elmareos.economicstests;

 import android.content.Intent;
 import android.database.Cursor;
 import android.database.SQLException;
 import android.database.sqlite.SQLiteDatabase;
 import android.os.Bundle;
 import android.support.v7.app.AppCompatActivity;
 import android.util.Log;
 import android.view.View;
 import android.widget.AdapterView;
 import android.widget.ArrayAdapter;
 import android.widget.Button;
 import android.widget.ListView;
 import android.widget.RadioButton;
 import android.widget.Toast;

 import java.util.ArrayList;

 public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RadioButton rbTheme, rbStudy, rbCourse;
    ListView listView;
    DBHelper dbHelper;
    SQLiteDatabase db;
    Button btnStart;
    private ArrayList<Question>Test = new ArrayList<>();

    final String LOG_TAG = "myLOGS";
    int choise = -1;

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
        //listView = findViewById(R.id.listView);
        dbHelper = new DBHelper(this);
        db = dbHelper.getReadableDatabase();
        listView = findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                choise = position;
            }
        });
        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);
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
            adapter  = new ArrayAdapter<>(this, R.layout.list_item  , list);
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
            case R.id.btnStart:
                Intent intent = new Intent(this, TestActivity.class);
                if (rbCourse.isChecked())
                    intent.putExtra("tvRegimTxt", "Контрольный тест по курсу \"Экономическая теория\"");
                if (rbTheme.isChecked())
                    if (choise == -1) {
                        Toast.makeText(this, "Необходимо выбрать тему!", Toast.LENGTH_LONG).show();
                        return;
                    }else {
                        intent.putExtra("tvRegimTxt", "Контрольный тест по теме \""+
                                list.get(choise) +"\"");
                        choise = -1; //Сброс выделения;
                    }
                if (rbStudy.isChecked())
                    if (choise == -1){
                        Toast.makeText(this, "Необходимо выбрать тему!", Toast.LENGTH_LONG).show();
                        return;
                    }else{
                        intent.putExtra("tvRegimTxt", "Обучающий тест по теме \""+
                                list.get(choise)+"\"");
                        //Создание обучающего теста по выбранной теме
                        Cursor cursor = db.rawQuery("SELECT _id FROM topics WHERE nameTopic = \""+
                                list.get(choise)+"\"", null);
                        cursor.moveToFirst();
                        //debug
                        int idTopic = cursor.getInt(cursor.getColumnIndex("_id"));
                        ArrayList<Question>test = createTest(idTopic);
                        intent.putParcelableArrayListExtra("test", test);
                        cursor.close();
                        choise = -1;
                    }
                startActivity(intent);
                break;
        }
     }
     private ArrayList<Question> createTest(int idTopic){
        ArrayList<Question>questions = new ArrayList<>();

        try{
            Cursor cursor = db.rawQuery("SELECT * FROM questions WHERE idTopic = "+idTopic, null);
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                int idQuestion = cursor.getInt(cursor.getColumnIndex("_id"));
                ArrayList<Answer> Answers= new ArrayList<>();
                Cursor cursorAnswers = db.rawQuery("SELECT * FROM answers WHERE idQuestion = "+idQuestion, null);
                cursorAnswers.moveToFirst();
                while (!cursorAnswers.isAfterLast()){
                    boolean isTrue;
                    if (cursorAnswers.getInt(cursorAnswers.getColumnIndex("isCorrect"))==0)
                        isTrue = false;
                    else
                        isTrue = true;
                    Answer answer = new Answer(idQuestion,
                            cursorAnswers.getInt(cursorAnswers.getColumnIndex("_id")),
                            cursorAnswers.getString(cursorAnswers.getColumnIndex("nameAnswer")),
                            isTrue);
                    Answers.add(answer);
                    cursorAnswers.moveToNext();
                }
                cursorAnswers.close();
                Question quest = new Question(idTopic,idQuestion,
                        cursor.getString(cursor.getColumnIndex("nameQuestion")),
                        Answers, cursor.getString(cursor.getColumnIndex("correctAnswer")));
                questions.add(quest);
                cursor.moveToNext();
            }
            cursor.close();
        }catch (SQLException e){
            Log.d(LOG_TAG, e.getMessage());
        }
        return questions;
     }
 }
