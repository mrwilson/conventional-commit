package uk.co.probablyfine.commit_dash.parser;

class ConventionalCommit {
    String type;
    String scope;

    ConventionalCommit(String type, String scope) {
        this.type = type;
        this.scope = scope;
    }
}