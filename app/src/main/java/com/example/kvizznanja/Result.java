package com.example.kvizznanja;

public class Result {
    private String email;
    private int score;

    private int time;

    // Firebase zahtijeva prazan konstruktor
    public Result() {}

    public Result(String email, int score, int time){
        this.email = email;
        this.score = score;
        this.time = time;
    }

    public String getEmail() { return email; }
    public int getScore() { return score; }

    public int getTime() { return time; }
}
