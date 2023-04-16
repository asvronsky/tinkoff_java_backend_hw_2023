package ru.asvronsky.linkparser.ParserResults;

import java.net.URI;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public abstract sealed class ParserResult permits GithubParserResult, StackOverflowParserResult {
    private URI normalizedLink;
}
