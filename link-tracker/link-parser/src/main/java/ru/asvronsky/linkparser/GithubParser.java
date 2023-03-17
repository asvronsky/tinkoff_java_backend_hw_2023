package ru.asvronsky.linkparser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GithubParser extends Parser {

    public GithubParser(Parser successor) {
        super(successor);
    }

    @Override
    public ParserResult parse(String url) {
        // first group: user
        // second group: name of repository
        Pattern userAndRepoPattern = Pattern.compile("^(?>https://)?github.com/(?<user>.+?)/(?<repo>.+?)(?>/.*)?$");
        Matcher userAndRepoMatcher = userAndRepoPattern.matcher(url);
        if (userAndRepoMatcher.matches()) {
            String user = userAndRepoMatcher.group("user");
            String repo = userAndRepoMatcher.group("repo");
            return new GithubParserResult(user, repo);
        } else {
            return super.parse(url);
        }
    }
    
}
