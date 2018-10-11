package uk.co.probablyfine.conventional_commit.parser;

import org.parboiled.parserunners.BasicParseRunner;
import org.parboiled.parserunners.ParseRunner;
import org.parboiled.support.ParsingResult;

import static org.parboiled.Parboiled.createParser;

class ConventionalCommit {

    private static ParseRunner<CommitBuilder> parser = new BasicParseRunner<>(
        createParser(SpecImplementingParser.class).Commit()
    );

    static Commit parse(String input) {
        final ParsingResult<CommitBuilder> result = parser.run(input);

        if (result.matched) {
            return result.parseTreeRoot.getValue().build();
        } else {
            return new Commit("unknown", null);
        }

    }
}
