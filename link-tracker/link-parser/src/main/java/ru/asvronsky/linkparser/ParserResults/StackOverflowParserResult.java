package ru.asvronsky.linkparser.ParserResults;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public final class StackOverflowParserResult extends ParserResult {
    private long id;
}
