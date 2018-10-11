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

    private Matcher<Optional<Commit>> hasType(String type) {
        return hasAttribute(commit -> commit.type, type);
    }

    private Matcher<Optional<Commit>> hasScope(String scope) {
        return hasAttribute(commit -> commit.scope, scope);
    }

    private <T> Matcher<Optional<Commit>> hasAttribute(Function<Commit, T> attribute, T value) {
        return new TypeSafeMatcher<Optional<Commit>>() {
            @Override
            protected boolean matchesSafely(Optional<Commit> commit) {
                return commit.isPresent() && Objects.equals(value, attribute.apply(commit.get()));
            }

            @Override
            protected void describeMismatchSafely(Optional<Commit> foo, Description description) {
                description.appendText("was ").appendValue(foo);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Commit with ").appendValue(value);
            }
        };
    }


}