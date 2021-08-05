package com.site.blog.my.core.entity;

import java.util.List;

public class stateWith7dayData {
    private String state;
    private String country;
    private String date;
    private List<Integer> sevenDayData;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Integer> getSevenDayData() {
        return sevenDayData;
    }

    public void setSevenDayData(List<Integer> sevenDayData) {
        this.sevenDayData = sevenDayData;
    }
}
