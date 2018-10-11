package uk.co.probablyfine.conventional_commit.parser;

public class Commit {
    public final String type;
    public final String scope;
    public final String description;
    public final String body;

    Commit(String type, String scope, String description, String body) {
        this.type = type;
        this.scope = scope;
        this.description = description;
        this.body = body;
    }
}