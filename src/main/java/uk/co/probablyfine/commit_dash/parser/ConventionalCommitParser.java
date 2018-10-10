package uk.co.probablyfine.commit_dash.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ConventionalCommitParser {

    private static Pattern pattern = Pattern.compile("(?:Revert \"|Reapply \")?(.*?)(?:\\((.*)\\))?:.*");

    static ConventionalCommit parse(String input) {

        final Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return new ConventionalCommit(
                identifyType(input, matcher).trim(),
                matcher.group(2)
            );
        } else if (input.toLowerCase().startsWith("merge branch")) {
            return new ConventionalCommit("merge", null);
        } else {
            return new ConventionalCommit("unknown", null);
        }


    }

    private static String identifyType(String input, Matcher matcher) {
        if (input.toLowerCase().startsWith("revert")) {
            return "revert";
        } else if (input.toLowerCase().startsWith("reapply")) {
            return "reapply";
        } else {
            return matcher.group(1).toLowerCase();
        }
    }
}
