package de.X_P_3.money;

public class MoneyCalculator {
    public static MoneyElement[] addMoneyAndCalculate(double Value, MoneyElement[] moneyElements) {
        for (MoneyElement Item : moneyElements) {
            while (Value >= Item.Value) {
                Item.count++;
                Value -= Item.Value;
            }
        }
        return moneyElements;
    }
}