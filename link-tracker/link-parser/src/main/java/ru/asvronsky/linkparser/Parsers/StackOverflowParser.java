package ru.asvronsky.linkparser.Parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.asvronsky.linkparser.ParserResults.ParserResult;
import ru.asvronsky.linkparser.ParserResults.StackOverflowParserResult;

public final class StackOverflowParser extends Parser {
    private final static Pattern idPattern = Pattern.compile(
        "^(?>https://)?stackoverflow\\.com/questions/(?<id>\\d+)(?>/.*)?$"
    );

    public StackOverflowParser(Parser successor) {
        super(successor);
    }

    @Override
    public ParserResult parse(String url) {
        
        Matcher idMatcher = idPattern.matcher(url);
        if (idMatcher.matches()) {
            String id = idMatcher.group("id");
            return new StackOverflowParserResult(id);
        } else {
            return super.parse(url);
        }
    }
    
}
