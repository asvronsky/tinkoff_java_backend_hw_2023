package ru.asvronsky.scrapper.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.RequiredArgsConstructor;
import ru.asvronsky.linkparser.ParserResults.GithubParserResult;
import ru.asvronsky.linkparser.ParserResults.ParserResult;
import ru.asvronsky.linkparser.ParserResults.StackOverflowParserResult;
import ru.asvronsky.linkparser.Parsers.Parser;
import ru.asvronsky.scrapper.clients.BotClient;
import ru.asvronsky.scrapper.clients.GithubClient;
import ru.asvronsky.scrapper.clients.StackOverflowClient;
import ru.asvronsky.scrapper.model.Link;
import ru.asvronsky.scrapper.repository.LinkDao;
import ru.asvronsky.scrapper.repository.SubscriptionDao;
import ru.asvronsky.shared.botdto.UpdateLinksRequest;

@Service
@RequiredArgsConstructor
public class JdbcLinkUpdater implements LinkUpdater {

    private final GithubClient githubClient;
    private final StackOverflowClient stackOverflowClient;
    private final BotClient botClient;
    
    private final LinkDao linkRepository;
    private final SubscriptionDao subscriptionRepository;

    private final Parser parser;

    private final ObjectMapper mapper = new ObjectMapper()
        .registerModule(new JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    

    @Override
    public void update(Duration offset) {
        List<Link> outdatedLinks = linkRepository.findOutdated(offset);

        for (Link link : outdatedLinks) {
            ParserResult parserResult;
            try {
                parserResult = parser.parse(new URI(link.getUrl()))
                    .orElseThrow(() -> new IllegalStateException("DB link not parseable %s".formatted(link.getUrl())));
            } catch (URISyntaxException e) {
                throw new IllegalStateException("DB link not URI parseable %s".formatted(link.getUrl()));
            }
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

            List<String> updatedTags = getUpdatedTags(link, clientResponse);
            if (!updatedTags.isEmpty()) {
                String message = generateMessage(link, updatedTags);
                List<Long> chatIds = subscriptionRepository.findChatsByLink(link);
                UpdateLinksRequest request = generateRequest(link, message, chatIds);
                botClient.sendNotification(request);
            }
        }
    }

    private List<String> getUpdatedTags(Link link, Object websiteData) {
        if (websiteData == null)
            return List.of();

        try {
            link.setWebsiteData(mapper.writeValueAsString(websiteData));
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(
                "JsonProcessingException thrown while processing website data for link \"%s\""
                    .formatted(link.getUrl()), 
                e
            );
        }

        return linkRepository.update(link);
    }
    
    private String generateMessage(Link link, List<String> tags) {
        return "Link: \"%s\"\n".formatted(link.getUrl())
            + "Updated tags:\n"
            + tags.stream()
                .map(str -> "\t" + str)
                .collect(Collectors.joining("\n"));
    }

    private UpdateLinksRequest generateRequest(Link link, String message, List<Long> chatIds) {
        return new UpdateLinksRequest(
            link.getId(), 
            URI.create(link.getUrl()), 
            message, 
            chatIds
        );
    }
    
}
