package uk.co.probablyfine.conventional_commit.parser;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class ConventionalCommitTest {

    @Test
    public void shouldExtractCommitType_noScope() {
        Commit commit = ConventionalCommit.parse(
            "fix: Widgets are broken"
        );

        assertThat(commit.type, is("fix"));
        assertThat(commit.scope, is(nullValue()));
    }

    @Test
    public void shouldExtractCommitType_withScope() {
        Commit commit = ConventionalCommit.parse(
            "chore(widget): Update dependencies for Widget"
        );

        assertThat(commit.type, is("chore"));
        assertThat(commit.scope, is("widget"));
    }

    @Test
    public void shouldReduceTypeToLowerCase() {
        Commit commit = ConventionalCommit.parse(
            "Docs(sprockets): Document new sprocket integration"
        );

        assertThat(commit.type, is("Docs"));
        assertThat(commit.scope, is("sprockets"));
    }

    @Test
    public void shouldReturnUnknownTypeIfUnableToParseCommit() {
        Commit commit = ConventionalCommit.parse(
            "We're rebuilding this to make use of a WidgetFramework."
        );

        assertThat(commit.type, is("unknown"));
        assertThat(commit.scope, is(nullValue()));
    }

    @Test
    public void shouldNotParseCommitsWithExtraWhitespace() {
        Commit commit = ConventionalCommit.parse(
                "feat : Lint whitespace"
        );

        assertThat(commit.type, is("unknown"));
        assertThat(commit.scope, is(nullValue()));
    }
}