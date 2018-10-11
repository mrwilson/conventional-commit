package uk.co.probablyfine.conventional_commit.parser;

class Commit {
    String type;
    String scope;
    String description;
    String body;

    Commit(String type, String scope, String description, String body) {
        this.type = type;
        this.scope = scope;
        this.description = description;
        this.body = body;
    }
}