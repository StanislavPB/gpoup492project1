package org.gpoup492project1.Project.Entity;

import java.time.LocalDate;

public class Balance {
   private Double income;
   private Double outcome;
   private String category;
   private LocalDate date;

    public Balance(Double income, Double outcome, String category, LocalDate date) {
        this.income = income;
        this.outcome = outcome;
        this.category = category;
        this.date = date;
    }

    public Double getIncome() {
        return income;
    }

    public Double getOutcome() {
        return outcome;
    }

    public LocalDate getDate() {
        return date;
    }



    @Override
    public String toString() {
        return "Balance{" +
                "income=" + income +
                ", outcome=" + outcome +
                ", category='" + category + '\'' +
                ", date=" + date +
                '}';
    }
}
