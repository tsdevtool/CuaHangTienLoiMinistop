package com.example.Melistop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

public class MonthlyRevenue {
    private int year;
    private int month;
    private Double revenue;

    public MonthlyRevenue(int year, int month, Double revenue) {
        this.year = year;
        this.month = month;
        this.revenue = revenue;
    }

    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
