package org.example.login.util;

import java.text.NumberFormat;
import java.util.Locale;

public class PriceFormatter {
    public static String formatPrice(long price) {
        NumberFormat formatter = NumberFormat.getInstance(Locale.KOREA);
        return formatter.format(price) + "원";
    }
}
