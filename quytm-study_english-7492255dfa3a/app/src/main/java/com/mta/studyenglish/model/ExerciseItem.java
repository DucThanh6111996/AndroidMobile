package com.mta.studyenglish.model;

import java.io.Serializable;

/**
 /* Class sử dụng Serilizable vì nó được dùng để truyền qua lại giữa các activity
 *  và fragment (gửi thông qua Context)
 */

public class ExerciseItem implements Serializable{
    public static final int PASSED = 2;
    public static final int NOT_PASSED = 1;
    public static final int NOT_ANSWER = 0;


    private int id;
    private int grammarId;
    private String question;
    private String answerA, answerB, answerC;
    private String trueAnswer;
    private int pass;

    public ExerciseItem(int id, int grammarId, String question, String answerA, String answerB, String answerC, String trueAnswer, int pass) {
        this.id = id;
        this.grammarId = grammarId;
        this.question = question;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.trueAnswer = trueAnswer;
        this.pass = pass;
    }

    public int getGrammarId() {
        return grammarId;
    }

    public void setGrammarId(int grammarId) {
        this.grammarId = grammarId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getTrueAnswer() {
        return trueAnswer;
    }

    public void setTrueAnswer(String trueAnswer) {
        this.trueAnswer = trueAnswer;
    }

    public int getPass() {
        return pass;
    }

    public void setPass(int pass) {
        this.pass = pass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
