package uk.co.probablyfine.conventional_commit.parser;

import org.parboiled.BaseParser;
import org.parboiled.Rule;

import static uk.co.probablyfine.conventional_commit.parser.CommitBuilder.builder;

public class SpecImplementingParser extends BaseParser<CommitBuilder> {

    public Rule Commit() {
        return Sequence(
            // Initialise stack with empty builder.

            push(builder()),

            // <type>[optional scope]: <description>

            Type(), Optional(Scope()), Delimiter(), Description(),

            FirstOf(

                // [optional body]
                // [optional footer]
                Sequence(
                    SectionSeparator(),
                    Body(),
                    SectionSeparator(),
                    Footer(),
                    Optional(NewLine()),
                    EOI
                ),

                // [optional body]
                Sequence(
                    SectionSeparator(),
                    Body(),
                    Optional(NewLine()),
                    EOI
                ),

                // No body or footer
                Sequence(
                    Optional(NewLine()),
                    EOI
                )
            )
        );
    }

    Rule Body() {
        return MultipleLinesOfText(CommitBuilder::body);
    }

    Rule Footer() {
        return MultipleLinesOfText(CommitBuilder::footer);
    }

    Rule NewLine() {
        return Ch('\n');
    }

    Rule Type() {
        return Sequence(
            OneOrMore(
                CharRange('A', 'z')
            ),
            push(pop().type(match()))
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
                LineOfText()
            ),
            push(pop().description(match()))
        );
    }

    Rule MultipleLinesOfText(AddFieldToBuilder dataLoader) {
        return Sequence(
            Sequence(
                LineOfText(),
                ZeroOrMore(
                    NewLine(), LineOfText()
                )
            ),
            push(dataLoader.add(pop(), match()))
        );
    }

    Rule LineOfText() {
        return OneOrMore(NoneOf("\n"));
    }

    Rule SectionSeparator() {
        return Sequence(
            NewLine(), NewLine()
        );
    }

    interface AddFieldToBuilder {
        CommitBuilder add(CommitBuilder builder, String value);
    }
}
