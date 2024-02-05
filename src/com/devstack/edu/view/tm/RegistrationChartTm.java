package com.devstack.edu.view.tm;

import java.time.LocalDate;

public class RegistrationChartTm {

    private LocalDate date;
    private long count;

    public RegistrationChartTm() {
    }

    public RegistrationChartTm(LocalDate date, long count) {
        this.date = date;
        this.count = count;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "RegistrationChartTm{" +
                "date=" + date +
                ", count=" + count +
                '}';
    }
}
