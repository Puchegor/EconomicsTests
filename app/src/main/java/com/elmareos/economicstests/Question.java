package com.elmareos.economicstests;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;

public class Question {
    private int idTopic;
    private int idQuestion;
    private String questionText;
    private ArrayList<Answer> answers;
    private String correctAnswer;

    public Question(int idTopic, int idQuestion, String questionText, ArrayList<Answer> answers, String correctAnswer){
        this.idTopic = idTopic;
        this.idQuestion = idQuestion;
        this.questionText = questionText;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    public int getIdTopic() {
        return idTopic;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public String getQuestionText() {
        return questionText;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    @NonNull
    @Override
    public String toString() {
        return getQuestionText();
    }
}
