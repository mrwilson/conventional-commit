# conventional-commit

[![Build Status](https://travis-ci.org/mrwilson/conventional-commit.svg?branch=master)](https://travis-ci.org/mrwilson/conventional-commit)

A small java library to parse [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0-beta.2/)

## API

```java
Optional<Commit> commit = ConventionalCommit.parse("feat(widget): Add thingy to widget");

commit.isPresent(); // true
commit.type; // "feat"
commit.scope; // "widget"
```

## Building

This project requires Maven and Java 8+.

```bash
$ mvn clean test
```
