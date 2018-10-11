package uk.co.probablyfine.conventional_commit.parser;

class ConventionalCommit {
    String type;
    String scope;

    ConventionalCommit(String type, String scope) {
        this.type = type;
        this.scope = scope;
    }
}