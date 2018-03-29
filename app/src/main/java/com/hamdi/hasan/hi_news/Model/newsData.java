package com.hamdi.hasan.hi_news.Model;

import java.util.ArrayList;


public class newsData {

    private String status;
    private int totalResults;
    private ArrayList<articles> articles;


    public newsData(String status, int totalResults, ArrayList<com.hamdi.hasan.hi_news.Model.articles> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public ArrayList<com.hamdi.hasan.hi_news.Model.articles> getArticles() {
        return articles;
    }



}
