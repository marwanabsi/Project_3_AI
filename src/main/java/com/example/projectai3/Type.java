package com.example.projectai3;

public class Type {
    private String word;
    private int Freq;

    public Type(String word, int freq) {
        this.word = word;
        Freq = freq;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getFreq() {
        return Freq;
    }

    public void setFreq(int freq) {
        Freq = freq;
    }

    @Override
    public String toString() {
        return "Type{" +
                "word='" + word + '\'' +
                ", Freq=" + Freq +
                '}';
    }
}
