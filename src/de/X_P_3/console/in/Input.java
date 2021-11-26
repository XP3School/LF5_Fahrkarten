package de.X_P_3.console.in;

import de.X_P_3.variable.variable;

import java.io.PrintStream;
import java.util.Scanner;

public class Input {
    public static Object repeatInputWMessage(final ValidObjectTypes type, Scanner scanner, String repeatingMessage, String errorMessage, PrintStream output){
        while (true) {
            output.println(repeatingMessage);
            SaveReturnConsoleInputReturn Value = getSaveConsoleInput(type, scanner);
            if (!Value.Error)
                return Value.Value;
            output.println(errorMessage);
        }
    }

    public static Object repeatInputWMessage(final ValidObjectTypes type, Scanner scanner, String repeatingMessage, String errorMessage, PrintStream output, String exceptPattern){
        while (true) {
            output.println(repeatingMessage);
            SaveReturnConsoleInputReturn Value = getSaveConsoleInput(type, scanner);
            if (!Value.Error && !de.X_P_3.pattern.Pattern.stringMatchesPattern(Value.Value.toString(), exceptPattern))
                return Value.Value;
            output.println(errorMessage);
        }
    }

    public static String repeatInputWMessageVO(iRepeatValue repeat, Scanner scanner, String repeatingMessage, String errorMessage, PrintStream output, String defaultReturn){
        Break _break = new Break();
        while (!_break.Break) {
            output.println(repeatingMessage);
            String Value = scanner.next();
            if (repeat.isValueOk(Value, _break))
                return Value;
            else if (!_break.Break)
                output.println(errorMessage);
        }
        return defaultReturn;
    }

    public static SaveReturnConsoleInputReturn getSaveConsoleInput(final ValidObjectTypes type, Scanner scanner){
        String SValue = scanner.next();
        if (SValue != null){
            switch (type){
                case String -> {
                    return new SaveReturnConsoleInputReturn(SValue);
                }
                case Int -> {
                    if (variable.isFormattedLike(SValue, ValidObjectTypes.Int))
                        return new SaveReturnConsoleInputReturn(Integer.parseInt(SValue));
                }
                case Double -> {
                    if (variable.isFormattedLike(SValue.replace(',', '.'), ValidObjectTypes.Double))
                        return new SaveReturnConsoleInputReturn(Double.parseDouble(SValue.replace(',', '.')));
                }
            }
        }
        SaveReturnConsoleInputReturn _return = new SaveReturnConsoleInputReturn(null);
        _return.Error = true;
        return _return;
    }
}

