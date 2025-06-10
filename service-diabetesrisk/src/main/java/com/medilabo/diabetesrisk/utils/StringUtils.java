package com.medilabo.diabetesrisk.utils;

import org.apache.commons.lang.StringEscapeUtils;

public abstract class StringUtils {
    public static String getHTMLStringContent(String htmlContent) {
        return StringEscapeUtils.unescapeHtml(htmlContent).trim();
    }
}
