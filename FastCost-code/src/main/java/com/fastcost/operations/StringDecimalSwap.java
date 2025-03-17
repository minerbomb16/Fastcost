package com.fastcost.operations;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class StringDecimalSwap {
    public static String decimalToString(BigDecimal bigDecimal) {
        return new DecimalFormat("0.00").format(bigDecimal);
    }

    public static BigDecimal stringToDecimal(String string) {
        if(string.equals("")) {
            return new BigDecimal("0.00");
        }
        if(string.contains(",")) {
            for(int i = 0; i < string.length(); i++) {
                if(string.charAt(i) == ',') {
                    string = string.substring(0, i) + "." + string.substring(i + 1);
                }
            }
        }
        try {
            return new BigDecimal(string);
        } catch (Exception e) {
            return new BigDecimal("0.00");
        }
    }

    public static String stringDecimalFormatOption(String string, int option) {
        String[] format = {"#.##", "0.00"};
        if(string.equals("")) {
            return "";
        }
        if(string.contains(",")) {
            for(int i = 0; i < string.length(); i++) {
                if(string.charAt(i) == ',') {
                    string = string.substring(0, i) + "." + string.substring(i + 1);
                }
            }
        }
        try {
            return new DecimalFormat(format[option]).format(new BigDecimal(string));
        } catch (Exception e) {
            return "0";
        }
    }
}
