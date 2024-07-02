package com.example.Melistop.DTO;

import java.util.Date;

//@Data
//@AllArgsConstructor
public class DailyRevenue {
    private Date date;
    private double total;

    public DailyRevenue(Date date, Double total){
        this.date = date;
        this.total = total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
