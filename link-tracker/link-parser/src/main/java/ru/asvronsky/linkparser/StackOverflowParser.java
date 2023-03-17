package ru.asvronsky.linkparser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StackOverflowParser extends Parser {

    public StackOverflowParser(Parser successor) {
        super(successor);
    }

    @Override
    public ParserResult parse(String url) {
        Pattern idPattern = Pattern.compile("^(?>https://)?stackoverflow\\.com/questions/(?<id>\\d+)(?>/.*)?$");
        Matcher idMatcher = idPattern.matcher(url);
        if (idMatcher.matches()) {
            String id = idMatcher.group("id");
            return new StackOverflowParserResult(id);
        } else {
            return super.parse(url);
        }
    }
    
}
