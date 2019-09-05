package com.elmareos.economicstests;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvRegim, tvQuestion;
    ListView lvAnswers;

    private ArrayList<Question>test = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Intent intent = getIntent();
        tvRegim = findViewById(R.id.tvRegim);
        tvRegim.setText(intent.getStringExtra("tvRegimTxt"));
        tvQuestion = findViewById(R.id.tvQuestion);
        lvAnswers = findViewById(R.id.lvAnswers);
        test = MainActivity.getTest();//intent.getParcelableArrayListExtra("test");
        Collections.shuffle(test);
        fillScreen();
    }

    private void fillScreen(){
        Random rand = new Random();
        int max = test.size();
        int diff = max - 1;
        int i = rand.nextInt(diff+1);
        i += 1;
        tvQuestion.setText(test.get(i).getQuestionText());
    }

    @Override
    public void onClick(View v) {

    }
}
