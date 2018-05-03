package javafx;

import javafx.beans.property.SimpleIntegerProperty;

public class Payment {

    private final SimpleIntegerProperty paymentNumber;
    private final SimpleIntegerProperty monthlyPayment;
    private final SimpleIntegerProperty amountOwed;

    public Payment(int paymentNo, int monthlyPayment, int amountOwed) {
        this.paymentNumber = new SimpleIntegerProperty(paymentNo);
        this.monthlyPayment = new SimpleIntegerProperty(monthlyPayment);
        this.amountOwed = new SimpleIntegerProperty(amountOwed);
    }

    public int getPaymentNumber() { return paymentNumber.get(); }

    public SimpleIntegerProperty paymentNumberProperty() {
        return paymentNumber;
    }

    public void setPaymentNumber(int paymentNumber) {
        this.paymentNumber.set(paymentNumber);
    }

    public int getMonthlyPayment() {
        return monthlyPayment.get();
    }

    public SimpleIntegerProperty monthlyPaymentProperty() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(int monthlyPayment) {
        this.monthlyPayment.set(monthlyPayment);
    }

    public int getAmountOwed() {
        return amountOwed.get();
    }

    public SimpleIntegerProperty amountOwedProperty() {
        return amountOwed;
    }

    public void setAmountOwed(int amountOwed) {
        this.amountOwed.set(amountOwed);
    }
}
