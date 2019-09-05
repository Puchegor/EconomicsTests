package com.elmareos.economicstests;

import android.support.annotation.NonNull;

public class Answer {
    private int idQuestion;
    private int idAnswer;
    private String answerText;
    private boolean isTrue;

    public Answer(int idQuestion, int idAnswer, String answerText, boolean isTrue){
        this.idQuestion = idQuestion;
        this.idAnswer = idAnswer;
        this.answerText = answerText;
        this.isTrue = isTrue;
    }

    public int getIdAnswer() {
        return idAnswer;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public String getAnswerText() {
        return answerText;
    }

    public boolean isTrue() {
        return isTrue;
    }

    @NonNull
    @Override
    public String toString() {
        return getAnswerText();
    }
}
