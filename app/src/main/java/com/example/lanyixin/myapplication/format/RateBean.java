package com.example.lanyixin.myapplication.format;

import java.io.Serializable;

/**
 * Created by jiongjiong on 2018/12/11.
 */
public class RateBean implements Serializable {
    private String symbol;

    private double rate;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
