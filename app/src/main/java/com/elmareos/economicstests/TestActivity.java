package com.elmareos.economicstests;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvRegim, tvQuestion;
    ListView lvAnswers;
    Button btnOk, btnCancel, btnSkip;
    //Answer answer;
    //final String LOG_TAG = "myLOGS";
    private ArrayList<Question>test = new ArrayList<>();
    private SparseBooleanArray correctAnswers = new SparseBooleanArray();
    private SparseBooleanArray chosenAnswers = new SparseBooleanArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Intent intent = getIntent();
        tvRegim = findViewById(R.id.tvRegim);
        tvRegim.setText(intent.getStringExtra("tvRegimTxt"));
        tvQuestion = findViewById(R.id.tvQuestion);
        btnCancel = findViewById(R.id.btnCancel);
        btnSkip = findViewById(R.id.btnSkip);
        lvAnswers = findViewById(R.id.lvAnswers);
        lvAnswers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chosenAnswers.clear();
                SparseBooleanArray sp = lvAnswers.getCheckedItemPositions();
                for (int i = 0; i < lvAnswers.getAdapter().getCount(); i++){
                    if(sp.get(i))
                        chosenAnswers.put(((Answer)lvAnswers.getAdapter().getItem(i)).getIdAnswer(), true);
                    else
                        chosenAnswers.put(((Answer)lvAnswers.getAdapter().getItem(i)).getIdAnswer(), false);
                }
            }
        });
        btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);
        test = intent.getParcelableArrayListExtra("test");
        Collections.shuffle(test);
        fillScreen();
    }

    private void fillScreen(){
        tvQuestion.setText(test.get(0).getQuestionText());
        ArrayList<Answer> answers = test.get(0).getAnswers();
        Collections.shuffle(answers);
        for (int i = 0; i < answers.size(); i++){
            correctAnswers.put(answers.get(i).getIdAnswer(), answers.get(i).isTrue());
        }
        ArrayAdapter<Answer> adapter = new ArrayAdapter<>(this, R.layout.list_item,answers);
        lvAnswers.setAdapter(adapter);
        lvAnswers.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnOk:
                if (chosenAnswers.size()==0)
                    Toast.makeText(this, "Необходимо выбрать хотя-бы один вариант ответа!", Toast.LENGTH_LONG).show();
                else{
                    SparseBooleanArray results = new SparseBooleanArray();
                    for(int i = 0; i < correctAnswers.size(); i++){
                        if(correctAnswers.valueAt(i)==chosenAnswers.valueAt(i))
                            results.put(correctAnswers.keyAt(i), true);
                        else
                            results.put(correctAnswers.keyAt(i), false);
                    }
                    int corrects = 0;
                    for (int i = 0; i < results.size(); i++){
                        if (results.valueAt(i))
                            corrects++;
                    }
                    if(corrects == results.size())
                        Toast.makeText(this, "Вы совершенно правы", Toast.LENGTH_LONG).show();
                    if(corrects > 0 && corrects<results.size())
                        Toast.makeText(this, "Вы не совсем правы", Toast.LENGTH_LONG).show();
                    if(corrects == 0)
                        Toast.makeText(this, "Да ты ваще дебил, бля?!?", Toast.LENGTH_LONG).show();

                    /* ДОБАВИТЬ ОБЪЕКТЫ ДЛЯ СОХРАНЕНИЯ ОТЧЕТА
                    -------------------------------------
                     */
                    test.remove(0);
                    fillScreen();
                }
                break;
            case R.id.btnCancel:
                break;
            case R.id.btnSkip:
                // ---------ИЗМЕНИТЬ ИНДЕКС ЭЛЕМЕНТА НА ПОСЛЕДНИЙ--------------
                break;
        }
    }
}
