package uk.co.probablyfine.conventional_commit.parser;

class Commit {
    String type;
    String scope;

    Commit(String type, String scope) {
        this.type = type;
        this.scope = scope;
    }
}