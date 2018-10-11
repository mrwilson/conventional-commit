package uk.co.probablyfine.conventional_commit.parser;

class Commit {
    String type;
    String scope;
    String description;

    Commit(String type, String scope, String description) {
        this.type = type;
        this.scope = scope;
        this.description = description;
    }
}