package com.buildtechwithJack.holmes.utilities;

import com.buildtechwithJack.holmes.models.LineDiff;
import org.apache.commons.text.StringEscapeUtils;

import java.util.ArrayList;
import java.util.List;

public class RenderUtils {

    public static List<LineDiff> basicLineDiffWithHighlight(String a, String b) {
        String[] aLines = splitLines(a);
        String[] bLines = splitLines(b);

        int max = Math.max(aLines.length, bLines.length);

        List<LineDiff> out = new ArrayList<>(max);

        for (int i = 0; i < max; i++) {
            String av = "";
            String bv = "";
            boolean hasA = i < aLines.length;
            boolean hasB = i < bLines.length;

            if (hasA) {
                av = aLines[i];
            }
            if (hasB) {
                bv = bLines[i];
            }

            String status = "same";
            if (hasA && hasB && av.equals(bv)) {
                status = "same";
            } else if (hasA && hasB && !av.equals(bv)) {
                status = "changed";
            } else if (hasA && !hasB) {
                status = "removed";
            } else if (!hasA && hasB) {
                status = "added";
            }

            LineDiff row = new LineDiff();
            row.setLineNum(i + 1);
            row.setStatus(status);

            if ("changed".equals(status)) {
                String[] highlighted = highlightCharDiff(av, bv);
                row.setAHtml(highlighted[0]);
                row.setBHtml(highlighted[1]);
            } else {
                row.setAHtml(StringEscapeUtils.escapeHtml4(av));
                row.setBHtml(StringEscapeUtils.escapeHtml4(bv));
            }

            out.add(row);
        }

        return out;
    }

    private static String[] splitLines(String text) {
        if (text == null || text.isEmpty()) {
            return new String[0];
        }
        return text.split("\\R", -1);
    }

    public static String[] highlightCharDiff(String a, String b) {
        if (a == null) a = "";
        if (b == null) b = "";

        char[] ar = a.toCharArray();
        char[] br = b.toCharArray();

        // common prefix
        int p = 0;
        while (p < ar.length && p < br.length && ar[p] == br[p]) {
            p++;
        }

        // common suffix
        int as = ar.length;
        int bs = br.length;
        while (as > p && bs > p && ar[as - 1] == br[bs - 1]) {
            as--;
            bs--;
        }

        String aPrefix = a.substring(0, p);
        String aMid = a.substring(p, as);
        String aSuffix = a.substring(as);

        String bPrefix = b.substring(0, p);
        String bMid = b.substring(p, bs);
        String bSuffix = b.substring(bs);

        StringBuilder bufA = new StringBuilder();
        bufA.append(StringEscapeUtils.escapeHtml4(aPrefix));
        if (!aMid.isEmpty()) {
            bufA.append("<mark>");
            bufA.append(StringEscapeUtils.escapeHtml4(aMid));
            bufA.append("</mark>");
        }
        bufA.append(StringEscapeUtils.escapeHtml4(aSuffix));

        StringBuilder bufB = new StringBuilder();
        bufB.append(StringEscapeUtils.escapeHtml4(bPrefix));
        if (!bMid.isEmpty()) {
            bufB.append("<mark>");
            bufB.append(StringEscapeUtils.escapeHtml4(bMid));
            bufB.append("</mark>");
        }
        bufB.append(StringEscapeUtils.escapeHtml4(bSuffix));

        return new String[]{bufA.toString(), bufB.toString()};
    }
}
