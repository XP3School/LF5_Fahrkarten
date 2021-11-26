package de.X_P_3.money;

import java.util.Objects;

public class MoneyElement {
    public double Value;
    public String name;
    public int count = 0;
    public MoneyElement(double Value, String name){
        this.Value = Value;
        this.name = name;
    }

    public static MoneyElement findElement(MoneyElement[] array, MoneyElement item){
        for (MoneyElement _item:
                array) {
            if (_item.Value == item.Value && Objects.equals(_item.name, item.name))
                return _item;
        }
        return null;
    }

    public static MoneyElement findElementByValue(MoneyElement[] array, double value){
        for (MoneyElement item:
             array) {
            if (item.Value == value)
                return item;
        }
        return null;
    }

    public static void resetCounts(MoneyElement[] array){
        for (MoneyElement Item:
             array) {
            Item.count = 0;
        }
    }
}
