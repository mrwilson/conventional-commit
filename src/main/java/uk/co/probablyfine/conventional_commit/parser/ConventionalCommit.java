package uk.co.probablyfine.conventional_commit.parser;

import org.parboiled.parserunners.BasicParseRunner;
import org.parboiled.parserunners.ParseRunner;
import org.parboiled.support.ParsingResult;

import java.util.Optional;

import static org.parboiled.Parboiled.createParser;

public class ConventionalCommit {

    private static ParseRunner<CommitBuilder> parser = new BasicParseRunner<>(
        createParser(SpecImplementingParser.class).Commit()
    );

    public static Optional<Commit> parse(String input) {
        final ParsingResult<CommitBuilder> result = parser.run(input);

        if (result.matched) {
            return Optional.of(result.valueStack.pop().build());
        } else {
            return Optional.empty();
        }

    }
}
