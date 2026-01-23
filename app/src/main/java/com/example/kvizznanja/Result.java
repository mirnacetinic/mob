package com.example.kvizznanja;

public class Result {
    private String name;
    private int score;

    // Firebase zahtijeva prazan konstruktor
    public Result() {}

    public Result(String name, int score){
        this.name = name;
        this.score = score;
    }

    public String getName() { return name; }
    public int getScore() { return score; }
}
