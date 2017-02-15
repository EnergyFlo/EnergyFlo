package com.example.billy.energyflo;

/**
 * Created by Billy on 2/15/17.
 */

public class Log {
    private int hour;
    private Float average;
    private int number_of_ratings;
    private int total;
    public Log()
    {
    }
    public Log(int hour,Float average,int number_of_ratings, int total)
    {
        this.hour=hour;
        this.average=average;
        this.number_of_ratings = number_of_ratings;
        this.total = total;
    }
    public void setHour(int hour) {
        this.hour = hour;
    }
    public void setAverage(Float avg) {
        this.average = avg;
    }
    public void setNumber_of_ratings(int num) {
        this.number_of_ratings = num;
    }
    public void setTotal(int tot){ this.total = tot; };
    public int getHour() {
        return this.hour;
    }
    public Float getAverage() {
        return this.average;
    }
    public int getNumber_of_ratings() {
        return this.number_of_ratings;
    }
    public int getTotal(){ return this.total; }
}

