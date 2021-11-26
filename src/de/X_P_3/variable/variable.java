package de.X_P_3.variable;

import de.X_P_3.console.in.ValidObjectTypes;
import de.X_P_3.pattern.Pattern;


public class variable {
    public static boolean isFormattedLike(String Value, ValidObjectTypes type){
        switch (type){
            case Double -> {return Pattern.stringMatchesPattern(Value, "\\d+(.\\d+)?"); }
            case Int -> {return Pattern.stringMatchesPattern(Value, "\\d+"); }
            default -> {return false; }
        }
    }
}
