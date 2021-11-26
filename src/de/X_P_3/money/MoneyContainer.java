package de.X_P_3.money;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class MoneyContainer {
    public double value;
    public MoneyElement[] moneyElements;
    public MoneyContainer(){
        value = 0;
        moneyElements = new MoneyElement[0];
    }

    public void setMoneyElements(MoneyElement[] moneyElements){
        this.moneyElements = moneyElements;
    }

    public void addMoneyElementValue(MoneyElement element){
        MoneyElement listItem = MoneyElement.findElement(moneyElements, element);
        if (listItem != null) {
            listItem.count += element.count;
            calculateValue();
            return;
        }
        throw new NoSuchElementException();
    }

    public int getCountInRange(double min, double max){
        int out = 0;
        for (MoneyElement Item:
             moneyElements) {
            if (Item.Value >= min && Item.Value <= max)
                out+= Item.count;
        }
        return out;
    }

    public void calculateValue(){
        value = 0;
        for (MoneyElement item: moneyElements) {
            value+= item.Value * item.count;
        }
    }

    public void addCalculated(MoneyElement[] calculated){
        for (MoneyElement Item: calculated) {
            addMoneyElementValue(Item);
        }
        calculateValue();
    }
}
