package uk.co.probablyfine.conventional_commit.parser;

import org.parboiled.BaseParser;
import org.parboiled.Rule;

import static uk.co.probablyfine.conventional_commit.parser.CommitBuilder.builder;

public class SpecImplementingParser extends BaseParser<CommitBuilder> {

    public Rule Commit() {
        return Sequence(

            // <type>[optional scope]: <description>

            Type(), Optional(Scope()), Delimiter(), Description(),

            // [optional body]

            FirstOf(
                EOI,
                Sequence(
                    NewLine(), EOI
                ),
                Sequence(
                    NewLine(), NewLine(),
                    Body(),
                    EOI
                )
            )
        );
    }

    Rule Body() {
        return Sequence(
            OneOrMore(
                ANY
            ),
            push(pop().body(match()))
        );
    }

    Rule NewLine() {
        return Ch('\n');
    }

    Rule Type() {
        return Sequence(
            OneOrMore(
                CharRange('A', 'z')
            ),
            push(builder().type(match()))
        );
    }

    Rule Scope() {
        return Sequence(
                Ch('('),
                OneOrMore(NoneOf(")")),
                push(pop().scope(match())),
                Ch(')')
        );
    }

    Rule Delimiter() {
        return String(": ");
    }

    Rule Description() {
        return Sequence(
            Sequence(
                NoneOf(" "),
                ZeroOrMore(
                    NoneOf("\n")
                )
            ),
            push(pop().description(match()))
        );
    }
}
