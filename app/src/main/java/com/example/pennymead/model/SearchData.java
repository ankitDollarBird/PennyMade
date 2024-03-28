package com.example.pennymead.model;

public class SearchData {
    String term;
    int adesc;
    int category_number;
    String sortby;
    int page;

    public SearchData(String term, int adesc, int category_number, String sortby, int page) {
        this.term = term;
        this.adesc = adesc;
        this.category_number = category_number;
        this.sortby = sortby;
        this.page = page;
    }

    public String getTerm() {
        return term;
    }

    public int getAdesc() {
        return adesc;
    }

    public int getCategory_number() {
        return category_number;
    }

    public String getSortby() {
        return sortby;
    }

    public int getPage() {
        return page;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void setAdesc(int adesc) {
        this.adesc = adesc;
    }

    public void setCategory_number(int category_number) {
        this.category_number = category_number;
    }

    public void setSortby(String sortby) {
        this.sortby = sortby;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
