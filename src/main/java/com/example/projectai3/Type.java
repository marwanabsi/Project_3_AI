package com.example.projectai3;

public class Type implements Comparable<Type>{
    private float prob;
    private int Freq;

    public Type( int freq,float prob) {
        this.Freq = freq;
        this.prob = prob;

    }

    public Type(){

    }

    public float getProb() {
        return prob;
    }

    public Type setProb(float prob) {
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


    @Override
    public int compareTo(Type o) {
        return Double.compare(this.getProb(), o.getProb());
    }
}
