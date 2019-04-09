package com.mta.studyenglish.model;

/**
 *
 */

public class GrammarDetailItem {

    private int id;
    private String description;
    private int grammarListId;

    public GrammarDetailItem(int id, String description, int grammarListId) {
        this.id = id;
        this.description = description;
        this.grammarListId = grammarListId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGrammarListId() {
        return grammarListId;
    }

    public void setGrammarListId(int grammarListId) {
        this.grammarListId = grammarListId;
    }
}
