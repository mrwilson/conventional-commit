package uk.co.probablyfine.commit_dash.parser;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class ConventionalCommitParserTest {

    @Test
    public void shouldExtractCommitType_noScope() {
        ConventionalCommit commit = ConventionalCommitParser.parse(
            "fix: Widgets are broken"
        );

        assertThat(commit.type, is("fix"));
    }

    @Test
    public void shouldExtractCommitType_withScope() {
        ConventionalCommit commit = ConventionalCommitParser.parse(
            "chore(widget): Update dependencies for Widget"
        );

        assertThat(commit.type, is("chore"));
        assertThat(commit.scope, is("widget"));
    }

    @Test
    public void shouldAttributeRevertCommitsToScopeOfRevertedCommit() {
        ConventionalCommit commit = ConventionalCommitParser.parse(
            "Revert \"feat(sprockets): Replace widgets with sprockets\""
        );

        assertThat(commit.type, is("revert"));
        assertThat(commit.scope, is("sprockets"));
    }

    @Test
    public void shouldAttributeRevertCommitsToScopeOfRevertedCommit_lowerCase() {
        ConventionalCommit commit = ConventionalCommitParser.parse(
            "revert \"feat(sprockets): Replace widgets with sprockets\""
        );

        assertThat(commit.type, is("revert"));
        assertThat(commit.scope, is("sprockets"));
    }


    @Test
    public void shouldAttributeReapplyCommitsToScopeOfReappliedCommit() {
        ConventionalCommit commit = ConventionalCommitParser.parse(
            "Reapply \"feat(sprockets): Replace widgets with sprockets\""
        );

        assertThat(commit.type, is("reapply"));
        assertThat(commit.scope, is("sprockets"));
    }

    @Test
    public void shouldAttributeReapplyCommitsToScopeOfReappliedCommit_lowerCase() {
        ConventionalCommit commit = ConventionalCommitParser.parse(
            "reapply \"feat(sprockets): Replace widgets with sprockets\""
        );

        assertThat(commit.type, is("reapply"));
        assertThat(commit.scope, is("sprockets"));
    }


    @Test
    public void shouldReduceTypeToLowerCase() {
        ConventionalCommit commit = ConventionalCommitParser.parse(
            "Docs(sprockets): Document new sprocket integration"
        );

        assertThat(commit.type, is("docs"));
        assertThat(commit.scope, is("sprockets"));
    }

    @Test
    public void shouldReturnUnknownTypeIfUnableToParseCommit() {
        ConventionalCommit commit = ConventionalCommitParser.parse(
            "We're rebuilding this to make use of a WidgetFramework."
        );

        assertThat(commit.type, is("unknown"));
        assertThat(commit.scope, is(nullValue()));
    }

    @Test
    public void shouldAttributeMergeCommits() {
        ConventionalCommit commit = ConventionalCommitParser.parse(
                "Merge branch 'master'"
        );

        assertThat(commit.type, is("merge"));
        assertThat(commit.scope, is(nullValue()));
    }

    @Test
    public void shouldAttributeMergeCommits_lowerCase() {
        ConventionalCommit commit = ConventionalCommitParser.parse(
                "merge branch 'master'"
        );

        assertThat(commit.type, is("merge"));
        assertThat(commit.scope, is(nullValue()));
    }

    @Test
    public void shouldTrimTrailingWhitespaceFromCommitType() {
        ConventionalCommit commit = ConventionalCommitParser.parse(
                "feat : Lint whitespace"
        );

        assertThat(commit.type, is("feat"));
    }


}