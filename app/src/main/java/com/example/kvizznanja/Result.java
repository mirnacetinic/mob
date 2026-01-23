package com.example.kvizznanja;

public class Result {
    private String email;
    private int score;

    // Firebase zahtijeva prazan konstruktor
    public Result() {}

    public Result(String email, int score){
        this.email = email;
        this.score = score;
    }

    public String getEmail() { return email; }
    public int getScore() { return score; }
}
