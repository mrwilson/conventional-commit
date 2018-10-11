package uk.co.probablyfine.conventional_commit.parser;

class CommitBuilder {
    private String type;
    private String scope;

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

    Commit build() {
        return new Commit(this.type, this.scope);
    }

}
