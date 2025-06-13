package com.ps;

public class SalesContract extends Contract {

    private boolean isFinance;

    public SalesContract (String date, String customerName, String customerEmail, Vehicle vehicle, boolean finance) {
        super(date, customerName, customerEmail, vehicle);
        this.isFinance = finance;
    }

    public boolean isFinance() {
        return isFinance;
    }

    public void setFinance(boolean finance) {
        isFinance = finance;
    }

    @Override
    public double getTotalPrice() {
        double price = getVehicle().getPrice();
        double salesTax = price * 0.05;
        double recordingFee = 100;
        double processingFee = price < 10000 ? 295 : 495;
        return  price + salesTax + recordingFee + processingFee;
    }

    @Override
    public double getMonthlyPayment() {
        if (!isFinance) {
            return 0.0;
        }

        double totalPrice = getTotalPrice();
        double interestRate;
        int months;

        if (getVehicle().getPrice() >= 10000) {
            interestRate = 0.0425;
            months = 48;
        } else {
            interestRate = 0.0525;
            months = 24;
        }

        // following the monthly payment formula.
        double monthlyRate = interestRate / 12;
        double numerator = totalPrice * monthlyRate;
        double denominator = 1 - Math.pow(1 + monthlyRate, -months);
        double monthlyPayment = numerator / denominator;

        return  Math.round(monthlyPayment * 100.0) / 100.0;
    }
}

