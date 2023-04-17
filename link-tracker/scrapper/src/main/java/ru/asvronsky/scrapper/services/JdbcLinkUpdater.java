package ru.asvronsky.scrapper.services;

import java.net.URI;
import java.time.Duration;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import ru.asvronsky.linkparser.ParserResults.GithubParserResult;
import ru.asvronsky.linkparser.ParserResults.ParserResult;
import ru.asvronsky.linkparser.ParserResults.StackOverflowParserResult;
import ru.asvronsky.linkparser.Parsers.Parser;
import ru.asvronsky.scrapper.clients.GithubClient;
import ru.asvronsky.scrapper.clients.StackOverflowClient;
import ru.asvronsky.scrapper.model.Link;
import ru.asvronsky.scrapper.repository.LinkDao;

@Service
@RequiredArgsConstructor
public class JdbcLinkUpdater implements LinkUpdater {

    private final GithubClient githubClient;
    private final StackOverflowClient stackOverflowClient;
    
    private final LinkDao linkRepository;

    private final Parser parser;

    private final ObjectMapper mapper = new ObjectMapper();
    

    @Override
    public void update(Duration offset) {
        List<Link> outdatedLinks = linkRepository.findOutdated(offset);

        for (Link link : outdatedLinks) {
            ParserResult parserResult = parser.parse(URI.create(link.getUrl()))
                .orElseThrow(() -> new IllegalStateException("DB link not parseable %s".formatted(link.getUrl())));

            Object clientResponse = switch (parserResult) {
                case GithubParserResult result ->
                    githubClient.getGihubData(
                        result.getUsername(), 
                        result.getRepo()
                    );
                case StackOverflowParserResult result ->
                    stackOverflowClient.getStackOverflowData(
                        result.getId()
                    );
                default -> null;
            };

            passToNotifier(link, clientResponse);
        }
    }

    private void passToNotifier(Link link, Object websiteData) {
        if (websiteData == null)
            return;

        try {
            link.setWebsiteData(mapper.writeValueAsString(websiteData));
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(
                "JsonProcessingException thrown while processing website data for link \"%s\""
                    .formatted(link.getUrl()), 
                e
            );
        }

        List<String> updatedTags = linkRepository.update(link);
    }
    
}
