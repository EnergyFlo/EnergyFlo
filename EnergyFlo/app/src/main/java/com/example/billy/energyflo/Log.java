package com.example.billy.energyflo;

/**
 * Created by Billy on 2/15/17.
 */

public class Log {
    private int hour;
    private double average;
    private int number_of_ratings;
    private int total;
    public Log()
    {
    }

    public Log(int hour,double average,int number_of_ratings, int total)
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
    public double getAverage() {
        return this.average;
    }
    public int getNumber_of_ratings() {
        return this.number_of_ratings;
    }
    public int getTotal(){ return this.total; }
    public void updateLog(int rating){
        //method updates the log that was grabbed from the database
        //NOTE this does not update the database!
        this.total += rating;
        this.number_of_ratings++;
        this.average = this.total/this.number_of_ratings;
    }


}

