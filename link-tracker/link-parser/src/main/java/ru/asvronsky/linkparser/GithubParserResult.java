package ru.asvronsky.linkparser;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public final class GithubParserResult extends ParserResult {
    private String user;
    private String repository;
}
