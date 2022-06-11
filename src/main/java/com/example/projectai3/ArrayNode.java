package com.example.projectai3;

public class ArrayNode implements Comparable<ArrayNode>{
    private String key;
    private Type type;

    public ArrayNode(String key, Type type) {
        this.key = key;
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return " الكلمة =  " + key +"\t نسبة التوقع =  " + type.getProb()+"\n";
    }

    @Override
    public int compareTo(ArrayNode o) {
        return Double.compare(this.getType().getProb(), o.getType().getProb());
    }
}
