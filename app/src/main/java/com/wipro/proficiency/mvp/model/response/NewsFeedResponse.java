package com.wipro.proficiency.mvp.model.response;

public class NewsFeedResponse {

    private String title;

    private Rows[] rows;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Rows[] getRows() {
        return rows;
    }

    public void setRows(Rows[] rows) {
        this.rows = rows;
    }

}