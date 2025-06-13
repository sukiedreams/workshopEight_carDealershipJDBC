package com.ps;

public class LeaseContract extends Contract{

    public LeaseContract (String date, String customerName, String customerEmail, Vehicle vehicle) {
        super(date, customerName, customerEmail, vehicle);
    }

    @Override
    public double getTotalPrice() {
        double price = getVehicle().getPrice();
        double leaseFee = price * 0.07;
        return leaseFee + (price * 0.5);
    }

    @Override
    public double getMonthlyPayment() {
        double amount = getTotalPrice();
        return (amount * 1.04) / 36;
    }
}
