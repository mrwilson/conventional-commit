package uk.co.probablyfine.conventional_commit.parser;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class ConventionalCommitTest {

    @Test
    public void shouldExtractCommitType_noScope() {
        Optional<Commit> commit = ConventionalCommit.parse(
            "fix: Widgets are broken"
        );

        assertThat(commit, hasType("fix"));
        assertThat(commit, hasScope(null));
    }

    @Test
    public void shouldExtractCommitType_withScope() {
        Optional<Commit> commit = ConventionalCommit.parse(
            "chore(widget): Update dependencies for Widget"
        );

         assertThat(commit, hasType("chore"));
         assertThat(commit, hasScope("widget"));
    }

    @Test
    public void shouldExtractCommitDescription() {
        Optional<Commit> commit = ConventionalCommit.parse(
            "fix: Widgets are broken"
        );

        assertThat(commit, hasDescription("Widgets are broken"));
    }

    @Test
    public void shouldExtractCommitDescription_WithScope() {
        Optional<Commit> commit = ConventionalCommit.parse(
                "fix(widget): Widgets are broken"
        );

        assertThat(commit, hasDescription("Widgets are broken"));
    }

    @Test
    public void shouldExtractBodyFromCommit() {
        Optional<Commit> commit = ConventionalCommit.parse(
            "fix(widget): Widgets are broken\n\nThis was due to the wotsit being old and rusty"
        );

        assertThat(commit, hasBody("This was due to the wotsit being old and rusty"));
    }

    @Test
    public void shouldExtractMultiLineBodyFromCommit() {
        Optional<Commit> commit = ConventionalCommit.parse(
            "fix(widget): Widgets are still broken\n\nReplacement wotsit was faulty\nUsing new brand"
        );

        assertThat(commit, hasBody("Replacement wotsit was faulty\nUsing new brand"));
    }

    @Test
    public void shouldSupportTrailingNewlineIfNoBodyOrFooter() {
        Optional<Commit> commit = ConventionalCommit.parse(
            "chore: Add newlines to commit messages\n"
        );

        assertThat(commit, hasType("chore"));
        assertThat(commit, hasDescription("Add newlines to commit messages"));
    }

    @Test
    public void shouldExtractBodyFromCommit_RequireNewLineSeparator() {
        Optional<Commit> commit = ConventionalCommit.parse(
            "fix(widget): Widgets are broken\nThis was due to the wotsit being old and rusty"
        );

        assertFalse(commit.isPresent());
    }

    @Test
    public void shouldReturnEmptyOptionalIfUnableToParse() {
        Optional<Commit> commit = ConventionalCommit.parse(
            "We're rebuilding this to make use of a WidgetFramework."
        );

        assertFalse(commit.isPresent());
    }

    @Test
    public void shouldNotParseCommitsWithExtraWhitespace() {
        Optional<Commit> commit = ConventionalCommit.parse(
            "feat : Lint whitespace"
        );

        assertFalse(commit.isPresent());
    }

    @Test
    public void shouldNotParseCommitsWithNoDescription() {
        Optional<Commit> commit = ConventionalCommit.parse(
            "feat(foo): "
        );

        assertFalse(commit.isPresent());
    }

    @Test
    public void shouldNotParseCommitsWithNoDescriptionAndTrailingWhitespace() {
        Optional<Commit> commit = ConventionalCommit.parse(
            "feat(foo):  "
        );

        assertFalse(commit.isPresent());
    }

    @Test
    public void shouldParseFooterIfBodyIsPresent() {
        Optional<Commit> commit = ConventionalCommit.parse(
            "fix(widget): Add grease\n\nThe widget was grinding against the sprockets\n\nFixes #13"
        );

        assertThat(commit, hasType("fix"));
        assertThat(commit, hasScope("widget"));
        assertThat(commit, hasDescription("Add grease"));
        assertThat(commit, hasBody("The widget was grinding against the sprockets"));
        assertThat(commit, hasFooter("Fixes #13"));
    }

    @Test
    public void shouldParseMultiLineFooterAndMultiLineBodyIsPresent() {
        Optional<Commit> commit = ConventionalCommit.parse(
            "fix(widget): Add grease\n\n" +
                "The widget was grinding against the sprockets\nGrease addresses the problem\n\n" +
                "Fixes #13\nCo-Authored-By: Bilbo Baggins <bb@bag-end.theshire.me>"
        );

        assertThat(commit, hasType("fix"));
        assertThat(commit, hasScope("widget"));
        assertThat(commit, hasDescription("Add grease"));
        assertThat(commit, hasBody("The widget was grinding against the sprockets\nGrease addresses the problem"));
        assertThat(commit, hasFooter("Fixes #13\nCo-Authored-By: Bilbo Baggins <bb@bag-end.theshire.me>"));
    }

    @Test
    public void shouldParseTrailingNewLineFromCommitFooter() {
        Optional<Commit> commit = ConventionalCommit.parse(
                "fix(widget): Add grease\n\nThe widget was grinding against the sprockets\n\nFixes #13\n"
        );

        assertThat(commit, hasType("fix"));
        assertThat(commit, hasScope("widget"));
        assertThat(commit, hasDescription("Add grease"));
        assertThat(commit, hasBody("The widget was grinding against the sprockets"));
        assertThat(commit, hasFooter("Fixes #13"));
    }

    private Matcher<Optional<Commit>> hasType(String type) {
        return hasAttribute(commit -> commit.type, type);
    }

    private Matcher<Optional<Commit>> hasScope(String scope) {
        return hasAttribute(commit -> commit.scope, scope);
    }

    private Matcher<Optional<Commit>> hasDescription(String description) {
        return hasAttribute(commit -> commit.description, description);
    }

    private Matcher<Optional<Commit>> hasBody(String body) {
        return hasAttribute(commit -> commit.body, body);
    }

    private Matcher<Optional<Commit>> hasFooter(String body) {
        return hasAttribute(commit -> commit.footer, body);
    }

    private <T> Matcher<Optional<Commit>> hasAttribute(Function<Commit, T> attribute, T value) {
        return new TypeSafeMatcher<Optional<Commit>>() {
            @Override
            protected boolean matchesSafely(Optional<Commit> commit) {
                return commit.isPresent() && Objects.equals(value, attribute.apply(commit.get()));
            }

            @Override
            protected void describeMismatchSafely(Optional<Commit> commit, Description description) {
                description.appendText("was ").appendValue(commit.map(attribute));
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Commit with ").appendValue(value);
            }
        };
    }


}