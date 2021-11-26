package de.X_P_3.pattern;

import java.util.regex.Matcher;

public class Pattern {
    public static boolean stringMatchesPattern(String Value, String _Pattern){
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(_Pattern);
        Matcher m = p.matcher(Value);
        return m.matches();
    }
}
