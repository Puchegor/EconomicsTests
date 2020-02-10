package com.elmareos.economicstests;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class Answer implements Parcelable{
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

    protected Answer(Parcel in) {
        idQuestion = in.readInt();
        idAnswer = in.readInt();
        answerText = in.readString();
        isTrue = in.readByte() != 0;
    }

    public static final Creator<Answer> CREATOR = new Creator<Answer>() {
        @Override
        public Answer createFromParcel(Parcel in) {
            return new Answer(in);
        }

        @Override
        public Answer[] newArray(int size) {
            return new Answer[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idQuestion);
        dest.writeInt(idAnswer);
        dest.writeString(answerText);
        dest.writeByte((byte) (isTrue ? 1 : 0));
    }
}
