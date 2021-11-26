package de.X_P_3.console.in;

public class Debug {
    public static final boolean DEBUG = false;
    public static void writeDebugMessage(String Message, String Class, String Methode, boolean Error){
        if (DEBUG)
            if (!Error)
                System.out.println(Class + "." + Methode + "|" + Message);
            else
                System.err.println(Class + "." + Methode + "|" + Message);
    }
}
