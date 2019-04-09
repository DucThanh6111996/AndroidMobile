package com.mta.studyenglish.model;

import java.io.Serializable;

/**
 *
 */

public class GrammarItem implements Serializable{

    public static final int MARKED = 1;
    public static final int NOT_MARKED = 0;

    private int id;
    private String name;
    private int mark;

    public GrammarItem() {
        this.name = "-";
        this.id = -1;
        this.mark = 0;
    }

    public GrammarItem (String name, int id, int mark) {
        this.name = name;
        this.id = id;
        this.mark = mark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
}
