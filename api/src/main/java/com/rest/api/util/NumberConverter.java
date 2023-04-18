package com.rest.api.util;

public class NumberConverter {
    public static double convertToDouble(String numberStr) {
        if (numberStr == null)
            return 0D;
        String number = numberStr.replaceAll(",", ".");
        if (isNumeric(number))
            return Double.parseDouble(number);
        return 0D;
    }

    public static boolean isNumeric(String numberStr) {
        if (numberStr == null)
            return false;
        String number = numberStr.replaceAll(",", ".");
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }
}
