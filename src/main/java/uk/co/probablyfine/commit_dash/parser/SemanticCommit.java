package uk.co.probablyfine.commit_dash.parser;

class SemanticCommit {
    String type;
    String scope;

    SemanticCommit(String type, String scope) {
        this.type = type;
        this.scope = scope;
    }
}