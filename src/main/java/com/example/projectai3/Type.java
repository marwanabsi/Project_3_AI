package com.example.projectai3;

public class Type {
    private double prob;
    private int Freq;

    public Type( int freq,double prob) {
        this.Freq = freq;
        this.prob = prob;

    }
    public Type(){

    }

    public double getProb() {
        return prob;
    }

    public Type setProb(double prob) {
        this.prob = prob;
        return this;
    }

    public int getFreq() {
        return Freq;
    }

    public Type setFreq(int freq) {
        Freq = freq;
        return this;
    }

    @Override
    public String toString() {
        return "prob=" + prob +", Freq=" + Freq ;
    }
}
