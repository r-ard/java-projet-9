package com.medilabo.diabetesrisk.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;

public abstract class StringUtils {
    public static String getHTMLStringContent(String htmlContent) {
        return StringEscapeUtils.unescapeHtml(htmlContent).trim();
    }

    public static String normalizeToAscii(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);

        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String ascii = pattern.matcher(normalized).replaceAll("");

        ascii = ascii.replaceAll("œ", "oe").replaceAll("[^\\p{ASCII}]", "");

        return ascii;
    }
}
