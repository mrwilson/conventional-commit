package uk.co.probablyfine.conventional_commit.parser;

class CommitBuilder {
    private String type;
    private String scope;
    private String description;
    private String body;
    private String footer;

    private CommitBuilder() {}

    static CommitBuilder builder() {
        return new CommitBuilder();
    }

    CommitBuilder type(String type) {
        this.type = type;
        return this;
    }

    CommitBuilder scope(String scope) {
        this.scope = scope;
        return this;
    }

    CommitBuilder description(String description) {
        this.description = description;
        return this;
    }

    CommitBuilder body(String body) {
        this.body = body;
        return this;
    }

    CommitBuilder footer(String footer) {
        this.footer = footer;
        return this;
    }

    Commit build() {
        return new Commit(this.type, this.scope, this.description, this.body, this.footer);
    }


}
