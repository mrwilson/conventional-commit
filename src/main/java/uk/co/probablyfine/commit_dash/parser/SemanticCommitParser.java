package uk.co.probablyfine.commit_dash.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class SemanticCommitParser {

    private static Pattern pattern = Pattern.compile("(?:Revert \"|Reapply \")?(.*?)(?:\\((.*)\\))?:.*");

    static SemanticCommit parse(String input) {

        final Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return new SemanticCommit(
                identifyType(input, matcher),
                matcher.group(2)
            );
        } else {
            return new SemanticCommit("unknown", null);
        }


    }

    private static String identifyType(String input, Matcher matcher) {
        if (input.toLowerCase().startsWith("revert")) {
            return "revert";
        } else if (input.toLowerCase().startsWith("reapply")) {
            return "reapply";
        } else if (input.toLowerCase().startsWith("merge branch")) {
            return "merge";
        } else {
            return matcher.group(1).toLowerCase();
        }
    }
}
