package uk.co.probablyfine.commit_dash.parser;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class SemanticCommitParserTest {

    @Test
    public void shouldExtractCommitType_noScope() {
        SemanticCommit commit = SemanticCommitParser.parse(
            "fix: Widgets are broken"
        );

        assertThat(commit.type, is("fix"));
    }

    @Test
    public void shouldExtractCommitType_withScope() {
        SemanticCommit commit = SemanticCommitParser.parse(
            "chore(widget): Update dependencies for Widget"
        );

        assertThat(commit.type, is("chore"));
        assertThat(commit.scope, is("widget"));
    }

    @Test
    public void shouldAttributeRevertCommitsToScopeOfRevertedCommit() {
        SemanticCommit commit = SemanticCommitParser.parse(
            "Revert \"feat(sprockets): Replace widgets with sprockets\""
        );

        assertThat(commit.type, is("revert"));
        assertThat(commit.scope, is("sprockets"));
    }

    @Test
    public void shouldAttributeReapplyCommitsToScopeOfReappliedCommit() {
        SemanticCommit commit = SemanticCommitParser.parse(
            "Reapply \"feat(sprockets): Replace widgets with sprockets\""
        );

        assertThat(commit.type, is("reapply"));
        assertThat(commit.scope, is("sprockets"));
    }

    @Test
    public void shouldReduceTypeToLowerCase() {
        SemanticCommit commit = SemanticCommitParser.parse(
            "Docs(sprockets): Document new sprocket integration"
        );

        assertThat(commit.type, is("docs"));
        assertThat(commit.scope, is("sprockets"));
    }

    @Test
    public void shouldReturnUnknownTypeIfUnableToParseCommit() {
        SemanticCommit commit = SemanticCommitParser.parse(
            "We're rebuilding this to make use of a WidgetFramework."
        );

        assertThat(commit.type, is("unknown"));
        assertThat(commit.scope, is(nullValue()));
    }

}